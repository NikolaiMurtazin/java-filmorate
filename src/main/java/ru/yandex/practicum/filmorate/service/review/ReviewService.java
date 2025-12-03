package ru.yandex.practicum.filmorate.service.review;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;

/**
 * Service interface for managing {@link Review} business logic.
 * <p>
 * Handles the lifecycle of reviews, including creation, modification, deletion,
 * and the management of user feedback (likes/dislikes) which affects the review's usefulness score.
 * </p>
 */
public interface ReviewService {

    /**
     * Creates a new review.
     * <p>
     * Validates that the associated user and film exist before saving.
     * </p>
     *
     * @param review the review object to create
     * @return the created review with a generated ID
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the user or film does not exist
     */
    Review create(Review review);

    /**
     * Updates an existing review.
     *
     * @param review the review object with updated content and positivity status
     * @return the updated review
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the review ID does not exist
     */
    Review update(Review review);

    /**
     * Deletes a review by its ID.
     *
     * @param id the ID of the review to delete
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the review does not exist
     */
    void delete(Long id);

    /**
     * Retrieves a specific review by its ID.
     *
     * @param id the ID of the review
     * @return the requested review
     * @throws ru.yandex.practicum.filmorate.exception.NotFoundException if the review is not found
     */
    Review getById(Long id);

    /**
     * Retrieves a list of reviews.
     * <p>
     * If {@code filmId} is provided, returns reviews for that specific film.
     * If {@code filmId} is null, returns reviews for all films.
     * Results are typically sorted by usefulness (likes - dislikes).
     * </p>
     *
     * @param filmId the optional ID of the film to filter by (can be null)
     * @param count  the maximum number of reviews to return
     * @return a collection of reviews
     */
    Collection<Review> getReviews(Long filmId, int count);

    /**
     * Adds a like to a review.
     *
     * @param id     the ID of the review
     * @param userId the ID of the user adding the like
     */
    void addLikeToReview(Long id, Long userId);

    /**
     * Adds a dislike to a review.
     *
     * @param id     the ID of the review
     * @param userId the ID of the user adding the dislike
     */
    void addDislikeToReview(Long id, Long userId);

    /**
     * Removes a like from a review.
     *
     * @param id     the ID of the review
     * @param userId the ID of the user removing the like
     */
    void deleteLikeFromReview(Long id, Long userId);

    /**
     * Removes a dislike from a review.
     *
     * @param id     the ID of the review
     * @param userId the ID of the user removing the dislike
     */
    void deleteDislikeFromReview(Long id, Long userId);
}