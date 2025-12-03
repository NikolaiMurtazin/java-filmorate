package ru.yandex.practicum.filmorate.repository.feed;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;
import ru.yandex.practicum.filmorate.model.Event;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for {@link Event} objects.
 * <p>
 * This class implements the Spring JDBC {@link RowMapper} interface to convert
 * rows from a database {@link ResultSet} into {@link Event} entities.
 * It handles the mapping of columns to event properties, including the conversion
 * of string values to {@link EventType} and {@link Operation} enums.
 * </p>
 */
@Component
public class EventMapper implements RowMapper<Event> {

    /**
     * Maps a single row of the result set to an {@link Event} object.
     *
     * @param rs     the {@link ResultSet} to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the constructed {@link Event} object
     * @throws SQLException if an SQLException is encountered while getting column values
     */
    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Event.builder()
                .eventId(rs.getLong("EVENT_ID"))
                .timestamp(rs.getLong("TIMESTAMP"))
                .userId(rs.getLong("USER_ID"))
                .eventType(EventType.valueOf(rs.getString("EVENT_TYPE")))
                .operation(Operation.valueOf(rs.getString("OPERATION")))
                .entityId(rs.getLong("ENTITY_ID"))
                .build();
    }
}