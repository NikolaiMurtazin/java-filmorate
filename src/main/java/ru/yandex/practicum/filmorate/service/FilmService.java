package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film change(Film film) {
        return filmStorage.change(film);
    }

    public Collection<Film> getTopTenFilms(Integer count) {
        return filmStorage.getFilms().values().stream()
                .sorted(Comparator.comparingInt(Film::sizeLikes).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film likeFilm(Long filmId, Long userId) {
        Film film = filmStorage.getFilms().get(filmId);
        User user = userStorage.getUsers().get(userId);
        if (film != null && user != null) {
            film.addLike(user);
        }
        return film;
    }

    public Film unlikeFilm(Long filmId, Long userId) {
        Film film = filmStorage.getFilms().get(filmId);
        User user = userStorage.getUsers().get(userId);
        if (film != null && user != null) {
            film.removeLike(user);
        }
        return film;
    }
}
