package com.example.demo.handlers;

import com.example.demo.commands.CreateUserCommand;
import com.example.demo.commands.UserLoginCommand;
import com.example.demo.models.UserModel;
import com.example.demo.responsemodels.GenericResponseModel;
import com.example.demo.responsemodels.UserResponseModel;
import com.example.demo.services.TokenService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserHandler {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public GenericResponseModel execute(CreateUserCommand command) {
        command.validate();

        if (!command.isValid())
            return new GenericResponseModel(false, "Nao foi possivel cadastrar o usuario",
                    command.getNotifications());

        if (userService.emailAlreadyExists(command.getEmail()))
            return new GenericResponseModel(false, "Email ja cadastrado", command.getEmail());

        UserModel userModel = userService.save(command);

        return new GenericResponseModel(true, "Usuario cadastrado com sucesso",
                new UserResponseModel(userModel.getId(), userModel.getFirstName(), userModel.getLastName(),
                        userModel.getEmail())
        );
    }

    public GenericResponseModel execute(UserLoginCommand command) {
        command.validate();

        if (!command.isValid())
            return new GenericResponseModel(
                    false,
                    "Nao foi possivel realizar o login",
                    command.getNotifications()
            );

        UserModel user = userService.findByEmail(command.getEmail());

        if (user == null || !passwordEncoder.matches(command.getPassword(), user.getPassword()))
            return new GenericResponseModel(false, "Usuario ou senha invalidos", null);

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        command.getEmail(),
                        command.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.generateToken(authentication);
        record Token(String token) {
        }
        return new GenericResponseModel(true, "Autenticacao realizada com sucesso", new Token(token));
    }
}
