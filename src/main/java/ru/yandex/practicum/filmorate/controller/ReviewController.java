package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.review.ReviewService;

import java.util.Collection;

/**
 * REST Controller for managing movie reviews.
 * <p>
 * Handles CRUD operations for reviews, as well as adding/removing likes and dislikes
 * to/from reviews.
 * </p>
 */
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Creates a new review.
     *
     * @param review the review object to be created
     * @return the created review with its assigned ID
     */
    @PostMapping
    public Review create(@Validated @RequestBody Review review) {
        log.info("POST /reviews request: {}", review);
        Review createdReview = reviewService.create(review);
        log.info("POST /reviews response: {}", createdReview);
        return createdReview;
    }

    /**
     * Updates an existing review.
     *
     * @param review the review object with updated data
     * @return the updated review
     */
    @PutMapping
    public Review update(@Validated @RequestBody Review review) {
        log.info("PUT /reviews request: {}", review);
        Review updatedReview = reviewService.update(review);
        log.info("PUT /reviews response: {}", updatedReview);
        return updatedReview;
    }

    /**
     * Deletes a review by its ID.
     *
     * @param id the ID of the review to delete
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("DELETE /reviews/{} request", id);
        reviewService.delete(id);
        log.info("DELETE /reviews/{} response: success", id);
    }

    /**
     * Retrieves a specific review by its ID.
     *
     * @param id the ID of the review to retrieve
     * @return the requested review object
     */
    @GetMapping("/{id}")
    public Review getById(@PathVariable Long id) {
        log.info("GET /reviews/{} request", id);
        Review review = reviewService.getById(id);
        log.info("GET /reviews/{} response: {}", id, review);
        return review;
    }

    /**
     * Retrieves a collection of reviews.
     * <p>
     * Results can be filtered by a specific film ID. If no film ID is provided,
     * it returns reviews for all films. The number of results is limited by the count parameter.
     * </p>
     *
     * @param filmId the ID of the film to filter reviews by (optional)
     * @param count  the maximum number of reviews to return (default is 10)
     * @return a collection of reviews
     */
    @GetMapping
    public Collection<Review> getReviews(
            @RequestParam(required = false) Long filmId,
            @RequestParam(defaultValue = "10") int count) {
        log.info("GET /reviews?filmId={}, count={} request", filmId, count);
        Collection<Review> reviews = reviewService.getReviews(filmId, count);
        log.info("GET /reviews?filmId={}, count={} response: {}", filmId, count, reviews.size());
        return reviews;
    }

    /**
     * Adds a like to a review.
     *
     * @param id     the ID of the review
     * @param userId the ID of the user adding the like
     */
    @PutMapping("/{id}/like/{userId}")
    public void addLikeToReview(@PathVariable Long id, @PathVariable Long userId) {
        log.info("PUT /reviews/{}/like/{} request", id, userId);
        reviewService.addLikeToReview(id, userId);
        log.info("PUT /reviews/{}/like/{} response: success", id, userId);
    }

    /**
     * Adds a dislike to a review.
     *
     * @param id     the ID of the review
     * @param userId the ID of the user adding the dislike
     */
    @PutMapping("/{id}/dislike/{userId}")
    public void addDislikeToReview(@PathVariable Long id, @PathVariable Long userId) {
        log.info("PUT /reviews/{}/dislike/{} request", id, userId);
        reviewService.addDislikeToReview(id, userId);
        log.info("PUT /reviews/{}/dislike/{} response: success", id, userId);
    }

    /**
     * Removes a like from a review.
     *
     * @param id     the ID of the review
     * @param userId the ID of the user removing the like
     */
    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikeFromReview(@PathVariable Long id, @PathVariable Long userId) {
        log.info("DELETE /reviews/{}/like/{} request", id, userId);
        reviewService.deleteLikeFromReview(id, userId);
        log.info("DELETE /reviews/{}/like/{} response: success", id, userId);
    }

    /**
     * Removes a dislike from a review.
     *
     * @param id     the ID of the review
     * @param userId the ID of the user removing the dislike
     */
    @DeleteMapping("/{id}/dislike/{userId}")
    public void deleteDislikeFromReview(@PathVariable Long id, @PathVariable Long userId) {
        log.info("DELETE /reviews/{}/dislike/{} request", id, userId);
        reviewService.deleteDislikeFromReview(id, userId);
        log.info("DELETE /reviews/{}/dislike/{} response: success", id, userId);
    }
}