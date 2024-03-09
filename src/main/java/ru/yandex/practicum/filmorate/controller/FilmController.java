package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private Integer filmId = 0;
    private final HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping
    public ArrayList<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление нового фильма");

        checkMoviesBirthdayDate(film.getReleaseDate());

        film.setId(generateId());
        films.put(film.getId(), film);

        log.info(String.format("Фильм %s  с id = %d успешно добавлен", film.getName(), film.getId()));
        return film;
    }

    @PutMapping
    public Film change(@Valid @RequestBody Film film) {
            log.info("Получен запрос на обновление фильма");

        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Такого id нет в списке");
        }

        checkMoviesBirthdayDate(film.getReleaseDate());

        films.put(film.getId(), film);

        log.info(String.format("Фильм %s с id = %d успешно обновлен", film.getName(), film.getId()));
        return film;
    }

    private Integer generateId() {
        return ++filmId;
    }

    private void checkMoviesBirthdayDate(LocalDate releaseDate) {
        LocalDate happyBirthdayMovie = LocalDate.of(1895, 12, 28);

        if (releaseDate.isBefore(happyBirthdayMovie)) {
            throw new ValidationException("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
        }
    }
}

/*
    В задании написано, что хорошей практикой будет логирование ошибок, но я же пробрасываю exception.
    Еще я в аннотации вписал message. Так для чего использовать логирование при ошибках?
 */

