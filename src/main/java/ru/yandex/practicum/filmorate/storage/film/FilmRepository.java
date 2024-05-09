package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface FilmRepository {
    Collection<Film> getAll();

    Optional<Film> get(long filmId);

    Film create(Film film);

    Film update(Film film);

    Film getFilm(Long filmIf);

    void likeFilm(Film film, User user);

    void unlikeFilm(Film film, User user);

    Collection<Film> getPopularFilms(int count);
}
