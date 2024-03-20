package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films;
    private int generatedId = 0;

    public InMemoryFilmStorage() {
        films = new HashMap<>();
    }

    @Override
    public List<Film> getFilms() {
        log.info("Пришел GET запрос /films ");
        List<Film> filmsList = new ArrayList<>(films.values());
        log.info("Отправлен ответ GET /films с телом: {}", filmsList);
        return filmsList;
    }

    @Override
    public Film getFilm(int id) {
        log.info("Пришел GET запрос /films/{filmId}");
        if (films.get(id) == null) {
            throw new FilmNotFoundException("Film with id=" + id + " not found");
        }

        log.info("Отправлен ответ GET /films/{filmId} с телом: {}", films.get(id));
        return films.get(id);
    }

    @Override
    public Film addFilm(Film film) {
        log.info("Пришел POST запрос /films с телом: {}", film);
        Film newFilm = checkFilm(film);
        newFilm.setId(++generatedId);
        films.put(generatedId, newFilm);
        log.info("Отправлен ответ POST /films с телом: {}", film);
        return newFilm;
    }

    @Override
    public void deleteFilm(int id) {
        log.info("Пришел DELETE запрос /films/{filmId}");
        Film film = films.get(id);
        if (film == null) {
            throw new FilmNotFoundException("Film with id=" + id + " not found");
        }

        films.remove(id);
        log.info("Отправлен ответ DELETE /films/{filmId}");
    }

    @Override
    public Film updateFilm(Film film) {
        log.info("Пришел PUT запрос /films с телом: {}", film);
        if (films.get(film.getId()) == null) {
            throw new FilmNotFoundException("Film with id=" + film.getId() + " not found");
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