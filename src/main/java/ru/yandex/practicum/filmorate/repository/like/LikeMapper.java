package ru.yandex.practicum.filmorate.repository.like;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Like;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for {@link Like} objects.
 * <p>
 * This class implements the Spring JDBC {@link RowMapper} interface to convert
 * rows from the database (typically from a link table like FILMS_LIKES)
 * into {@link Like} data objects.
 * </p>
 */
@Component
public class LikeMapper implements RowMapper<Like> {

    /**
     * Maps a single row of the result set to a {@link Like} object.
     *
     * @param rs     the {@link ResultSet} to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the constructed {@link Like} object containing the film ID and user ID
     * @throws SQLException if an SQLException is encountered while getting column values
     */
    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Like(
                rs.getLong("FILM_ID"),
                rs.getLong("USER_ID")
        );
    }
}