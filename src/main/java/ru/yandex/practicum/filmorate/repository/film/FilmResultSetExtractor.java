package ru.yandex.practicum.filmorate.repository.film;

import org.springframework.jdbc.core.ResultSetExtractor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class FilmResultSetExtractor implements ResultSetExtractor<List<Film>> {
    @Override
    public LinkedList<Film> extractData(ResultSet rs) throws SQLException {
        LinkedHashMap<Long, Film> filmMap = new LinkedHashMap<>();

        while (rs.next()) {
            long filmId = rs.getLong("FILM_ID");
            Film film = filmMap.get(filmId);
            if (film == null) {
                film = Film.builder()
                        .id(filmId)
                        .name(rs.getString("NAME"))
                        .description(rs.getString("DESCRIPTION"))
                        .releaseDate(rs.getDate("RELEASE_DATE").toLocalDate())
                        .duration(rs.getInt("DURATION"))
                        .mpa(MPA.builder()
                                .id(rs.getInt("MPA_ID"))
                                .name(rs.getString("MPAS.NAME"))
                                .build())
                        .genres(new HashSet<>())
                        .build();

                filmMap.put(filmId, film);
            }
        }

        return new LinkedList<>(filmMap.values());
    }
}
