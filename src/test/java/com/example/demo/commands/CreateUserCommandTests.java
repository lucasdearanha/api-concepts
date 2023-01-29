package com.example.demo.commands;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CreateUserCommandTests {
    private final CreateUserCommand userCommandValid = new CreateUserCommand(
            "Test",
            "Test",
            "test@email.com",
            "Password@56"
    );

    private final CreateUserCommand userCommandInvalidFourErrors = new CreateUserCommand(
            "Te",
            "Te",
            "testemail.com",
            "password!56"
    );

    private final CreateUserCommand userCommandInvalidTwoErrors = new CreateUserCommand(
            "Te",
            "Te",
            "test@email.com",
            "Password@56"
    );

    @Test
    public void CreateUserCommand_Validate_Should_Return_Command_Is_Valid_And_Zero_Notifications() {
        userCommandValid.validate();

        Assertions.assertThat(userCommandValid.isValid()).isTrue();
        Assertions.assertThat(userCommandValid.getNotifications().size()).isEqualTo(0);
    }

    @Test
    public void CreateUserCommand_Validate_Should_Return_Command_Is_Invalid_And_Four_Notifications() {
        userCommandInvalidFourErrors.validate();

        Assertions.assertThat(userCommandInvalidFourErrors.isValid()).isFalse();
        Assertions.assertThat(userCommandInvalidFourErrors.getNotifications().size()).isEqualTo(4);
    }

    @Test
    public void CreateUserCommand_Validate_Should_Return_Command_Is_Invalid_And_Two_Notifications() {
        userCommandInvalidTwoErrors.validate();

        Assertions.assertThat(userCommandInvalidTwoErrors.isValid()).isFalse();
        Assertions.assertThat(userCommandInvalidTwoErrors.getNotifications().size()).isEqualTo(2);
    }
}
