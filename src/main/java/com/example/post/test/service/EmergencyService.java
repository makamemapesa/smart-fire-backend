package com.example.post.test.service;


import com.example.post.test.DTOs.EmergencyResponseDto;
import com.example.post.test.entity.Emergency;
import com.example.post.test.entity.User;

import java.util.List;
import java.util.Optional;

public interface EmergencyService {
    Emergency saveEmergency(Emergency emergency);

    List<Emergency> getAllEmergencies();

    List<EmergencyResponseDto> getEmergencies();

    Optional<Emergency> getEmergencyById(Long id);

    void deleteEmergency(Long id);

    boolean isExist(Long id);

    List<EmergencyResponseDto> getEmergency();

    Emergency approveEmergency(Long emergencyId, Long dispatcherId);

    List<Emergency> getEmergenciesByReporter(Long reporterId);

    List<Emergency> getEmergenciesByDriver(Long driverId);

    User updateDriverLocation(Long driverId, Double latitude, Double longitude);

    Emergency completeEmergency(Long emergencyId, Long driverId);

    Emergency assignEmergencyToDriver(Long emergencyId, Long driverId);

    Emergency rejectEmergency(Long emergencyId, Long driverId, Long dispatcherId);

    List<Emergency> getEmergenciesByUser(Long userId);

    void respondToAssignment(Long emergencyId, Long driverId, boolean accepted);
}
//package com.example.post.test.service;
//
//import com.example.post.test.DTOs.EmergencyResponseDto;
//import com.example.post.test.entity.Emergency;
//import com.example.post.test.entity.User;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface EmergencyService {
//    Emergency saveEmergency(Emergency emergency);
//    List<Emergency> getAllEmergencies();
//    List<EmergencyResponseDto> getEmergencies();
//    Optional<Emergency> getEmergencyById(Long id);
//    void deleteEmergency(Long id);
//    boolean isExist(Long id);
//    List<EmergencyResponseDto> getEmergency();
//
//    Emergency approveEmergency(Long emergencyId, Long dispatcherId);
//    List<Emergency> getEmergenciesByReporter(Long reporterId);
//    List<Emergency> getEmergenciesByDriver(Long driverId);
//    List<Emergency> getEmergenciesByUser(Long userId);
//
//    Emergency assignEmergencyToDriver(Long emergencyId, Long driverId, Long dispatcherId);
//    Emergency rejectEmergency(Long emergencyId, Long driverId, Long dispatcherId);
//    Emergency completeEmergency(Long emergencyId, Long driverId);
//
//    User updateDriverLocation(Long driverId, Double latitude, Double longitude);
//
//    void respondToAssignment(Long emergencyId, Long driverId, boolean accepted);
//}
//
