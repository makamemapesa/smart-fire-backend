package com.example.post.test.repository;
import com.example.post.test.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByPlateNumberContainingIgnoreCase(String plateNumber);
    ;


}

