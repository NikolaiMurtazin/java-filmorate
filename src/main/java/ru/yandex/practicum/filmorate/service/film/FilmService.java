package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmService implements FilmServiceInterface {
    private final FilmRepository filmRepository;

    private final UserRepository userRepository;

    @Override
    public Collection<Film> getAll() {
        return filmRepository.getAll();
    }

    @Override
    public Film get(long filmId) {
        final Film film = filmRepository.get(filmId);
        if (film == null) {
            throw new NotFoundException("User not found with " + filmId);
        }

        return film;
    }

    @Override
    public Film save(Film film) {
        return filmRepository.create(film);
    }

    @Override
    public Film update(Film film) {
        long filmId = film.getId();
        final Film saved = filmRepository.get(filmId);
        if (saved == null) {
            throw new NotFoundException("Film not found");
        }

        return filmRepository.update(film);
    }

    @Override
    public Film likeFilm(long filmId, long userId) {
        final Film film = filmRepository.get(filmId);
        if (film == null) {
            throw new NotFoundException("User not found with " + filmId);
        }
        final User user = userRepository.get(userId);
        if (user == null) {
            throw new NotFoundException("User not found with " + userId);
        }

        return filmRepository.likeFilm(film, user);
    }

    @Override
    public Film unlikeFilm(long filmId, long userId) {
        final Film film = filmRepository.get(filmId);
        if (film == null) {
            throw new NotFoundException("User not found with " + filmId);
        }
        final User user = userRepository.get(userId);
        if (user == null) {
            throw new NotFoundException("User not found with " + userId);
        }

        return filmRepository.unlikeFilm(film, user);
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        return filmRepository.getPopularFilms(count);
    }
}
