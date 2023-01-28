package com.example.demo.commands;

import com.example.demo.validations.Notification;
import com.example.demo.validations.ValidationUtils;

import java.util.HashSet;
import java.util.Set;

public class CreateUserCommand implements Command {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Notification> notifications = new HashSet<>();

    public CreateUserCommand() {
    }

    public CreateUserCommand(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    @Override
    public void validate() {
        if (!ValidationUtils.isValidName(firstName, 3, 30))
            this.notifications.add(
                    new Notification(
                            "firstname",
                            "Nome invalido: O nome deve conter entre 3 e 30 caracteres"
                    )
            );

        if (!ValidationUtils.isValidName(lastName, 3, 40))
            this.notifications.add(
                    new Notification(
                            "lastname",
                            "Nome invalido: O nome deve conter entre 3 e 40 caracteres"
                    )
            );

        if (!ValidationUtils.isValidEmail(email))
            this.notifications.add(new Notification("email", "Email invalido"));

        if (!ValidationUtils.isValidPassword(password, 8, 30))
            this.notifications.add(new Notification("password", "Senha invalida"));
    }

    @Override
    public boolean isValid() {
        return this.notifications.isEmpty();
    }
}
