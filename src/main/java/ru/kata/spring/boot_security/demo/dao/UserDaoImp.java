package ru.kata.spring.boot_security.demo.dao;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    final UserRepository userRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDaoImp(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void getUserForSave(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
        entityManager.flush();

    }

    @Override
    public void getIdForDelete(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> setListUsers() {
        List<User> list = entityManager.createQuery("select user from User user ").getResultList();
        return list;
    }

    @Override
    public User getIdForUser(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.detach(user);
        return user;
    }

    @Override
    public void getIdAndUserForEdit(long id, User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUsernameForUser(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
}

