package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

/**
 * Service interface for managing {@link Film} business logic.
 * <p>
 * This interface defines the core operations for movies, including CRUD,
 * social interactions (likes), and complex retrieval queries (search, popularity, recommendations).
 * It acts as the transaction boundary and validation layer before accessing the {@link ru.yandex.practicum.filmorate.repository.FilmRepository}.
 * </p>
 */
public interface FilmService {

    /**
     * Retrieves all films in the system.
     *
     * @return a collection of all films
     */
    Collection<Film> getAll();

    /**
     * Creates a new film.
     * <p>
     * Validates business rules (e.g., release date) before saving.
     * </p>
     *
     * @param film the film object to create
     * @return the created film with a generated ID
     */
    Film create(Film film);

    /**
     * Updates an existing film.
     *
     * @param film the film object with updated data
     * @return the updated film
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the film ID does not exist
     */
    Film update(Film film);

    /**
     * Deletes a film by its ID.
     *
     * @param filmId the ID of the film to delete
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the film ID does not exist
     */
    void delete(long filmId);

    /**
     * Adds a like to a film from a user.
     * <p>
     * This action usually triggers an event creation in the user's feed.
     * </p>
     *
     * @param filmId the ID of the film
     * @param userId the ID of the user adding the like
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the film or user does not exist
     */
    void like(long filmId, long userId);

    /**
     * Removes a like from a film.
     * <p>
     * This action usually triggers an event creation (REMOVE LIKE) in the user's feed.
     * </p>
     *
     * @param filmId the ID of the film
     * @param userId the ID of the user removing the like
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the film or user does not exist
     */
    void unlike(long filmId, long userId);

    /**
     * Retrieves the most popular films with optional filters.
     *
     * @param count   the maximum number of films to return (limit)
     * @param genreId the optional genre ID to filter by (can be null)
     * @param year    the optional release year to filter by (can be null)
     * @return a collection of the top-rated films matching the criteria
     */
    Collection<Film> getMostPopular(Integer count, Integer genreId, Integer year);

    /**
     * Retrieves a specific film by its ID.
     *
     * @param filmId the ID of the film
     * @return the requested film
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the film is not found
     */
    Film getById(long filmId);

    /**
     * Retrieves films directed by a specific director, sorted by a parameter.
     *
     * @param directorId the ID of the director
     * @param sortBy     the sort criteria (e.g., "year" or "likes")
     * @return a collection of films
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the director does not exist
     */
    Collection<Film> getByDirector(int directorId, String sortBy);

    /**
     * Retrieves common films between two users.
     * <p>
     * Common films are those that both users have liked.
     * </p>
     *
     * @param userId   the ID of the first user
     * @param friendId the ID of the second user
     * @return a collection of films liked by both users
     */
    Collection<Film> getCommonFilms(long userId, long friendId);

    /**
     * Searches for films based on a keyword and specific fields.
     *
     * @param keyword the substring to search for
     * @param params  comma-separated list of fields to search in (e.g., "director,title")
     * @return a collection of matching films
     */
    Collection<Film> search(String keyword, String params);
}