package com.example.post.test.service.implimantation;


import com.example.post.test.entity.Emergency;
import com.example.post.test.entity.User;
import com.example.post.test.repository.EmergencyRepository;
import com.example.post.test.repository.UserRepository;
import com.example.post.test.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmergencyRepository emergencyRepository;

    public UserServiceImpl(UserRepository userRepository , EmergencyRepository emergencyRepository) {
        this.userRepository = userRepository;
        this.emergencyRepository = emergencyRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean isExist(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public List<Emergency> getEmergenciesByUser(Long userId) {
        return emergencyRepository.findByReporterId(userId);

   }
//    @Override
//    public Optional<User> getUserByEmail(String email){
//        return userRepository.findByEmail(email);
    //
    //
@Override
public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
}




}