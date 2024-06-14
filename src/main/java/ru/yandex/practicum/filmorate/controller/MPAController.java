package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.mpa.MPAService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
@Validated
@Slf4j
@RequiredArgsConstructor
public class MPAController {
    private final MPAService mpaService;

    @GetMapping
    public Collection<MPA> getAll() {
        log.info("==> Запрос на вывод всех возрастных рейтингов");
        return mpaService.getAll();
    }

    @GetMapping("/{id}")
    public MPA getById(@PathVariable("id") Integer mpaId) {
        MPA mpa = mpaService.getById(mpaId);
        log.info("==> Запрос на вывод возрастного рейтинга: {}", mpa);
        return mpaService.getById(mpaId);
    }
}
