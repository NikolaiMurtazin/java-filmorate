package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.validator.Update;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@Validated
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> getAll() {
        log.info("==> Запрос на вывод всех фильмов");
        return filmService.getAll();
    }

    @GetMapping("/{filmId}")
    Film get(@PathVariable long filmId) {
        Film film = filmService.get(filmId);
        log.info("==> Запрос на вывод фильма: {}", film);
        return film;
    }

    @PostMapping
    public Film save(@Valid @RequestBody Film film) {
        log.info("==> Запрос на добавление фильма: {}", film);
        return filmService.save(film);
    }

    @PutMapping
    public Film update(@Validated(Update.class) @RequestBody Film film) {
        log.info("==> Запрос на изменение фильма: {}", film);
        return filmService.update(film);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film likeFilm(@PathVariable("filmId") long filmId, @PathVariable("userId") long userId) {
        log.info("==> Запрос на проставление лайка фильма");
        return filmService.likeFilm(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film unlikeFilm(@PathVariable("filmId") long filmId, @PathVariable("userId") long userId) {
        log.info("==> Запрос на удаления лайка фильма");
        return filmService.unlikeFilm(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("==> Запрос на вывод популярных фильмов");
        return filmService.getPopularFilms(count);
    }
}