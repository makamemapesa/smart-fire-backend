package com.example.post.test.service;


import com.example.post.test.entity.Emergency;
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

//    List<User> searchUsers(String email, String phoneNumber);
//    List<User> searchUsers(String email);
    Optional<User> findByEmail(String email);



    List<Emergency> getEmergenciesByUser(Long id);

//    Optional<Object> getUserByEmail(String email);
}
