package ru.yandex.practicum.filmorate.repository.like;

/**
 * Repository interface for managing film likes.
 * <p>
 * Handles the association between users and films, allowing users to express
 * positive feedback (likes) and revoke it.
 * </p>
 */
public interface LikeRepository {

    /**
     * Adds a like to a film from a specific user.
     * <p>
     * This operation persists the relationship between the user and the film.
     * </p>
     *
     * @param filmId the ID of the film being liked
     * @param userId the ID of the user adding the like
     */
    void like(long filmId, long userId);

    /**
     * Removes a like from a film.
     * <p>
     * Deletes the relationship record between the user and the film.
     * </p>
     *
     * @param filmId the ID of the film
     * @param userId the ID of the user removing the like
     */
    void unlike(long filmId, long userId);
}
