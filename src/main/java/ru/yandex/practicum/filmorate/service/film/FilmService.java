package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmRepository;
import ru.yandex.practicum.filmorate.storage.user.UserRepository;

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
        return filmRepository.get(filmId)
                .orElseThrow(() -> new NotFoundException("User not found with " + filmId));
    }

    @Override
    public Film save(Film film) {
        return filmRepository.create(film);
    }

    @Override
    public Film update(Film film) {
        if (filmRepository.get(film.getId()).isEmpty()) {
            throw new NotFoundException("Film not found");
        }

        return filmRepository.update(film);
    }

    @Override
    public void likeFilm(Long filmId, Long userId) {
        Film film = filmRepository.get(filmId)
                .orElseThrow(() -> new NotFoundException("Film not found with " + filmId));
        User user = userRepository.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with " + userId));

        filmRepository.likeFilm(film, user);
    }

    @Override
    public void unlikeFilm(Long filmId, Long userId) {
        Film film = filmRepository.get(filmId)
                .orElseThrow(() -> new NotFoundException("Film not found with " + filmId));
        User user = userRepository.get(userId)
                .orElseThrow(() -> new NotFoundException("User not found with " + userId));

        filmRepository.unlikeFilm(film, user);
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        return filmRepository.getPopularFilms(count);
    }
}
