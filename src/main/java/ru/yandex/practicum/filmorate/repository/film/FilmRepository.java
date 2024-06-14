package ru.yandex.practicum.filmorate.repository.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmRepository {
    Collection<Film> getAll();

    Optional<Film> getById(long filmId);

    Film create(Film film);

    Film update(Film film);

    Collection<Film> getPopularFilms(int count);
}
