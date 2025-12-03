package ru.yandex.practicum.filmorate.service.mpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.mpa.MpaRepository;

import java.util.Collection;

/**
 * Implementation of the {@link MpaService}.
 * <p>
 * Handles business logic for retrieving MPA ratings.
 * Acts as a bridge to the {@link MpaRepository} and handles "Not Found" scenarios.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MpaServiceImpl implements MpaService {

    private final MpaRepository mpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Mpa> getAll() {
        return mpaRepository.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mpa getById(int mpaId) {
        return mpaRepository.getById(mpaId)
                .orElseThrow(() -> {
                    log.warn("GET MPA By ID {}: Rating not found", mpaId);
                    return new NotFoundException("Рейтинг с id=" + mpaId + " не существует");
                });
    }
}