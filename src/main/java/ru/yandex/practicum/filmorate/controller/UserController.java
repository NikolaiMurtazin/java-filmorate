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
import ru.yandex.practicum.filmorate.service.friendship.FriendshipServiceImpl;
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
    private final FriendshipServiceImpl friendshipService;

    @GetMapping
    public Collection<User> getAll() {
        log.info("==> Запрос на вывод всех пользователей");
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public User getById(@PathVariable long userId) {
        log.info("==> Запрос на вывод пользователя с ID: {}", userId);
        return userService.getById(userId);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("==> Запрос на добавление пользователя: {}", user);
        return userService.create(user);
    }

    @PutMapping
    public User update(@Validated(Update.class) @RequestBody User user) {
        log.info("==> Запрос на изменение пользователя: {}", user);
        return userService.update(user);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable long userId, @PathVariable long friendId) {
        log.info("==> Запрос на добавление в друзья пользователя с ID: {} к пользователю с ID: {}", userId, friendId);
        friendshipService.add(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable long userId, @PathVariable long friendId) {
        log.info("==> Запрос на удаление из друзей пользователя с ID: {} у пользователя с ID: {}", userId, friendId);
        friendshipService.delete(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public Collection<User> getFriends(@PathVariable long userId) {
        log.info("==> Запрос на вывод всех друзей пользователя с ID: {}", userId);
        return userService.getFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable long userId, @PathVariable long otherId) {
        log.info("==> Запрос на вывод общих друзей для пользователей с ID: {} и {}", userId, otherId);
        return userService.getCommonFriends(userId, otherId);
    }
}