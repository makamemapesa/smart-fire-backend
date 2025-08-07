package com.example.post.test.DTOs;


import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String role;
    private String email;
    private String address;
}

