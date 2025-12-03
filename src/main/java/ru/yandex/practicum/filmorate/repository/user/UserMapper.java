package ru.yandex.practicum.filmorate.repository.user;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for {@link User} objects.
 * <p>
 * This class implements the Spring JDBC {@link RowMapper} interface to convert
 * rows from a database {@link ResultSet} into {@link User} entities.
 * It is marked as a {@link Component} to be automatically detected and instantiated by Spring.
 * </p>
 */
@Component
public class UserMapper implements RowMapper<User> {

    /**
     * Maps a single row of the result set to a {@link User} object.
     *
     * @param rs     the {@link ResultSet} to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the constructed {@link User} object
     * @throws SQLException if an SQLException is encountered while getting column values
     */
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("USER_ID"))
                .email(rs.getString("EMAIL"))
                .login(rs.getString("LOGIN"))
                .name(rs.getString("NAME"))
                .birthday(rs.getDate("BIRTHDAY").toLocalDate())
                .build();
    }
}