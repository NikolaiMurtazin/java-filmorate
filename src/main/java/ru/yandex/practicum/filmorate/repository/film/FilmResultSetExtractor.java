package ru.yandex.practicum.filmorate.repository.film;

import org.springframework.jdbc.core.ResultSetExtractor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class FilmResultSetExtractor implements ResultSetExtractor<List<Film>> {
    @Override
    public List<Film> extractData(ResultSet rs) throws SQLException {
        HashMap<Long, Film> filmMap = new HashMap<>();

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

            int genreId = rs.getInt("GENRE_ID");
            if (genreId > 0) {
                Genre genre = Genre.builder()
                        .id(genreId)
                        .name(rs.getString("GENRES.NAME"))
                        .build();
                film.getGenres().add(genre);
            }
        }

        return new ArrayList<>(filmMap.values());
    }
}
