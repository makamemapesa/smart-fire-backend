package com.example.post.test.DTOs;
import lombok.Data;

@Data
public class DriverDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String licenseNumber;
    private String status;
    private Long carId;
}
