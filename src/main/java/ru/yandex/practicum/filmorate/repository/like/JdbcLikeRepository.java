package ru.yandex.practicum.filmorate.repository.like;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.repository.BaseJdbcRepository;

import java.util.Map;

/**
 * JDBC implementation of the {@link LikeRepository} interface.
 * <p>
 * This repository handles the storage of film likes using a relational database.
 * It extends {@link BaseJdbcRepository} for consistency, although read operations
 * are not explicitly performed here (likes are usually aggregated in film queries).
 * </p>
 */
@Repository
public class JdbcLikeRepository extends BaseJdbcRepository<Like> implements LikeRepository {

    public JdbcLikeRepository(NamedParameterJdbcOperations jdbc, RowMapper<Like> mapper) {
        super(jdbc, mapper);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Uses the {@code MERGE} statement to ensure idempotency: if the like already exists,
     * it simply updates the existing record (effectively doing nothing) instead of throwing a duplicate key error.
     * </p>
     */
    @Override
    public void like(long filmId, long userId) {
        String query = """
                MERGE INTO LIKES (FILM_ID, USER_ID)
                KEY (FILM_ID, USER_ID)
                VALUES (:filmId, :userId);
                """;
        jdbc.update(query, Map.of("filmId", filmId, "userId", userId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlike(long filmId, long userId) {
        String query = "DELETE FROM LIKES WHERE FILM_ID = :filmId AND USER_ID = :userId;";
        jdbc.update(query, Map.of("filmId", filmId, "userId", userId));
    }
}