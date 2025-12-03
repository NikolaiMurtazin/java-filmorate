package ru.yandex.practicum.filmorate.repository.director;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Director} persistence operations.
 * <p>
 * Defines the standard CRUD operations and specific queries for accessing director data
 * in the underlying storage (e.g., database).
 * </p>
 */
public interface DirectorRepository {

    /**
     * Retrieves all directors from the storage.
     *
     * @return a collection of all directors
     */
    Collection<Director> getAll();

    /**
     * Finds a director by their unique identifier.
     *
     * @param directorId the ID of the director to retrieve
     * @return an {@link Optional} containing the director if found, or empty if not
     */
    Optional<Director> getById(int directorId);

    /**
     * Saves a new director to the storage.
     *
     * @param director the director object to be created
     * @return the saved director with its generated ID
     */
    Director create(Director director);

    /**
     * Updates an existing director's information.
     *
     * @param director the director object with updated data
     * @return the updated director
     */
    Director update(Director director);

    /**
     * Removes a director from the storage by their ID.
     *
     * @param directorId the ID of the director to delete
     */
    void removeById(int directorId);

    /**
     * Counts the number of directors that exist in the storage matching the provided list of IDs.
     * <p>
     * This method is typically used for validation purposes to ensure that a list of
     * referenced director IDs actually exists in the database.
     * </p>
     *
     * @param directorIds the list of director IDs to check
     * @return the count of found directors matching the IDs
     */
    int countMatchingDirectors(List<Integer> directorIds);
}