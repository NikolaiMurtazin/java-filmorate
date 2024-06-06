package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Collection<User>> getAll() {
        log.info("==> Запрос на вывод всех пользователей");
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> get(@PathVariable long userId) {
        log.info("==> Запрос на вывод пользователя с ID: {}", userId);
        return ResponseEntity.ok(userService.getById(userId));
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        log.info("==> Запрос на добавление пользователя: {}", user);
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> update(@Validated(Update.class) @RequestBody User user) {
        log.info("==> Запрос на изменение пользователя: {}", user);
        return ResponseEntity.ok(userService.update(user));
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable long userId, @PathVariable long friendId) {
        log.info("==> Запрос на добавление в друзья пользователя с ID: {} к пользователю с ID: {}", userId, friendId);
        friendshipService.add(userId, friendId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable long userId, @PathVariable long friendId) {
        log.info("==> Запрос на удаление из друзей пользователя с ID: {} у пользователя с ID: {}", userId, friendId);
        friendshipService.delete(userId, friendId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<Collection<User>> getFriends(@PathVariable long userId) {
        log.info("==> Запрос на вывод всех друзей пользователя с ID: {}", userId);
        return ResponseEntity.ok(userService.getFriends(userId));
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public ResponseEntity<Collection<User>> getCommonFriends(@PathVariable long userId, @PathVariable long otherId) {
        log.info("==> Запрос на вывод общих друзей для пользователей с ID: {} и {}", userId, otherId);
        return ResponseEntity.ok(userService.getCommonFriends(userId, otherId));
    }
}