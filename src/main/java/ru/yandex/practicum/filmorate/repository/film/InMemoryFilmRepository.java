package ru.yandex.practicum.filmorate.repository.film;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class InMemoryFilmRepository implements FilmRepository {
    private long filmId = 0L;

    private final HashMap<Long, Film> filmMap = new HashMap<>();

    private final HashMap<Long, Set<Long>> filmLikeIds = new HashMap<>();

    @Override
    public Collection<Film> getAll() {
        return filmMap.values();
    }

    @Override
    public Film get(long filmId) { //=
        return filmMap.get(filmId);
    }

    @Override
    public Film create(Film film) {
        film.setId(generateId());
        filmMap.put(film.getId(), film);

        return film;
    }

    @Override
    public Film update(Film film) {
        if (!filmMap.containsKey(film.getId())) {
            throw new NotFoundException("ID not found in the List");
        }

        filmMap.put(film.getId(), film);

        return film;
    }

    @Override
    public Film likeFilm(Film film, User user) {
        Set<Long> filmIds = filmLikeIds.computeIfAbsent(film.getId(), id -> new HashSet<>());
        filmIds.add(user.getId());

        return film;
    }

    @Override
    public Film unlikeFilm(Film film, User user) {
        Set<Long> filmIds = filmLikeIds.computeIfAbsent(film.getId(), id -> new HashSet<>());
        filmIds.remove(user.getId());

        return film;
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        return filmLikeIds.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue().size(), entry1.getValue().size())) // Сортировка по количеству лайков
                .map(entry -> filmMap.get(entry.getKey()))
                .limit(count)
                .collect(Collectors.toList());
    }

    private Long generateId() {
        return ++filmId;
    }
}
