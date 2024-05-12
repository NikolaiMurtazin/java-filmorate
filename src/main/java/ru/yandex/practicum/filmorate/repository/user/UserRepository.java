package ru.yandex.practicum.filmorate.repository.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    Collection<User> getAll();

    Optional<User> get(long userId);

    User create(User user);

    User update(User user);

    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);

    Collection<User> getCommonFriends(User user, User friend);

    Collection<User> getFriends(User user);
}
