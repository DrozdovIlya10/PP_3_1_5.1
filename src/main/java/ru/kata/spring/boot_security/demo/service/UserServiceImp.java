package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.dao.UserDAO;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImp(UserDAO userDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDAO = userDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUserById(long id) {
        User user = null;
        Optional<User> optional = userDAO.findById(id);
        if(optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    @Override
    public void updateUserByIDAndUser(User user, Long id) {
        user.setId(id);
        userDAO.save(user);

    }

    @Override
    public void deleteUserById(long id) {
        userDAO.deleteById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

}
