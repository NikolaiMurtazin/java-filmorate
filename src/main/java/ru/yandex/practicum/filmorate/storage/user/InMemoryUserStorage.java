package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.HashMap;

@Component
public class InMemoryUserStorage implements UserStorage {
    private Long userId = 0L;

    private final HashMap<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User create(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        user.setUserId(generateId());
        users.put(user.getUserId(), user);

        return user;
    }

    @Override
    public User change(User user) {
        if (!users.containsKey(user.getUserId())) {
            throw new ValidationException("Такого целочисленного идентификатор нет в списке");
        }

        if (user.getLogin().isEmpty()) {
            user.setName(user.getLogin());
        }

        users.put(user.getUserId(), user);

        return user;
    }

    @Override
    public HashMap<Long, User> getUsers() {
        return users;
    }

    private Long generateId() {
        return ++userId;
    }
}
