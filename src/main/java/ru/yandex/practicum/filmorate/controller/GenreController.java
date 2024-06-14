package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreService;

import java.util.Collection;

@RestController
@RequestMapping("/genres")
@Validated
@Slf4j
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public Collection<Genre> getAll() {
        log.info("==> Запрос на вывод всех жанров");
        return genreService.getAll();
    }

    @GetMapping("/{id}")
    public Genre getById(@PathVariable("id") Integer genreId) {
        Genre genre = genreService.getById(genreId);
        log.info("==> Запрос на вывод жанра: {}", genre);
        return genreService.getById(genreId);
    }
}
