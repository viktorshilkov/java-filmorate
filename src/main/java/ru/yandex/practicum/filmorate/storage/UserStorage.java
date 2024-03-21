package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User getUser(int id);

    List<User> getUsers();

    User addUser(User user);

    void deleteUser(int id);

    User updateUser(User user);
}