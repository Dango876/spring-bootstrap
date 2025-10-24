package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    void deleteUserById(Long id);
    void saveUser(User user, List<Long> roleIds);
    void updateUser(Long id, User userDetails, List<Long> roleIds);
}
