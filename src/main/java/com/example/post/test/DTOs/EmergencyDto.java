package com.example.post.test.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmergencyDto {
    private Long id;
    private String description;
    private String status;
    private Double latitude;
    private Double longitude;
    private String locationDescription;
    private LocalDateTime reportedAt;
    private LocalDateTime respondedAt;
    private LocalDateTime completedAt;
    private Long reporterId;
    private Long driverId;

}

