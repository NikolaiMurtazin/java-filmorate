package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.validator.Update;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    Collection<User> getAll() {
        log.info("==> Запрос на вывод всех пользователей");
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    User get(@PathVariable long userId) {
        User user = userService.get(userId);
        log.info("==> Запрос на вывод пользователя: {}", user);
        return user;
    }

    @PostMapping
    User save(@Valid @RequestBody User user) {
        log.info("==> Запрос на добавление пользователя: {}", user);
        return userService.save(user);
    }

    @PutMapping
    User update(@Validated(Update.class) @RequestBody User user) {
        log.info("==> Запрос на изменение пользователя: {}", user);
        return userService.update(user);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    void addFriend(@PathVariable long userId, @PathVariable long friendId) {
        log.info("==> Запрос на добавление в друзья");
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    void deleteFriend(@PathVariable long userId, @PathVariable long friendId) {
        log.info("==> Запрос на удаление из друзей");
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    Collection<User> getFriends(@PathVariable long userId) {
        log.info("==> Запрос на вывод всех друзей");
        return userService.getFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    Collection<User> getCommonFriends(@PathVariable long userId, @PathVariable long otherId) {
        log.info("==> Запрос на вывод общих друзей");
        return userService.getCommonFriends(userId, otherId);
    }
}