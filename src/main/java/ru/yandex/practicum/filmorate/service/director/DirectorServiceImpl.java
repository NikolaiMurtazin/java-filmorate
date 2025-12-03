package ru.yandex.practicum.filmorate.service.director;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.repository.director.DirectorRepository;

import java.util.Collection;

/**
 * Implementation of the {@link DirectorService}.
 * <p>
 * This class contains the business logic for managing directors.
 * It interacts with the {@link DirectorRepository} to perform database operations
 * and ensures that appropriate exceptions (like {@link NotFoundException}) are thrown
 * when resources are not found.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Director> getAll() {
        return directorRepository.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Director getById(int directorId) {
        return directorRepository.getById(directorId)
                .orElseThrow(() -> {
                    log.warn("GET DIRECTOR By ID {}: Director not found", directorId);
                    return new NotFoundException("Режиссер с id=" + directorId + " не существует");
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Director create(Director director) {
        return directorRepository.create(director);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Director update(Director director) {
        directorRepository.getById(director.getId())
                .orElseThrow(() -> {
                    log.warn("UPDATE DIRECTOR {}: Director not found", director.getId());
                    return new NotFoundException("Режиссер с id=" + director.getId() + " не существует");
                });

        return directorRepository.update(director);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeById(int directorId) {
        directorRepository.removeById(directorId);
    }
}