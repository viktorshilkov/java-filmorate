package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

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
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(int id) {
        if (users.get(id) == null) {
            throw new UserNotFoundException("User with id=" + id + " not found");
        }
        return users.get(id);
    }

    @Override
    public User addUser(User user) {
        int id = ++generatedId;
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        final User user = users.get(id);
        if (user == null) {
            throw new UserNotFoundException("User with id=" + id + " not found");
        }

        for (Integer friendId : user.getFriends()) {
            User friend = users.get(friendId);
            friend.deleteFriend(id);
        }

        users.remove(id);
        log.info("Пользователь с id=" + id + " удалён");
    }

    @Override
    public User updateUser(User user) {
        if (users.get(user.getId()) == null) {
            throw new UserNotFoundException("User with id=" + user.getId() + " not found");
        }
        users.put(user.getId(), user);
        return user;
    }
}