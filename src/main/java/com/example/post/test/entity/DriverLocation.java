package com.example.post.test.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class DriverLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double latitude;
    private double longitude;
    private String updatedAt;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
}
