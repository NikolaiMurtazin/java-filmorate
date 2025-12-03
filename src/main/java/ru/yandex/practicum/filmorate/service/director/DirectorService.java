package ru.yandex.practicum.filmorate.service.director;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.Collection;

/**
 * Service interface for managing {@link Director} business logic.
 * <p>
 * This interface defines the contract for handling director-related operations,
 * acting as a bridge between the controller/API layer and the persistence layer.
 * It is responsible for validation and throwing business exceptions (e.g., if a director is not found).
 * </p>
 */
public interface DirectorService {

    /**
     * Retrieves all directors.
     *
     * @return a collection of all directors
     */
    Collection<Director> getAll();

    /**
     * Retrieves a specific director by their ID.
     *
     * @param directorId the ID of the director
     * @return the requested director
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the director is not found
     */
    Director getById(int directorId);

    /**
     * Creates a new director.
     *
     * @param director the director data to create
     * @return the created director with its assigned ID
     */
    Director create(Director director);

    /**
     * Updates an existing director.
     *
     * @param director the director data to update
     * @return the updated director
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the director ID does not exist
     */
    Director update(Director director);

    /**
     * Removes a director by their ID.
     *
     * @param directorId the ID of the director to remove
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the director is not found
     */
    void removeById(int directorId);
}