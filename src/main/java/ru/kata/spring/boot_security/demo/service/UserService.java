package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void setUserForSave(User user);

    List<User> getListUsers();

    void setIdForDelete(Long id);

    void getIdForUser(long id, User user);

    User setIdAndUserForEdit(long id);

    User getUserByUsername(String name);

}
