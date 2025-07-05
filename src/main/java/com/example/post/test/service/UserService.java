package com.example.post.test.service;


import com.example.post.test.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    void deleteUser(Long id);
    boolean isExist(Long id);
    Optional<User>findByEmailAndPassword(String email, String password);

}
