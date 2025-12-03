package ru.yandex.practicum.filmorate.repository.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository interface for managing {@link Mpa} reference data.
 * <p>
 * Provides read-only operations for accessing Motion Picture Association ratings
 * from the storage.
 * </p>
 */
public interface MpaRepository {

    /**
     * Finds an MPA rating by its unique identifier.
     *
     * @param mpaId the ID of the MPA rating to retrieve
     * @return an {@link Optional} containing the rating if found, or empty if not
     */
    Optional<Mpa> getById(int mpaId);

    /**
     * Retrieves all available MPA ratings from the storage.
     *
     * @return a collection of all MPA ratings
     */
    Collection<Mpa> getAll();
}
