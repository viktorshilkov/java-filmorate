package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

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
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilm(int id) {
        if (films.get(id) == null) {
            throw new FilmNotFoundException("Film with id=" + id + " not found");
        }
        return films.get(id);
    }

    @Override
    public Film addFilm(Film film) {
        int id = ++generatedId;
        film.setId(id);
        films.put(id, film);
        return film;
    }

    @Override
    public void deleteFilm(int id) {
        Film film = films.get(id);
        if (film == null) {
            throw new FilmNotFoundException("Film with id=" + id + " not found");
        }
        films.remove(id);
        log.info("Фильм с id=" + id + " удалён");
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.get(film.getId()) == null) {
            throw new FilmNotFoundException("Film with id=" + film.getId() + " not found");
        }
        films.put(film.getId(), film);
        return film;
    }
}