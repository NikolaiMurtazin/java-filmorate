package ru.yandex.practicum.filmorate.repository.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Genre} persistence operations.
 * <p>
 * Provides read access to genre reference data. Genres are typically static data
 * used to categorize films.
 * </p>
 */
public interface GenreRepository {

    /**
     * Finds a genre by its unique identifier.
     *
     * @param genreId the ID of the genre to retrieve
     * @return an {@link Optional} containing the genre if found, or empty if not
     */
    Optional<Genre> getById(int genreId);

    /**
     * Retrieves all genres available in the storage.
     *
     * @return a collection of all genres
     */
    Collection<Genre> getAll();

    /**
     * Counts the number of genres that exist in the storage matching the provided list of IDs.
     * <p>
     * This method is efficiently used for validation to ensure that all genre IDs
     * associated with a film actually exist in the database.
     * </p>
     *
     * @param genreIds the list of genre IDs to check
     * @return the count of found genres matching the IDs
     */
    int countMatchingGenres(List<Integer> genreIds);
}