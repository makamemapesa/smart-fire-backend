package com.example.post.test.service;

import com.example.post.test.entity.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {
    Driver saveDriver(Driver driver);
    List<Driver> getAllDrivers();
    Optional<Driver> getDriverById(Long id);
    void deleteDriver(Long id);
    boolean isExist(Long id);
    Optional<Driver> getDriverByEmail(String email);
}
