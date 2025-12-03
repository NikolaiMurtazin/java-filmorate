package ru.yandex.practicum.filmorate.repository.genre;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for {@link Genre} objects.
 * <p>
 * This class implements the Spring JDBC {@link RowMapper} interface to convert
 * rows from a database {@link ResultSet} into {@link Genre} entities.
 * It is marked as a {@link Component} to be automatically detected and instantiated by Spring.
 * </p>
 */
@Component
public class GenreMapper implements RowMapper<Genre> {

    /**
     * Maps a single row of the result set to a {@link Genre} object.
     *
     * @param rs     the {@link ResultSet} to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the constructed {@link Genre} object
     * @throws SQLException if an SQLException is encountered while getting column values
     */
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(
                rs.getInt("GENRE_ID"),
                rs.getString("NAME")
        );
    }
}
