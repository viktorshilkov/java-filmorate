package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int generatedId = 0;

    @GetMapping("/films")
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Пришел POST запрос /films с телом: {}", film);
        Film newFilm = checkFilm(film);
        newFilm.setId(++generatedId);
        films.put(generatedId, newFilm);
        log.info("Отправлен ответ POST /films с телом: {}", film);
        return newFilm;
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Пришел PUT запрос /films с телом: {}", film);
        if (films.get(film.getId()) == null) {
            throw new ValidationException("Film with id=" + film.getId() + " not found");
        }

        films.put(film.getId(), film);
        log.info("Отправлен ответ PUT /films с телом: {}", film);
        return film;
    }

    private Film checkFilm(Film film) {
        final LocalDate startDate = LocalDate.of(1895, 12, 27);
        if (film.getReleaseDate().isBefore(startDate)) {
            throw new ValidationException("Date must be after 1895.12.27");
        }
        return film;
    }
}