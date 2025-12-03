package ru.yandex.practicum.filmorate.repository.film;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;

/**
 * Mapper for {@link Film} objects.
 * <p>
 * This class implements the Spring JDBC {@link RowMapper} interface to convert
 * rows from a database {@link ResultSet} into {@link Film} entities.
 * </p>
 * <p>
 * <strong>Note:</strong> The {@code genres} and {@code directors} collections are initialized
 * as empty sets. They must be populated separately (e.g., via a separate query or
 * a ResultSetExtractor) because a standard RowMapper cannot handle one-to-many relationships
 * in a single pass without duplicating the parent entity.
 * </p>
 */
@Component
public class FilmMapper implements RowMapper<Film> {

    /**
     * Maps a single row of the result set to a {@link Film} object.
     *
     * @param rs     the {@link ResultSet} to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the constructed {@link Film} object (with empty genre/director sets)
     * @throws SQLException if an SQLException is encountered while getting column values
     */
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Mpa mpa = new Mpa(
                rs.getInt("MPA_ID"),
                rs.getString("MPA_NAME")
        );

        return Film.builder()
                .id(rs.getLong("FILM_ID"))
                .name(rs.getString("NAME"))
                .description(rs.getString("DESCRIPTION"))
                .releaseDate(rs.getDate("RELEASE_DATE").toLocalDate())
                .duration(rs.getInt("DURATION"))
                .mpa(mpa)
                .genres(new LinkedHashSet<>())
                .directors(new LinkedHashSet<>())
                .build();
    }
}