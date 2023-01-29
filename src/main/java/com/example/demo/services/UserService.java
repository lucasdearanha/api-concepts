package com.example.demo.services;

import com.example.demo.commands.CreateUserCommand;
import com.example.demo.exceptions.DatabaseException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.PaginationLimitException;
import com.example.demo.exceptions.UserOrPasswordInvalidException;
import com.example.demo.models.Role;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.responsemodels.PageableResponseModel;
import com.example.demo.responsemodels.UserResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final String STANDARD_ROLE = "ROLE_USER";

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public UserModel save(CreateUserCommand command) {
        var user = new UserModel(command.getFirstName(),
                command.getLastName(),
                command.getEmail(),
                passwordEncoder.encode(command.getPassword())
        );

        try {
            Role standardRole = roleRepository
                    .findByName(STANDARD_ROLE)
                    .orElseThrow(() -> new NotFoundException("Role nao existe"));

            user.addRole(standardRole);
            user = userRepository.save(user);
        } catch (DataAccessException e) {
            throw new DatabaseException(
                    "Nao foi possivel salvar o usuario, erro de conexao com a base de dados."
            );
        }
        return user;
    }

    public boolean emailAlreadyExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserOrPasswordInvalidException("Usuario ou senha invalidos"));
    }

    public UserResponseModel findById(long id) {
        UserModel userModel = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id nao encontrado"));
        return userToUserResponseModel(userModel);
    }

    public PageableResponseModel<UserResponseModel> getAll(int pageNumber, int pageSize) {

        if (pageSize > 100)
            throw new PaginationLimitException("O limite de elementos por pagina e 100");

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<UserModel> usersPage;
        List<UserModel> userModels;

        try {
            usersPage = userRepository.findAll(pageable);
            userModels = usersPage.getContent();
        } catch (DataAccessException e) {
            throw new DatabaseException(
                    "Nao foi possivel retornar a lista de usuarios, erro de conexao com a base de dados."
            );
        }

        var result = userModels.stream().map(user -> userToUserResponseModel(user)).toList();

        var response = new PageableResponseModel<UserResponseModel>(
                usersPage.getNumber(),
                usersPage.getSize(),
                usersPage.getTotalElements(),
                usersPage.getTotalPages(),
                usersPage.isLast()
        );
        response.setContent(result);

        return response;
    }

    private UserResponseModel userToUserResponseModel(UserModel userModel) {
        return new UserResponseModel(
                userModel.getId(),
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getEmail()
        );
    }
}
