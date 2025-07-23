package com.example.post.test.DTOs;
import com.example.post.test.entity.Car;
import lombok.Data;

@Data
public class CarDto {
    private Long id;
    private String plateNumber;
    private String model;
    private String color;
}
