package ru.yandex.practicum.filmorate.service.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.genre.GenreRepository;

import java.util.Collection;

/**
 * Implementation of the {@link GenreService}.
 * <p>
 * Handles business logic for retrieving film genres.
 * This service interacts with the {@link GenreRepository} and ensures correct error handling
 * when a requested genre is not found.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Genre> getAll() {
        return genreRepository.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Genre getById(int genreId) {
        return genreRepository.getById(genreId)
                .orElseThrow(() -> {
                    log.warn("GET GENRE By ID {}: Genre not found", genreId);
                    return new NotFoundException("Жанр с id=" + genreId + " не существует");
                });
    }
}