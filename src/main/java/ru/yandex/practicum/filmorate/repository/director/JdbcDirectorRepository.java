package ru.yandex.practicum.filmorate.repository.director;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.SaveDataException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.repository.BaseJdbcRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * JDBC implementation of the {@link DirectorRepository} interface.
 * <p>
 * This class uses {@link NamedParameterJdbcOperations} to interact with the database,
 * providing CRUD operations for {@link Director} entities.
 * </p>
 */
@Repository
public class JdbcDirectorRepository extends BaseJdbcRepository<Director> implements DirectorRepository {

    public JdbcDirectorRepository(NamedParameterJdbcOperations jdbc, RowMapper<Director> mapper) {
        super(jdbc, mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Director> getAll() {
        String getAllQuery = "SELECT * FROM DIRECTORS";
        return jdbc.query(getAllQuery, mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Director> getById(int directorId) {
        try {
            String getByIdQuery = "SELECT * FROM DIRECTORS WHERE DIRECTOR_ID = :directorId";
            return Optional.ofNullable(jdbc.queryForObject(getByIdQuery, Map.of("directorId", directorId), mapper));
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Director create(Director director) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String createQuery = """
                INSERT INTO DIRECTORS (NAME)
                VALUES (:name);
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", director.getName());

        jdbc.update(createQuery, params, keyHolder);

        Integer id = keyHolder.getKeyAs(Integer.class);

        if (id != null) {
            director.setId(id);
        } else {
            throw new SaveDataException("Failed to save data: " + director);
        }

        return director;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Director update(Director director) {
        String updateQuery = """
                UPDATE DIRECTORS
                SET NAME = :name
                WHERE DIRECTOR_ID = :directorId;
                """;

        int rowsUpdated = jdbc.update(updateQuery, Map.of("directorId", director.getId(), "name", director.getName()));

        if (rowsUpdated == 0) {
            throw new NotFoundException("Director with ID " + director.getId() + " not found");
        }

        return director;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeById(int directorId) {
        String deleteQuery = "DELETE FROM DIRECTORS WHERE DIRECTOR_ID = :directorId";
        jdbc.update(deleteQuery, Map.of("directorId", directorId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countMatchingDirectors(List<Integer> directorIds) {
        if (directorIds == null || directorIds.isEmpty()) {
            return 0;
        }

        String sqlQuery = "SELECT COUNT(*) FROM DIRECTORS WHERE DIRECTOR_ID IN (:directorIds)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("directorIds", directorIds);
        Integer count = jdbc.queryForObject(sqlQuery, params, Integer.class);
        return count != null ? count : 0;
    }
}