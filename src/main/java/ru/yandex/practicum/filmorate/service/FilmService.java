package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }

    public Film setLike(int filmId, int userId) {
        log.info("Пришел PUT запрос /films/{filmId}/like/{userId}");
        final Film film = filmStorage.getFilm(filmId);
        film.setLike(userId);
        log.info("Отправлен ответ PUT /films/{filmId}/like/{userId} с телом: {}", film);
        return film;
    }

    public Film deleteLike(int filmId, int userId) {
        log.info("Пришел DELETE запрос /films/{filmId}/like/{userId}");
        final Film film = filmStorage.getFilm(filmId);
        film.deleteLike(userId);
        log.info("Отправлен ответ DELETE /films/{filmId}/like/{userId} с телом: {}", film);
        return film;
    }

    public List<Film> getPopularFilms(int count) {
        log.info("Пришел GET запрос /films/popular с телом: {}", count);
        List<Film> films = filmStorage.getFilms().stream()
                .sorted(Comparator.comparingInt(film -> ((Film) film).getLikes().size()).reversed())
                .limit(count).collect(Collectors.toList());

        log.info("Отправлен ответ GET /films/popular с телом: {}", films);
        return films;
    }
}