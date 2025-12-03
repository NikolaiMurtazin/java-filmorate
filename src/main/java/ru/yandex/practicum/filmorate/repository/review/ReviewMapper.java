package ru.yandex.practicum.filmorate.repository.review;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for {@link Review} objects.
 * <p>
 * This class implements the Spring JDBC {@link RowMapper} interface to convert
 * rows from a database {@link ResultSet} into {@link Review} entities.
 * It is marked as a {@link Component} to be automatically detected by Spring.
 * </p>
 */
@Component
public class ReviewMapper implements RowMapper<Review> {

    /**
     * Maps a single row of the result set to a {@link Review} object.
     *
     * @param rs     the {@link ResultSet} to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the constructed {@link Review} object
     * @throws SQLException if an SQLException is encountered while getting column values
     */
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Review.builder()
                .reviewId(rs.getLong("REVIEW_ID"))
                .filmId(rs.getLong("FILM_ID"))
                .userId(rs.getLong("USER_ID"))
                .content(rs.getString("CONTENT"))
                .useful(rs.getInt("USEFUL"))
                .isPositive(rs.getBoolean("IS_POSITIVE"))
                .build();
    }
}