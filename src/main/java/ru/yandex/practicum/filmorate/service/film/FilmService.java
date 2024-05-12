package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    Collection<Film> getAll();

    Film get(long filmId);

    Film save(Film film);

    Film update(Film film);

    Film likeFilm(long filmId, long userId);

    Film unlikeFilm(long filmId, long userId);

    Collection<Film> getPopularFilms(int count);
}
