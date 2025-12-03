package ru.yandex.practicum.filmorate.repository.mpa;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for {@link Mpa} objects.
 * <p>
 * This class implements the Spring JDBC {@link RowMapper} interface to convert
 * rows from a database {@link ResultSet} into {@link Mpa} (Motion Picture Association) rating entities.
 * It is marked as a {@link Component} to be automatically detected and instantiated by Spring.
 * </p>
 */
@Component
public class MpaMapper implements RowMapper<Mpa> {

    /**
     * Maps a single row of the result set to an {@link Mpa} object.
     *
     * @param rs     the {@link ResultSet} to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the constructed {@link Mpa} object
     * @throws SQLException if an SQLException is encountered while getting column values
     */
    @Override
    public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(
                rs.getInt("MPA_ID"),
                rs.getString("NAME")
        );
    }
}