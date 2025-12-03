package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Operation;
import ru.yandex.practicum.filmorate.repository.director.DirectorRepository;
import ru.yandex.practicum.filmorate.repository.feed.FeedRepository;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.genre.GenreRepository;
import ru.yandex.practicum.filmorate.repository.like.LikeRepository;
import ru.yandex.practicum.filmorate.repository.mpa.MpaRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link FilmService}.
 * <p>
 * This class handles the core business logic for films, including:
 * <ul>
 * <li>CRUD operations with validation (MPA, Genres, Directors)</li>
 * <li>Social interactions (Likes) and Feed generation</li>
 * <li>Complex search and filtering (Popular films, Search by keyword)</li>
 * </ul>
 * </p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final MpaRepository mpaRepository;
    private final GenreRepository genreRepository;
    private final LikeRepository likeRepository;
    private final DirectorRepository directorRepository;
    private final FeedRepository feedRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Film> getAll() {
        return filmRepository.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Film> getByDirector(int directorId, String sortBy) {
        directorRepository.getById(directorId)
                .orElseThrow(() -> {
                    log.warn("GET FILMS BY DIRECTOR: Director with ID {} not found", directorId);
                    return new NotFoundException("Режиссер с id=" + directorId + " не существует");
                });
        return filmRepository.getByDirector(directorId, sortBy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Film getById(long filmId) {
        return filmRepository.getById(filmId)
                .orElseThrow(() -> {
                    log.warn("GET FILM: Film with ID {} not found", filmId);
                    return new NotFoundException("Фильм с id=" + filmId + " не существует");
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Film create(Film film) {
        checkFilmMpa(film);
        checkFilmGenres(film);
        checkFilmDirectors(film);

        return filmRepository.create(film);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Film update(Film film) {
        checkFilmExist(film.getId(), "UPDATE");
        checkFilmMpa(film);
        checkFilmGenres(film);
        checkFilmDirectors(film);

        return filmRepository.update(film);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long filmId) {
        filmRepository.delete(filmId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void like(long filmId, long userId) {
        checkUserExist(userId, "LIKE");
        checkFilmExist(filmId, "LIKE");

        likeRepository.like(filmId, userId);

        feedRepository.saveEvent(userId, Operation.ADD, EventType.LIKE, filmId);
        log.info("User {} liked Film {}", userId, filmId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlike(long filmId, long userId) {
        checkUserExist(userId, "UNLIKE");
        checkFilmExist(filmId, "UNLIKE");

        likeRepository.unlike(filmId, userId);

        feedRepository.saveEvent(userId, Operation.REMOVE, EventType.LIKE, filmId);
        log.info("User {} unliked Film {}", userId, filmId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Film> getMostPopular(Integer count, Integer genreId, Integer year) {
        Collection<Film> films;

        if (genreId == null && year == null) {
            films = filmRepository.getMostPopular(count);
        } else if (genreId == null) {
            films = filmRepository.getPopularFilmsByYear(year);
        } else if (year == null) {
            genreRepository.getById(genreId).orElseThrow(() -> {
                log.warn("GET POPULAR: Genre with ID {} not found", genreId);
                return new NotFoundException("Жанр с id=" + genreId + " не существует");
            });
            films = filmRepository.getPopularFilmsByGenre(genreId);
        } else {
            films = filmRepository.getPopularFilmsByYearAndGenre(year, genreId);
        }

        return films;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Film> getCommonFilms(long userId, long friendId) {
        checkUserExist(userId, "COMMON-FILMS");
        checkUserExist(friendId, "COMMON-FILMS");

        return filmRepository.getCommonFilms(userId, friendId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Film> search(String keyword, String params) {
        Set<String> searchParams = Arrays.stream(params.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());

        return filmRepository.search(keyword.toLowerCase(), searchParams);
    }


    private void checkFilmMpa(Film film) {
        int mpaId = film.getMpa().getId();
        mpaRepository.getById(mpaId)
                .orElseThrow(() -> {
                    log.error("VALIDATION: MPA Rating with ID {} not found for film {}", mpaId, film.getName());
                    return new IllegalArgumentException("Рейтинг с id=" + mpaId + " не существует");
                });
    }

    private void checkFilmGenres(Film film) {
        Set<Genre> genres = film.getGenres();
        if (genres == null || genres.isEmpty()) {
            return;
        }

        List<Integer> genreIds = genres.stream()
                .map(Genre::getId)
                .collect(Collectors.toList());

        int matchingGenresCount = genreRepository.countMatchingGenres(genreIds);

        if (matchingGenresCount != genres.size()) {
            log.error("VALIDATION: Film {} contains invalid genres", film.getName());
            throw new IllegalArgumentException("Фильм содержит несуществующий жанр");
        }
    }

    private void checkFilmDirectors(Film film) {
        Set<Director> directors = film.getDirectors();
        if (directors == null || directors.isEmpty()) {
            return;
        }

        List<Integer> directorIds = directors.stream()
                .map(Director::getId)
                .collect(Collectors.toList());

        int matchingDirectorsCount = directorRepository.countMatchingDirectors(directorIds);
        if (matchingDirectorsCount != directors.size()) {
            log.error("VALIDATION: Film {} contains invalid directors", film.getName());
            throw new IllegalArgumentException("Фильм содержит не существующего режиссера");
        }
    }

    private void checkFilmExist(Long filmId, String operation) {
        filmRepository.getById(filmId).orElseThrow(() -> {
            log.warn("{}: Film with ID {} not found", operation, filmId);
            return new NotFoundException("Фильм с id=" + filmId + " не существует");
        });
    }

    private void checkUserExist(Long userId, String operation) {
        userRepository.getById(userId).orElseThrow(() -> {
            log.warn("{}: User with ID {} not found", operation, userId);
            return new NotFoundException("Пользователь с id=" + userId + " не существует");
        });
    }
}