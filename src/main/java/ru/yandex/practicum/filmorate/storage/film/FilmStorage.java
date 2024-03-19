package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Map;

public interface FilmStorage {
    Collection<Film> findAll();

    Film create(Film film);

    Film change(Film film);

    Map<Long, Film> getFilms();
}
