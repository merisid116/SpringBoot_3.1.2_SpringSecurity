package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;
import java.util.List;

public interface UserService {
     void addUser(User user);
     void deleteUser(Long id);
     void editUser(User user);
     User getUserById(Long id);
     User getUserByEmail(String email);
     List<User> getAllUsers();
}
