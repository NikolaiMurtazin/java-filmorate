package ru.yandex.practicum.filmorate.repository.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface FilmRepository {
    Collection<Film> getAll();

    Film get(long filmId);

    Film create(Film film);

    Film update(Film film);

    Film likeFilm(Film film, User user);

    Film unlikeFilm(Film film, User user);

    Collection<Film> getPopularFilms(int count);
}
