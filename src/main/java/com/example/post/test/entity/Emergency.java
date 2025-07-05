package com.example.post.test.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "emergencies")
@Data
public class Emergency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status;

    // Location coordinates
    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String locationDescription;

    @Column(nullable = false)
    private LocalDateTime reportedAt;

    private LocalDateTime respondedAt;
    private LocalDateTime completedAt;

    private Long driverId;

    private Long dispatcher;


    @ManyToOne(optional = false)
    @JoinColumn(name = "reporter", nullable = false)
    private User reporter;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "dispatcher", nullable = false)
//    private User dispatcher;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "driver_id", nullable = false)
//    private Driver driver;

    @PrePersist
    protected void onCreate() {
        this.reportedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "PENDING";
        }
    }

}





