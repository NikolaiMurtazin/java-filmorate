package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

public interface UserStorage {
    Collection<User> getAllUsers();

    User createUser(User user);

    User changeUser(User user);

    Collection<User> findMutualFriends(Long userId, Long friendId);

    Collection<User> getFriends(Long userId);

    User addToFriends(Long userId, Long friendId);

    User removeFromFriends(Long userId, Long friendId);

    Map<Long, User> getUsers();
}
