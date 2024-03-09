package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.ArrayList;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private Integer userId = 0;
    private final HashMap<Integer, User> users = new HashMap<>();

    @GetMapping
    public ArrayList<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
       log.info("Получен запрос на добавление нового пользователя");

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        user.setId(generateId());
        users.put(user.getId(), user);

        log.info(String.format("Пользователь %s  с id = %d успешно добавлен", user.getName(), user.getId()));
        return user;
    }

    @PutMapping
    public User change(@Valid @RequestBody User user) {
        log.info("Получен запрос на обновление пользователя");

        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Такого целочисленного идентификатор нет в списке");
        }

        if (user.getLogin().isEmpty()) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);

        log.info(String.format("Пользователь %s  с id = %d успешно обновлен", user.getName(), user.getId()));
        return user;
    }

    private Integer generateId() {
        return ++userId;
    }
}
