package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void getUserForSave(User user);

    List<User> setListUsers();

    void getIdForDelete(Long id);

    User getIdForUser(long id);

    void getIdAndUserForEdit(long id, User user);

    User getUsernameForUser(String username);
}
