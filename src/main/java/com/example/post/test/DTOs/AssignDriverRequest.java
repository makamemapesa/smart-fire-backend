package com.example.post.test.DTOs;

import lombok.Data;

@Data
public class AssignDriverRequest {
    private Long emergencyId;
    private Long driverId;
    private Long dispatcherId;
}
