package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.director.DirectorService;

import java.util.Collection;

/**
 * REST Controller for managing {@link Director} entities.
 * <p>
 * Handles CRUD operations: create, read (all or by ID), update, and delete directors.
 * </p>
 */
@RestController
@RequestMapping("/directors")
@RequiredArgsConstructor
@Slf4j
public class DirectorController {

    private final DirectorService directorService;

    /**
     * Retrieves all directors.
     *
     * @return a collection of all directors
     */
    @GetMapping
    public Collection<Director> getAll() {
        log.info("GET /directors request");
        Collection<Director> directors = directorService.getAll();
        log.info("GET /directors response: {}", directors.size());
        return directors;
    }

    /**
     * Retrieves a specific director by their ID.
     *
     * @param directorId the ID of the director to retrieve
     * @return the requested director
     */
    @GetMapping("/{directorId}")
    public Director getById(@PathVariable int directorId) {
        log.info("GET /directors/{} request", directorId);
        Director director = directorService.getById(directorId);
        log.info("GET /directors/{} response: {}", directorId, director);
        return director;
    }

    /**
     * Creates a new director.
     * <p>
     * The request body must contain valid director data.
     * </p>
     *
     * @param director the director object to be created
     * @return the created director with its assigned ID
     */
    @PostMapping
    public Director create(@Validated @RequestBody Director director) {
        log.info("POST /directors request: {}", director);
        Director createdDirector = directorService.create(director);
        log.info("POST /directors response: {}", createdDirector);
        return createdDirector;
    }

    /**
     * Updates an existing director.
     * <p>
     * The request body must contain valid director data, including the ID of the director to update.
     * </p>
     *
     * @param director the director object with updated data
     * @return the updated director
     */
    @PutMapping
    public Director update(@Validated @RequestBody Director director) {
        log.info("PUT /directors request: {}", director);
        Director updatedDirector = directorService.update(director);
        log.info("PUT /directors response: {}", updatedDirector);
        return updatedDirector;
    }

    /**
     * Deletes a director by their ID.
     * <p>
     * Returns HTTP 204 No Content upon successful deletion.
     * </p>
     *
     * @param directorId the ID of the director to delete
     */
    @DeleteMapping("/{directorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@PathVariable int directorId) {
        log.info("DELETE /directors/{} request", directorId);
        directorService.removeById(directorId);
        log.info("DELETE /directors/{} response", directorId);
    }
}