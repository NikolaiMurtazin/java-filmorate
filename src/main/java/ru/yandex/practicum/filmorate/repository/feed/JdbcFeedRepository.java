package ru.yandex.practicum.filmorate.repository.feed;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;
import ru.yandex.practicum.filmorate.repository.BaseJdbcRepository;

import java.time.Instant;

/**
 * JDBC implementation of the {@link FeedRepository} interface.
 * <p>
 * This repository handles the persistence of user activity events into the database.
 * It extends {@link BaseJdbcRepository} to reuse common JDBC infrastructure.
 * </p>
 */
@Repository
public class JdbcFeedRepository extends BaseJdbcRepository<Event> implements FeedRepository {

    public JdbcFeedRepository(NamedParameterJdbcOperations jdbc, RowMapper<Event> mapper) {
        super(jdbc, mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveEvent(long userId, Operation operation, EventType eventType, long entityId) {
        String sqlQuery = """
                INSERT INTO USER_EVENTS (TIMESTAMP, USER_ID, EVENT_TYPE, OPERATION, ENTITY_ID)
                VALUES (:timestamp, :userId, :eventType, :operation, :entityId);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("timestamp", Instant.now().toEpochMilli())
                .addValue("userId", userId)
                .addValue("eventType", eventType.name())
                .addValue("operation", operation.name())
                .addValue("entityId", entityId);

        jdbc.update(sqlQuery, params);
    }
}
