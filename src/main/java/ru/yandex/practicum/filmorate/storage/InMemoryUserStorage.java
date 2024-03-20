package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users;
    private int generatedId = 0;

    public InMemoryUserStorage() {
        users = new HashMap<>();
    }

    @Override
    public List<User> getUsers() {
        log.info("Пришел GET запрос /users");
        List<User> usersList = new ArrayList<>(users.values());
        log.info("Отправлен ответ GET /users с телом: {}", usersList);
        return usersList;
    }

    @Override
    public User getUser(int id) {
        log.info("Пришел GET запрос /users/{userId}");
        if (users.get(id) == null) {
            throw new UserNotFoundException("User with id=" + id + " not found");
        }

        log.info("Отправлен ответ GET /users/{userId} с телом: {}", users.get(id));
        return users.get(id);
    }

    @Override
    public User addUser(User user) {
        log.info("Пришел POST запрос /users с телом: {}", user);
        User newUser = checkUser(user);
        newUser.setId(++generatedId);
        users.put(generatedId, newUser);
        log.info("Отправлен ответ POST /users с телом: {}", user);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        log.info("Пришел DELETE запрос /users/{userId}");
        final User user = users.get(id);
        if (user == null) {
            throw new UserNotFoundException("User with id=" + id + " not found");
        }

        for (Integer friendId : user.getFriends()) {
            User friend = users.get(friendId);
            friend.deleteFriend(id);
        }

        users.remove(id);
        log.info("Отправлен ответ DELETE /films/{userId}");
    }

    @Override
    public User updateUser(User user) {
        log.info("Пришел PUT запрос /users с телом: {}", user);
        if (users.get(user.getId()) == null) {
            throw new UserNotFoundException("User with id=" + user.getId() + " not found");
        }

        users.put(user.getId(), user);
        log.info("Отправлен ответ PUT /users с телом: {}", user);
        return user;
    }

    private User checkUser(User user) {
        final LocalDate endTime = LocalDate.now();
        if (user.getBirthday().isAfter(endTime)) {
            throw new ValidationException("Birthday in future");
        } else if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        return user;
    }
}