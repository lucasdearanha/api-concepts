package com.example.demo.commands;

import com.example.demo.validations.Notification;
import com.example.demo.validations.ValidationUtils;

import java.util.HashSet;
import java.util.Set;

public class UserLoginCommand implements Command {
    private String email;
    private String password;
    private Set<Notification> notifications = new HashSet<>();

    public UserLoginCommand() {
    }

    public UserLoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
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
        if (!ValidationUtils.isValidEmail(email) || !ValidationUtils.isValidPassword(password, 8, 30))
            this.notifications.add(new Notification("Email/Password", "Email ou senha invalidos"));
    }

    @Override
    public boolean isValid() {
        return this.notifications.isEmpty();
    }
}
