package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void getUserForSave(User user);

    List<User> setListUsers();

    void getIdForDelete(Long id);

    void getIdForUser(long id, User user);

    User getIdAndUserForEdit(long id);

    User getUsernameForUser(String name);

}
