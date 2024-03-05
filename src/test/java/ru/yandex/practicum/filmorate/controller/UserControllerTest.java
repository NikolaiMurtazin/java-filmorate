package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Test
    void createUser_WithValidUser_ShouldReturnUserWithId() {
        User user = new User("test@example.com", "test_user", LocalDate.now().minusYears(20));
        User createdUser = userController.create(user);

        assertEquals(1, createdUser.getId());
        assertEquals("test@example.com", createdUser.getEmail());
        assertEquals("test_user", createdUser.getLogin());
        assertEquals(LocalDate.now().minusYears(20), createdUser.getBirthday());
    }

    @Test
    void changeUser_WithValidUser_ShouldReturnUser() {
        User user = new User("test@example.com", "test_user", LocalDate.now().minusYears(20));
        User createdUser = userController.create(user);
        createdUser.setName("test");
        User changedUser = userController.change(createdUser);

        assertEquals(user, changedUser);
    }
}