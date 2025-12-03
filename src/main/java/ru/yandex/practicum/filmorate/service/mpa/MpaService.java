package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

/**
 * Service interface for managing {@link Mpa} (Motion Picture Association) ratings.
 * <p>
 * Provides read-only access to the list of film ratings (e.g., G, PG, R).
 * This data is typically static and serves as reference material for films.
 * </p>
 */
public interface MpaService {

    /**
     * Retrieves all available MPA ratings.
     *
     * @return a collection of all ratings
     */
    Collection<Mpa> getAll();

    /**
     * Retrieves a specific MPA rating by its unique identifier.
     *
     * @param mpaId the ID of the rating
     * @return the requested MPA rating
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the rating is not found
     */
    Mpa getById(int mpaId);
}