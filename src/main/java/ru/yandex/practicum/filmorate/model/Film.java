package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Data
public class Film {
    private int id;
    @NotBlank
    private String name;
    @Size(min = 1, max = 200)
    private String description;
    private LocalDate releaseDate;
    @Min(1)
    private int duration;
    private Set<Integer> likes = new HashSet<>();

    public void setLike(int id) {
        likes.add(id);
    }

    public void deleteLike(int id) {
        likes.remove(id);
    }
}