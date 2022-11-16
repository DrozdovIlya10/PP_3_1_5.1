package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserDao userDao;

    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void getIdForDelete(Long id) {
        userDao.getIdForDelete(id);
    }

    @Transactional
    @Override
    public void getUserForSave(User user) {
        userDao.getUserForSave(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> setListUsers() {
        return userDao.setListUsers();
    }

    @Transactional
    @Override
    public User getIdAndUserForEdit(long id) {
        return userDao.getIdForUser(id);
    }

    @Transactional
    @Override
    public void getIdForUser(long id, User user) {
        userDao.getIdAndUserForEdit(id, user);
    }

    @Transactional
    @Override
    public User getUsernameForUser(String username) throws UsernameNotFoundException {
        return userDao.getUsernameForUser(username);
    }
}
