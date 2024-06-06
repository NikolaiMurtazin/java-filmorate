package ru.yandex.practicum.filmorate.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.genre.GenreRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public Collection<Genre> getAll() {
        return genreRepository.getAll();
    }

    public Genre getById(long genreId) {
        return genreRepository.getById(genreId)
                .orElseThrow(() -> new NotFoundException("Genre with id " + genreId + " not found"));
    }
}
