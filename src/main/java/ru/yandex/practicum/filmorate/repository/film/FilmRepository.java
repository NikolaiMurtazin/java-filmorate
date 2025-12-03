package ru.yandex.practicum.filmorate.repository.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * Repository interface for managing {@link Film} persistence operations.
 * <p>
 * Defines the contract for accessing and modifying film data, including complex queries
 * for filtering, searching, and recommendation algorithms.
 * </p>
 */
public interface FilmRepository {

    /**
     * Saves a new film to the storage.
     *
     * @param film the film object to be created
     * @return the saved film with its generated ID
     */
    Film create(Film film);

    /**
     * Updates an existing film's information.
     *
     * @param film the film object with updated data
     * @return the updated film
     */
    Film update(Film film);

    /**
     * Retrieves all films from the storage.
     *
     * @return a collection of all films
     */
    Collection<Film> getAll();

    /**
     * Finds a film by its unique identifier.
     *
     * @param id the ID of the film to retrieve
     * @return an {@link Optional} containing the film if found, or empty if not
     */
    Optional<Film> getById(Long id);

    /**
     * Retrieves all films directed by a specific director, sorted by a given criterion.
     *
     * @param directorId the ID of the director
     * @param sortBy     the sorting criteria (e.g., "year" or "likes")
     * @return a collection of films
     */
    Collection<Film> getByDirector(int directorId, String sortBy);

    /**
     * Deletes a film by its ID.
     *
     * @param id the ID of the film to delete
     */
    void delete(Long id);

    /**
     * Retrieves the most popular films based on user likes.
     *
     * @param count the maximum number of films to return
     * @return a collection of the top-rated films
     */
    Collection<Film> getMostPopular(int count);

    /**
     * Retrieves films that are liked by both specified users.
     * <p>
     * Typically used to show common interests between a user and their friend.
     * </p>
     *
     * @param userId   the ID of the first user
     * @param friendId the ID of the second user
     * @return a collection of common films
     */
    Collection<Film> getCommonFilms(long userId, long friendId);

    /**
     * Searches for films by keyword.
     *
     * @param keyword      the search string (substring match)
     * @param searchParams a set of fields to search in (e.g., "director", "title")
     * @return a collection of films matching the search criteria
     */
    Collection<Film> search(String keyword, Set<String> searchParams);

    /**
     * Retrieves the most popular films released in a specific year.
     *
     * @param year the release year to filter by
     * @return a collection of films
     */
    Collection<Film> getPopularFilmsByYear(int year);

    /**
     * Retrieves the most popular films belonging to a specific genre.
     *
     * @param genreId the ID of the genre to filter by
     * @return a collection of films
     */
    Collection<Film> getPopularFilmsByGenre(int genreId);

    /**
     * Retrieves the most popular films filtered by both year and genre.
     *
     * @param year    the release year
     * @param genreId the genre ID
     * @return a collection of films
     */
    Collection<Film> getPopularFilmsByYearAndGenre(int year, int genreId);

    /**
     * Generates film recommendations for a user.
     * <p>
     * The algorithm typically finds users with similar likes and suggests films
     * that those similar users liked but the target user hasn't seen yet.
     * </p>
     *
     * @param userId the ID of the user to get recommendations for
     * @return a collection of recommended films
     */
    Collection<Film> getFilmRecommendations(long userId);
}