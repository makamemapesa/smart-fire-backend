package com.example.post.test.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmergencyResponseDto {
    private EmergencyDto emergency;
    private DriverDto driver;
    private UserDto reporter;
    private CarDto car;

}

