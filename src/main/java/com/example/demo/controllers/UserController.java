package com.example.demo.controllers;

import com.example.demo.commands.CreateUserCommand;
import com.example.demo.handlers.UserHandler;
import com.example.demo.reponsemodels.GenericResponseModel;
import com.example.demo.reponsemodels.UserResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserHandler userHandler;

    @Autowired
    public UserController(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @PostMapping("/create-user")
    public ResponseEntity<GenericResponseModel> createUser(@RequestBody CreateUserCommand command) {

        var user = userHandler.execute(command);
        URI uri = null;

        if (user.getData() instanceof UserResponseModel obj) {
            uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(obj.id()).toUri();
        }

        return ResponseEntity.created(uri).body(user);
    }
}
