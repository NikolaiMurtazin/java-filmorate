package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.repository.user.InMemoryUserRepository;

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
        userController = new UserController(new UserService(new InMemoryUserRepository()));
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        user = User.builder()
                .email("test@example.com")
                .login("test_user")
                .birthday(LocalDate.now().minusYears(20))
                .build();
    }

    @Test
    void saveUser_WithValidUser_ShouldReturnUserWithId() {
        User createdUser = userController.save(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations).isEmpty();
        assertThat(user).isEqualTo(createdUser);
    }

    @Test
    void testEmptyEmail() {
        user.setEmail("");

        User createdUser = userController.save(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations).hasSize(1);
    }

    @Test
    void testEmail() {
        user.setEmail("asts");

        User createdUser = userController.save(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations).hasSize(1);
    }

    @Test
    void testEmptyLogin() {
        user.setLogin("");

        User createdUser = userController.save(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations).hasSize(1);
    }

    @Test
    void testEmptyName() {
        User createdUser = userController.save(user);

        assertThat(createdUser.getName()).isEqualTo("test_user");
    }

    @Test
    void testFutureBirthday() {
        user.setBirthday(LocalDate.now().plusDays(1));

        User createdUser = userController.save(user);
        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations).hasSize(1);
    }

    @Test
    void updateUser() {
        User createdUser = userController.save(user);
        createdUser.setName("test");
        userController.update(createdUser);

        Set<ConstraintViolation<User>> violations = validator.validate(createdUser);

        assertThat(violations).isEmpty();
        assertThat(user).isEqualTo(createdUser);
    }
}