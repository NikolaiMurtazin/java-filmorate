package ru.yandex.practicum.filmorate.repository.review;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.SaveDataException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.repository.BaseJdbcRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * JDBC implementation of the {@link ReviewRepository} interface.
 * <p>
 * This class manages the persistence of film reviews and their ratings (likes/dislikes).
 * It handles the calculation of the "useful" score for reviews by aggregating ratings.
 * </p>
 */
@Repository
@Primary
public class JdbcReviewRepository extends BaseJdbcRepository<Review> implements ReviewRepository {

    public JdbcReviewRepository(NamedParameterJdbcOperations jdbc, RowMapper<Review> mapper) {
        super(jdbc, mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Review create(Review review) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = """
                INSERT INTO REVIEWS (FILM_ID, USER_ID, CONTENT, IS_POSITIVE)
                VALUES (:filmId, :userId, :content, :isPositive);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("filmId", review.getFilmId())
                .addValue("userId", review.getUserId())
                .addValue("content", review.getContent())
                .addValue("isPositive", review.getIsPositive());

        jdbc.update(sql, params, keyHolder);

        Long id = keyHolder.getKeyAs(Long.class);

        if (id != null) {
            review.setReviewId(id);
        } else {
            throw new SaveDataException("Failed to save review data: " + review);
        }

        return review;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Review update(Review review) {
        final String sql = """
                UPDATE REVIEWS
                SET CONTENT = :content,
                    IS_POSITIVE = :isPositive
                WHERE REVIEW_ID = :reviewId;
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("content", review.getContent())
                .addValue("isPositive", review.getIsPositive())
                .addValue("reviewId", review.getReviewId());

        int rowsUpdated = jdbc.update(sql, params);

        if (rowsUpdated == 0) {
            throw new NotFoundException("Review with ID " + review.getReviewId() + " not found");
        }

        return getById(review.getReviewId())
                .orElseThrow(() -> new NotFoundException("Review not found after update"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Review> delete(long id) {
        Optional<Review> optionalReview = getById(id);

        if (optionalReview.isPresent()) {
            final String sql = "DELETE FROM REVIEWS WHERE REVIEW_ID = :reviewId;";
            jdbc.update(sql, new MapSqlParameterSource("reviewId", id));
        }

        return optionalReview;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Review> getById(long id) {
        try {
            final String sql = "SELECT * FROM REVIEWS WHERE REVIEW_ID = :reviewId;";

            return Optional.ofNullable(jdbc.queryForObject(sql,
                    new MapSqlParameterSource("reviewId", id), mapper));
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Review> getReviewsByFilmId(Long filmId, int count) {
        final String sql = """
                SELECT *
                FROM REVIEWS
                WHERE FILM_ID = :filmId
                ORDER BY USEFUL DESC
                LIMIT :count;
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("filmId", filmId)
                .addValue("count", count);
        return jdbc.query(sql, params, mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Review> getAllReviews(int count) {
        final String sql = """
                SELECT *
                FROM REVIEWS
                ORDER BY USEFUL DESC
                LIMIT :count;
                """;

        return jdbc.query(sql, new MapSqlParameterSource("count", count), mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLikeToReview(long id, long userId) {
        addRatingToReview(id, userId, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDislikeToReview(long id, long userId) {
        addRatingToReview(id, userId, -1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteLikeFromReview(long id, long userId) {
        deleteRatingFromReview(id, userId, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDislikeFromReview(long id, long userId) {
        deleteRatingFromReview(id, userId, -1);
    }

    /**
     * Adds a rating record (like or dislike) and recalculates utility.
     */
    private void addRatingToReview(long reviewId, long userId, int rating) {
        final String sql = """
                MERGE INTO REVIEW_RATINGS (REVIEW_ID, USER_ID, RATING)
                KEY (REVIEW_ID, USER_ID)
                VALUES (:reviewId, :userId, :rating);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("reviewId", reviewId)
                .addValue("userId", userId)
                .addValue("rating", rating);

        jdbc.update(sql, params);
        updateReviewUseful(reviewId);
    }

    /**
     * Removes a rating record and recalculates utility.
     */
    private void deleteRatingFromReview(long reviewId, long userId, int rating) {
        final String sql = """
                DELETE FROM REVIEW_RATINGS
                WHERE REVIEW_ID = :reviewId AND USER_ID = :userId AND RATING = :rating;
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("reviewId", reviewId)
                .addValue("userId", userId)
                .addValue("rating", rating);

        jdbc.update(sql, params);
        updateReviewUseful(reviewId);
    }

    /**
     * Recalculates and updates the 'useful' score for a review.
     * <p>
     * This denormalization step ensures that sorting by usefulness is performant.
     * </p>
     */
    private void updateReviewUseful(Long reviewId) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("reviewId", reviewId);

        final String sqlGetUseful = """
                SELECT COALESCE(SUM(RATING), 0)
                FROM REVIEW_RATINGS
                WHERE REVIEW_ID = :reviewId;
                """;

        Integer useful = jdbc.queryForObject(sqlGetUseful, params, Integer.class);
        if (useful == null) {
            useful = 0;
        }

        final String sqlUpdate = "UPDATE REVIEWS SET USEFUL = :useful WHERE REVIEW_ID = :reviewId";

        jdbc.update(sqlUpdate, Map.of("reviewId", reviewId, "useful", useful));
    }
}