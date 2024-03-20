package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
public class UserService {
    private final UserStorage userStorage;

    private final FilmStorage filmStorage;

    @Autowired
    public UserService(UserStorage userStorage, FilmStorage filmStorage) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User changeUser(User user) {
      return userStorage.changeUser(user);
    }

    public User removeUser(Long userId) {
        User user = userStorage.getUsers().get(userId);
        if (user != null) {
            for (Long friendId : user.getFriends()) {
                User friend = userStorage.getUsers().get(friendId);
                friend.getFriends().remove(userId);
            }
            for (Long filmId : user.getLikeFilms()) {
                Film film = filmStorage.getFilms().get(filmId);
                film.getLikes().remove(userId);
            }
        }
        return user;
    }

    public Collection<User> findMutualFriends(Long userId, Long friendId) {
        return userStorage.findMutualFriends(userId, friendId);
    }

    public Collection<User> getFriends(Long userId) {
        return userStorage.getFriends(userId);
    }

    public User addToFriends(Long userId, Long friendId) {
        return userStorage.addToFriends(userId, friendId);
    }

    public User removeFromFriends(Long userId, Long friendId) {
        return userStorage.removeFromFriends(userId, friendId);
    }
}
