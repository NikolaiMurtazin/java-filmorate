package ru.yandex.practicum.filmorate.repository.director;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Director;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for {@link Director} objects.
 * <p>
 * This class implements the Spring JDBC {@link RowMapper} interface to convert
 * rows from a database {@link ResultSet} into {@link Director} entities.
 * It is marked as a {@link Component} to be automatically detected and instantiated by Spring.
 * </p>
 */
@Component
public class DirectorMapper implements RowMapper<Director> {

    /**
     * Maps a single row of the result set to a {@link Director} object.
     *
     * @param rs     the {@link ResultSet} to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the result {@link Director} object
     * @throws SQLException if an SQLException is encountered while getting column values
     */
    @Override
    public Director mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Director(
                rs.getInt("DIRECTOR_ID"),
                rs.getString("NAME")
        );
    }
}