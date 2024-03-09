package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserControllerTest {
    private UserController userController;
    private Validator validator;

    @BeforeEach
    void setUp() {
        userController = new UserController();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void createUser_WithValidUser_ShouldReturnUserWithId() {
        User user = User.builder()
                .email("test@example.com")
                .login("test_user")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        User createdUser = userController.create(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations.size()).isEqualTo(0);
        assertThat(user).isEqualTo(createdUser);
    }

    @Test
    void testEmptyEmail() {
        UserController userController = new UserController();
        User user = User.builder()
                .email("")
                .login("testLogin")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        User createdUser = userController.create(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void testEmail() {
        UserController userController = new UserController();
        User user = User.builder()
                .email("asts")
                .login("testLogin")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        User createdUser = userController.create(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void testEmptyLogin() {
        UserController userController = new UserController();
        User user = User.builder()
                .email("test@example.com")
                .login("")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        User createdUser = userController.create(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void testEmptyName() {
        UserController userController = new UserController();
        User user = User.builder()
                .email("test@example.com")
                .login("testLogin")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        User createdUser = userController.create(user);

        assertThat(createdUser.getName()).isEqualTo("testLogin");
    }

    @Test
    void testFutureBirthday() {
        UserController userController = new UserController();
        User user = User.builder()
                .email("test@example.com")
                .login("testLogin")
                .name("Test User")
                .birthday(LocalDate.now().plusDays(1))
                .build();

        User createdUser = userController.create(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void changeUser() {
        User user = User.builder()
                .email("test@example.com")
                .login("test_user")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        User createdUser = userController.create(user);
        createdUser.setName("test");
        User changedUser = userController.change(createdUser);

        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations.size()).isEqualTo(0);
        assertThat(user).isEqualTo(changedUser);
    }
}