package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Long filmId = 0L;

    private final HashMap<Long, Film> films = new HashMap<>();

    @Override
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @Override
    public Film createFilm(Film film) {

        checkMoviesBirthdayDate(film.getReleaseDate());

        film.setId(generateId());
        films.put(film.getId(), film);

        return film;
    }

    @Override
    public Film changeFilm(Film film) {

        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Такого id нет в списке");
        }

        checkMoviesBirthdayDate(film.getReleaseDate());

        films.put(film.getId(), film);

        return film;
    }

    @Override
    public Film getFilm(Long filmId) {
        Film film = films.get(this.filmId);
        if (film == null) {
            throw new NotFoundException("User не найден.");
        }
        return film;
    }

    private Long generateId() {
        return ++filmId;
    }

    private void checkMoviesBirthdayDate(LocalDate releaseDate) {
        LocalDate happyBirthdayMovie = LocalDate.of(1895, 12, 28);

        if (releaseDate.isBefore(happyBirthdayMovie)) {
            throw new ValidationException("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
        }
    }
}
