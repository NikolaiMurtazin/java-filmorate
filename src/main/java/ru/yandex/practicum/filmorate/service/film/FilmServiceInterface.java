package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmServiceInterface {
    Collection<Film> getAll();

    Film get(long filmId);

    Film save(Film film);

    Film update(Film film);

    void likeFilm(Long filmId, Long userId);

    void unlikeFilm(Long filmId, Long userId);

    Collection<Film> getPopularFilms(int count);
}
