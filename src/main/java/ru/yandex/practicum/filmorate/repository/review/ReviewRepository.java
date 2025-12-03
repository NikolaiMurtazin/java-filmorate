package ru.yandex.practicum.filmorate.repository.review;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository interface for managing {@link Review} persistence operations.
 * <p>
 * Defines standard CRUD operations for reviews, as well as methods to manage
 * "usefulness" ratings (likes and dislikes).
 * </p>
 */
public interface ReviewRepository {

    /**
     * Saves a new review to the storage.
     *
     * @param review the review object to be created
     * @return the saved review with its generated ID
     */
    Review create(Review review);

    /**
     * Updates an existing review's content and positivity status.
     *
     * @param review the review object with updated data
     * @return the updated review
     */
    Review update(Review review);

    /**
     * Deletes a review by its ID.
     *
     * @param id the ID of the review to delete
     * @return an {@link Optional} containing the deleted review if found, or empty if not
     */
    Optional<Review> delete(long id);

    /**
     * Finds a review by its unique identifier.
     *
     * @param id the ID of the review to retrieve
     * @return an {@link Optional} containing the review if found, or empty if not
     */
    Optional<Review> getById(long id);

    /**
     * Retrieves reviews for a specific film, sorted by usefulness.
     *
     * @param filmId the ID of the film
     * @param count  the maximum number of reviews to return
     * @return a collection of reviews for the specified film
     */
    Collection<Review> getReviewsByFilmId(Long filmId, int count);

    /**
     * Retrieves reviews for all films, sorted by usefulness.
     *
     * @param count the maximum number of reviews to return
     * @return a collection of reviews
     */
    Collection<Review> getAllReviews(int count);

    /**
     * Adds a like (positive rating) to a review from a user.
     * <p>
     * This increases the review's "usefulness" score.
     * </p>
     *
     * @param id     the ID of the review
     * @param userId the ID of the user adding the like
     */
    void addLikeToReview(long id, long userId);

    /**
     * Adds a dislike (negative rating) to a review from a user.
     * <p>
     * This decreases the review's "usefulness" score.
     * </p>
     *
     * @param id     the ID of the review
     * @param userId the ID of the user adding the dislike
     */
    void addDislikeToReview(long id, long userId);

    /**
     * Removes a like from a review.
     *
     * @param id     the ID of the review
     * @param userId the ID of the user removing the like
     */
    void deleteLikeFromReview(long id, long userId);

    /**
     * Removes a dislike from a review.
     *
     * @param id     the ID of the review
     * @param userId the ID of the user removing the dislike
     */
    void deleteDislikeFromReview(long id, long userId);
}
