package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    Collection<Film> getAll();

    Film getById(long filmId);

    Film create(Film film);

    Film update(Film film);

    Collection<Film> getPopularFilms(int count);
}
