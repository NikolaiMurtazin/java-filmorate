package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

/**
 * Service interface for managing {@link Genre} reference data.
 * <p>
 * This interface handles the retrieval of film genres (e.g., Comedy, Drama).
 * Since genres are typically static reference data, this service primarily provides read-only access.
 * </p>
 */
public interface GenreService {

    /**
     * Retrieves all available genres.
     *
     * @return a collection of all genres
     */
    Collection<Genre> getAll();

    /**
     * Retrieves a specific genre by its unique identifier.
     *
     * @param genreId the ID of the genre (renamed from 'mpaId' for correctness)
     * @return the requested genre
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the genre is not found
     */
    Genre getById(int genreId);
}
