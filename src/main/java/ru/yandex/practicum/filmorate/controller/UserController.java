package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private Integer id = 1;
    private final HashMap<Integer, User> users = new HashMap<>();
    @GetMapping
    public Set<User> findAll() {
        return new HashSet<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(id);
        users.put(id, user);
        generateId();
        return user;
    }

    @PutMapping
    public User change(@Valid @RequestBody User user) {
        if (user.getId() == 0) {
            throw new ValidationException("Целочисленный идентификатор не может быть 0");
        }
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Такого целочисленного идентификатор нет в списке");
        }
        if (user.getLogin().isEmpty()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    private void generateId() {
        id++;
    }
}
