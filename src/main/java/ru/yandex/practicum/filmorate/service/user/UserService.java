package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    Collection<User> getAll();

    User get(long userId);

    User save(User user);

    User update(User user);

    Collection<User> getCommonFriends(long userId, long friendId);

    Collection<User> getFriends(long userId);

    void addFriend(long userId, long friendId);

    void deleteFriend(long userId, long friendId);
}
