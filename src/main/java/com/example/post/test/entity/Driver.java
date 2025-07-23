package com.example.post.test.entity;

import com.example.post.test.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Table(name = "drivers")
@Data
public class Driver { @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private String status; // e.g., "FREE", "ON_PROGRESS"

    // GPS coordinates
    private Double latitude;
    private Double longitude;
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;


    // Constructors
    public Driver() {
        if (this.status == null) {
            this.status = Status.ACTIVE.toString();
        }
    }
}



