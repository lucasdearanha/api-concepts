package com.example.demo.controllers;

import com.example.demo.commands.CreateUserCommand;
import com.example.demo.commands.UserLoginCommand;
import com.example.demo.handlers.UserHandler;
import com.example.demo.responsemodels.GenericResponseModel;
import com.example.demo.responsemodels.UserResponseModel;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserHandler userHandler;
    private final UserService userService;

    @Autowired
    public UserController(UserHandler userHandler, UserService userService) {
        this.userHandler = userHandler;
        this.userService = userService;
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

    @PostMapping("/login")
    public ResponseEntity<GenericResponseModel> login(@RequestBody UserLoginCommand command) {
        return ResponseEntity.ok(userHandler.execute(command));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponseModel> getById(@PathVariable long id) {
        return ResponseEntity.ok(
                new GenericResponseModel(
                        true,
                        "operacao relizada com sucesso",
                        userService.findById(id)
                )
        );
    }
}
