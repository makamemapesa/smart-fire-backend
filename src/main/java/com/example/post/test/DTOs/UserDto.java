package com.example.post.test.DTOs;


import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String roles;
    private String email;
    private String address;
}

