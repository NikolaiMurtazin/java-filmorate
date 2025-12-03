package ru.yandex.practicum.filmorate.service.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.repository.feed.FeedRepository;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.review.ReviewRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Implementation of the {@link ReviewService}.
 * <p>
 * Handles the business logic for film reviews.
 * This includes validation of related entities (User, Film), performing CRUD operations,
 * managing likes/dislikes on reviews, and updating the user's activity feed.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final FeedRepository feedRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Review create(Review review) {
        checkFilmExist(review.getFilmId(), "CREATE");
        checkUserExist(review.getUserId(), "CREATE");

        Review createdReview = reviewRepository.create(review);

        feedRepository.saveEvent(createdReview.getUserId(), Operation.ADD, EventType.REVIEW, createdReview.getReviewId());
        log.info("Review created with ID: {}", createdReview.getReviewId());

        return createdReview;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Review update(Review review) {
        checkReviewExist(review.getReviewId(), "UPDATE");
        checkFilmExist(review.getFilmId(), "UPDATE");
        checkUserExist(review.getUserId(), "UPDATE");

        Review updatedReview = reviewRepository.update(review);

        feedRepository.saveEvent(updatedReview.getUserId(), Operation.UPDATE, EventType.REVIEW, updatedReview.getReviewId());
        log.info("Review updated with ID: {}", updatedReview.getReviewId());

        return updatedReview;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long reviewId) {
        Optional<Review> deleteReviewOpt = reviewRepository.delete(reviewId);

        if (deleteReviewOpt.isPresent()) {
            Review review = deleteReviewOpt.get();
            feedRepository.saveEvent(review.getUserId(), Operation.REMOVE, EventType.REVIEW, reviewId);
            log.info("Review deleted with ID: {}", reviewId);
        } else {
            log.warn("DELETE REVIEW: Review with ID {} not found", reviewId);
            throw new NotFoundException("Отзыв с id=" + reviewId + " не существует");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Review getById(Long reviewId) {
        return reviewRepository.getById(reviewId)
                .orElseThrow(() -> {
                    log.warn("GET REVIEW: Review with ID {} not found", reviewId);
                    return new NotFoundException("Отзыв с id=" + reviewId + " не существует");
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Review> getReviews(Long filmId, int count) {
        if (filmId != null) {
            checkFilmExist(filmId, "GET-REVIEWS");
            return reviewRepository.getReviewsByFilmId(filmId, count);
        } else {
            return reviewRepository.getAllReviews(count);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLikeToReview(Long reviewId, Long userId) {
        checkReviewExist(reviewId, "ADD-LIKE");
        checkUserExist(userId, "ADD-LIKE");

        reviewRepository.addLikeToReview(reviewId, userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDislikeToReview(Long reviewId, Long userId) {
        checkReviewExist(reviewId, "ADD-DISLIKE");
        checkUserExist(userId, "ADD-DISLIKE");

        reviewRepository.addDislikeToReview(reviewId, userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteLikeFromReview(Long reviewId, Long userId) {
        checkReviewExist(reviewId, "REMOVE-LIKE");
        checkUserExist(userId, "REMOVE-LIKE");

        reviewRepository.deleteLikeFromReview(reviewId, userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDislikeFromReview(Long reviewId, Long userId) {
        checkReviewExist(reviewId, "REMOVE-DISLIKE");
        checkUserExist(userId, "REMOVE-DISLIKE");

        reviewRepository.deleteDislikeFromReview(reviewId, userId);
    }

    private void checkReviewExist(Long reviewId, String operation) {
        reviewRepository.getById(reviewId).orElseThrow(() -> {
            log.warn("{}: Review with ID {} not found", operation, reviewId);
            return new NotFoundException("Отзыв с id=" + reviewId + " не существует");
        });
    }

    private void checkFilmExist(Long filmId, String operation) {
        filmRepository.getById(filmId).orElseThrow(() -> {
            log.warn("{}: Film with ID {} not found", operation, filmId);
            return new NotFoundException("Фильм с id=" + filmId + " не существует");
        });
    }

    private void checkUserExist(Long userId, String operation) {
        userRepository.getById(userId).orElseThrow(() -> {
            log.warn("{}: User with ID {} not found", operation, userId);
            return new NotFoundException("Пользователь с id=" + userId + " не существует");
        });
    }
}