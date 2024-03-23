package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.HashMap;


@Component
public class InMemoryUserStorage implements UserStorage {
    private Long userId = 0L;

    private final HashMap<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        user.setId(generateId());
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User changeUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Такого целочисленного идентификатор нет в списке");
        }

        if (user.getLogin().isEmpty()) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User getUser(Long userId) {
        User user = users.get(userId);
        if (user == null) {
            throw new NotFoundException("User не найден.");
        }
        return user;
    }

    private Long generateId() {
        return ++userId;
    }
}
