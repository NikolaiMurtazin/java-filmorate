package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserControllerTest {
    private UserController userController;
    private Validator validator;
    private User user;

    @BeforeEach
    void setUp() {
        userController = new UserController(new UserService(new InMemoryUserStorage(), new InMemoryFilmStorage()));
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        user = User.builder()
                .email("test@example.com")
                .login("test_user")
                .birthday(LocalDate.now().minusYears(20))
                .build();
    }

    @Test
    void createUser_WithValidUser_ShouldReturnUserWithId() {
        User createdUser = userController.create(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations).isEmpty();
        assertThat(user).isEqualTo(createdUser);
    }

    @Test
    void testEmptyEmail() {
        user.setEmail("");

        User createdUser = userController.create(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations).hasSize(1);
    }

    @Test
    void testEmail() {
        user.setEmail("asts");

        User createdUser = userController.create(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations).hasSize(1);
    }

    @Test
    void testEmptyLogin() {
        user.setLogin("");

        User createdUser = userController.create(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations).hasSize(1);
    }

    @Test
    void testEmptyName() {
        User createdUser = userController.create(user);

        assertThat(createdUser.getName()).isEqualTo("test_user");
    }

    @Test
    void testFutureBirthday() {
        user.setBirthday(LocalDate.now().plusDays(1));

        User createdUser = userController.create(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations).hasSize(1);
    }

    @Test
    void changeUser() {
        User createdUser = userController.create(user);
        createdUser.setName("test");
        User changedUser = userController.change(createdUser);

        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations).isEmpty();
        assertThat(user).isEqualTo(changedUser);
    }
}