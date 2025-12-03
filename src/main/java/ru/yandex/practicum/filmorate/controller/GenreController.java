package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreService;

import java.util.Collection;

/**
 * REST Controller for managing {@link Genre} entities.
 * <p>
 * This controller provides endpoints to retrieve genre information, which serves as
 * reference data for films.
 * </p>
 */
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
@Slf4j
public class GenreController {

    private final GenreService genreService;

    /**
     * Retrieves all available film genres.
     *
     * @return a collection of all genres
     */
    @GetMapping
    public Collection<Genre> getAll() {
        log.info("GET /genres request");
        Collection<Genre> genres = genreService.getAll();
        log.info("GET /genres response: {}", genres.size());
        return genres;
    }

    /**
     * Retrieves a specific genre by its ID.
     *
     * @param genreId the ID of the genre to retrieve
     * @return the requested genre object
     */
    @GetMapping("/{genreId}")
    public Genre getById(@PathVariable int genreId) {
        log.info("GET /genres/{} request", genreId);
        Genre genre = genreService.getById(genreId);
        log.info("GET /genres/{} response: {}", genreId, genre);
        return genre;
    }
}