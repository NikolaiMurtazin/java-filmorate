package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmController {
    private static final int MAX_LENGTH = 200;
    private static final LocalDate HAPPY_BIRTHDAY_MOVIE = LocalDate.of(1895, 12, 28);
    private Integer id = 1;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final HashMap<Integer, Film> films = new HashMap<>();
    @GetMapping
    public Set<Film> findAll() {
        return new HashSet<>(films.values());
    }
    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        int lengthDescription = film.getDescription().length();
        LocalDate releaseDate = LocalDate.parse(film.getReleaseDate(), formatter);
        if (lengthDescription > MAX_LENGTH) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (releaseDate.isBefore(HAPPY_BIRTHDAY_MOVIE)) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        film.setId(id);
        films.put(id, film);
        generateId();
        return film;
    }

    @PutMapping
    public Film change(@Valid @RequestBody Film film) {
        int lengthDescription = film.getDescription().length();
        LocalDate releaseDate = LocalDate.parse(film.getReleaseDate(), formatter);
        if (film.getId() == 0) {
            throw new ValidationException("Целочисленный идентификатор не может быть 0");
        }
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Такого целочисленного идентификатор нет в списке");
        }
        if (lengthDescription > MAX_LENGTH) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (releaseDate.isBefore(HAPPY_BIRTHDAY_MOVIE)) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        films.put(film.getId(), film);
        return film;
    }

    private void generateId() {
        id++;
    }
}
