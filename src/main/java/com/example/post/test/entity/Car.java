package com.example.post.test.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cars")
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String plateNumber;

    @Column(nullable = false)
    private String model;

    private String color;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "car")
    private List<Driver> drivers;
}



