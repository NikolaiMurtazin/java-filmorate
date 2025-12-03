package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.constraints.Min;
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

import java.util.Collection;

/**
 * REST Controller for managing {@link Film} entities.
 * <p>
 * Handles operations related to films, including creating, reading, updating, deleting,
 * liking, and searching for films.
 * </p>
 */
@RestController
@RequestMapping("/films")
@Validated
@RequiredArgsConstructor
@Slf4j
public class FilmController {

    private final FilmService filmService;

    /**
     * Retrieves all films.
     *
     * @return a collection of all films
     */
    @GetMapping
    public Collection<Film> getAll() {
        log.info("GET /films request");
        Collection<Film> films = filmService.getAll();
        log.info("GET /films response: {}", films.size());
        return films;
    }

    /**
     * Retrieves films by a specific director, sorted by the specified parameter.
     *
     * @param directorId the ID of the director
     * @param sortBy     the sort criteria (e.g., "year" or "likes")
     * @return a collection of films directed by the specified director
     */
    @GetMapping("/director/{directorId}")
    public Collection<Film> getByDirector(@PathVariable int directorId, @RequestParam String sortBy) {
        log.info("GET /films/director/{}/{} request", directorId, sortBy);
        Collection<Film> films = filmService.getByDirector(directorId, sortBy);
        log.info("GET /films/director/{}/{} response: {}", directorId, sortBy, films.size());
        return films;
    }

    /**
     * Retrieves a film by its ID.
     *
     * @param filmId the ID of the film to retrieve
     * @return the requested film
     */
    @GetMapping("/{filmId}")
    public Film getById(@PathVariable long filmId) {
        log.info("GET /films{} request", filmId);
        Film film = filmService.getById(filmId);
        log.info("GET /films{} response: {}", filmId, film);
        return film;
    }

    /**
     * Creates a new film.
     *
     * @param film the film object to be created
     * @return the created film with its assigned ID
     */
    @PostMapping
    public Film create(@Validated @RequestBody Film film) {
        log.info("POST /films request: {}", film);
        Film createdFilm = filmService.create(film);
        log.info("POST /films response: {}", createdFilm);
        return createdFilm;
    }

    /**
     * Updates an existing film.
     *
     * @param film the film object with updated data
     * @return the updated film
     */
    @PutMapping
    public Film update(@Validated @RequestBody Film film) {
        log.info("PUT /films request: {}", film);
        Film updatedFilm = filmService.update(film);
        log.info("PUT /films response: {}", updatedFilm);
        return updatedFilm;
    }

    /**
     * Deletes a film by its ID.
     *
     * @param id the ID of the film to delete
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.info("DELETE /films/{} request", id);
        filmService.delete(id);
        log.info("DELETE /films/{} response: success", id);
    }

    /**
     * Adds a like to a film from a user.
     *
     * @param id     the ID of the film to like
     * @param userId the ID of the user adding the like
     */
    @PutMapping("/{id}/like/{userId}")
    public void like(@PathVariable long id, @PathVariable long userId) {
        log.info("PUT /films/{}/like/{} request", id, userId);
        filmService.like(id, userId);
        log.info("PUT /films/{}/like/{} response: success", id, userId);
    }

    /**
     * Removes a like from a film.
     *
     * @param id     the ID of the film to unlike
     * @param userId the ID of the user removing the like
     */
    @DeleteMapping("/{id}/like/{userId}")
    public void unlike(@PathVariable long id, @PathVariable long userId) {
        log.info("DELETE /films/{}/like/{} request", id, userId);
        filmService.unlike(id, userId);
        log.info("DELETE /films/{}/like/{} response: success", id, userId);
    }

    /**
     * Retrieves a list of the most popular films.
     * <p>
     * Can be filtered by genre ID and year.
     * </p>
     *
     * @param count   the maximum number of films to return (default is 10)
     * @param genreId the ID of the genre to filter by (optional)
     * @param year    the year to filter by (optional, cannot be before 1895)
     * @return a collection of popular films
     */
    @GetMapping("/popular")
    public Collection<Film> getMostPopular(@RequestParam(defaultValue = "10") @Min(0) Integer count,
                                           @RequestParam(required = false) Integer genreId,
                                           @RequestParam(required = false) @Min(1895) Integer year) {
        log.info("GET /films/popular?count={}, genreId={}, year={} request", count, genreId, year);
        Collection<Film> films = filmService.getMostPopular(count, genreId, year);
        log.info("GET /films/popular?count={}, genreId={}, year={} response: {} ", count, genreId, year, films.size());
        return films;
    }

    /**
     * Retrieves common films between two users.
     * <p>
     * Common films are films that both users have liked.
     * </p>
     *
     * @param userId   the ID of the first user
     * @param friendId the ID of the second user
     * @return a collection of common films
     */
    @GetMapping("/common")
    public Collection<Film> getCommonFilms(@RequestParam long userId, @RequestParam long friendId) {
        log.info("GET /films/common?userId={}, friendId={} request", userId, friendId);
        Collection<Film> films = filmService.getCommonFilms(userId, friendId);
        log.info("GET /films/common?userId={}, friendId={} response", userId, friendId);
        return films;
    }

    /**
     * Searches for films by title or director.
     *
     * @param keyword the search query string
     * @param params  the parameters to search by (e.g., "title", "director", or "title,director")
     * @return a collection of films matching the search criteria
     */
    @GetMapping("/search")
    public Collection<Film> search(@RequestParam(name = "query") String keyword,
                                   @RequestParam(name = "by") String params) {
        log.info("GET /films/search?query={}, by={} request", keyword, params);
        Collection<Film> films = filmService.search(keyword, params);
        log.info("GET /films/search?query={}, by={} response: {}", keyword, params, films.size());
        return films;
    }
}