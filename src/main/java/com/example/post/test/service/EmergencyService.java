package com.example.post.test.service;


import com.example.post.test.DTOs.EmergencyResponseDto;
import com.example.post.test.entity.Emergency;
import java.util.List;
import java.util.Optional;

public interface EmergencyService {
    Emergency saveEmergency(Emergency emergency);
    List<Emergency> getAllEmergencies();

    List<EmergencyResponseDto> getEmergencies();

    Optional<Emergency> getEmergencyById(Long id);
    void deleteEmergency(Long id);
    boolean isExist(Long id);
}