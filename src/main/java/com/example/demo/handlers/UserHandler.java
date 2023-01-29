package com.example.demo.handlers;

import com.example.demo.commands.CreateUserCommand;
import com.example.demo.models.UserModel;
import com.example.demo.responsemodels.GenericResponseModel;
import com.example.demo.responsemodels.UserResponseModel;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserHandler {
    @Autowired
    private UserService userService;

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
}
