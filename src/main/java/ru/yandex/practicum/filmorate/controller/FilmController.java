package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import ru.yandex.practicum.filmorate.service.filmLike.FilmLikeService;
import ru.yandex.practicum.filmorate.validator.Update;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@Validated
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    private final FilmLikeService likeFilmService;

    @GetMapping
    public ResponseEntity<Collection<Film>> getAll() {
        log.info("==> Запрос на вывод всех фильмов");
        Collection<Film> films = filmService.getAll();
        return ResponseEntity.ok(films);
    }

    @GetMapping("/{filmId}")
    public ResponseEntity<Film> getById(@PathVariable long filmId) {
        log.info("==> Запрос на вывод фильма с ID: {}", filmId);
        Film film = filmService.getById(filmId);
        return ResponseEntity.ok(film);
    }

    @PostMapping
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        log.info("==> Запрос на добавление фильма: {}", film);
        Film createdFilm = filmService.create(film);
        return new ResponseEntity<>(createdFilm, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Film> update(@Validated(Update.class) @RequestBody Film film) {
        log.info("==> Запрос на изменение фильма: {}", film);
        Film updatedFilm = filmService.update(film);
        return ResponseEntity.ok(updatedFilm);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public ResponseEntity<Void> addLikeToFilm(@PathVariable long filmId, @PathVariable long userId) {
        log.info("==> Запрос на проставление лайка фильму с ID: {} от пользователя с ID: {}", filmId, userId);
        likeFilmService.addLikeToFilm(filmId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public ResponseEntity<Void> deleteLikeFromFilm(@PathVariable long filmId, @PathVariable long userId) {
        log.info("==> Запрос на удаление лайка фильма с ID: {} пользователем с ID: {}", filmId, userId);
        likeFilmService.deleteLikeFromFilm(filmId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/popular")
    public ResponseEntity<Collection<Film>> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("==> Запрос на вывод популярных фильмов, количество: {}", count);
        Collection<Film> popularFilms = filmService.getPopularFilms(count);
        return ResponseEntity.ok(popularFilms);
    }
}