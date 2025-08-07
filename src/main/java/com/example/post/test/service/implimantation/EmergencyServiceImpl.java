package com.example.post.test.service.implimantation;

import com.example.post.test.DTOs.*;
import com.example.post.test.entity.Driver;
import com.example.post.test.entity.Emergency;
import com.example.post.test.entity.User;
import com.example.post.test.repository.DriverRepository;
import com.example.post.test.repository.EmergencyRepository;
import com.example.post.test.repository.UserRepository;
import com.example.post.test.service.EmergencyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmergencyServiceImpl implements EmergencyService {
    private final EmergencyRepository emergencyRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmergencyServiceImpl(EmergencyRepository emergencyRepository, DriverRepository driverRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.emergencyRepository = emergencyRepository;
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Emergency saveEmergency(Emergency emergency) {
        return emergencyRepository.save(emergency);
    }

    @Override
    public List<Emergency> getEmergenciesByUser(Long userId) {
        return emergencyRepository.findByReporterId(userId);
    }

    @Override
    public List<Emergency> getAllEmergencies() {
        return emergencyRepository.findAll();
    }

    @Override
    public Optional<Emergency> getEmergencyById(Long id) {
        return emergencyRepository.findById(id);
    }

    @Override
    public void deleteEmergency(Long id) {
        emergencyRepository.deleteById(id);
    }

    @Override
    public boolean isExist(Long id) {
        return emergencyRepository.existsById(id);
    }

    @Override
    public List<EmergencyResponseDto> getEmergency() {
        List<Emergency> emergencies = emergencyRepository.findAll();
        List<EmergencyResponseDto> responseList = new ArrayList<>();
        for (Emergency emergency : emergencies) {
            EmergencyResponseDto dto = new EmergencyResponseDto();
            dto.setEmergency(modelMapper.map(emergency, EmergencyDto.class));
            dto.setReporter(modelMapper.map(emergency.getReporter(), UserDto.class));
            if (emergency.getDriver() != null) {
                dto.setDriver(modelMapper.map(emergency.getDriver(), DriverDto.class));

            }
            responseList.add(dto);
        }
        return responseList;
    }

    @Override
    public List<EmergencyResponseDto> getEmergencies() {
        List<Emergency> emergencies = emergencyRepository.findAll();
        List<EmergencyResponseDto> responseList = new ArrayList<>();
        for (Emergency emergency : emergencies) {
            EmergencyResponseDto dto = new EmergencyResponseDto();
            dto.setEmergency(modelMapper.map(emergency, EmergencyDto.class));
            dto.setReporter(modelMapper.map(emergency.getReporter(), UserDto.class));
            if (emergency.getDriver() != null) {
                dto.setDriver(modelMapper.map(emergency.getDriver(), DriverDto.class));

            }
            responseList.add(dto);
        }
        return responseList;
    }

    @Override
    public void respondToAssignment(Long emergencyId, Long driverId, boolean accepted) {
        Emergency emergency = emergencyRepository.findById(emergencyId)
                .orElseThrow(() -> new RuntimeException("Emergency not found"));

        if (emergency.getDriver() == null || !driverId.equals(emergency.getDriver().getId())) {
            throw new RuntimeException("Driver not assigned to this emergency");
        }

        if (accepted) {
            emergency.setStatus("ON_PROGRESS");
        } else {
            emergency.setDriver(null); // unassign driver
            emergency.setStatus("PENDING");
        }

        emergencyRepository.save(emergency);
    }



    @Override
    public Emergency assignEmergencyToDriver(Long emergencyId, Long driverId) {
        Emergency emergency = emergencyRepository.findById(emergencyId)
                .orElseThrow(() -> new RuntimeException("Emergency not found with ID: " + emergencyId));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found with ID: " + driverId));

        emergency.setDriver(driver);

        emergency.setStatus("ASSIGNED");

        // Optional: mark driver as unavailable (if tracking availability)
        driver.setAvailable(false); // Mark driver unavailable
        driverRepository.save(driver);




        return emergencyRepository.save(emergency);
    }


    @Override
    public Emergency rejectEmergency(Long emergencyId, Long driverId, Long dispatcherId) {
        Optional<Emergency> optionalEmergency = emergencyRepository.findById(emergencyId);
        if (optionalEmergency.isPresent()) {
            Emergency emergency = optionalEmergency.get();
            emergency.setDispatcher(dispatcherId);
            emergency.setStatus("REJECTED");
            return emergencyRepository.save(emergency);
        } else {
            throw new RuntimeException("Emergency not found with ID: " + emergencyId);
        }
    }

    @Override
    public List<Emergency> getEmergenciesByReporter(Long reporterId) {
        return emergencyRepository.findByReporterId(reporterId);
    }

    @Override
    public List<Emergency> getEmergenciesByDriver(Long driverId) {
        return emergencyRepository.findByDriverId(driverId);
    }

@Override
    public User updateDriverLocation(Long driverId, Double latitude, Double longitude) {
        Optional<User> userOpt = userRepository.findById(driverId);
        if (userOpt.isPresent()) {
            User driver = userOpt.get();
            driver.setLatitude(latitude);
            driver.setLongitude(longitude);
            return userRepository.save(driver);
        } else {
            throw new RuntimeException("Driver not found with ID: " + driverId);
        }
    }


    @Override
    public Emergency completeEmergency(Long emergencyId, Long driverId) {
        Optional<Emergency> optionalEmergency = emergencyRepository.findById(emergencyId);
        if (optionalEmergency.isPresent()) {
            Emergency emergency = optionalEmergency.get();
            emergency.setStatus("COMPLETED");
            return emergencyRepository.save(emergency);
        } else {
            throw new RuntimeException("Emergency not found with ID: " + emergencyId);
        }
    }

    @Override
    public Emergency approveEmergency(Long emergencyId, Long dispatcherId) {
        Optional<Emergency> optionalEmergency = emergencyRepository.findById(emergencyId);
        if (optionalEmergency.isPresent()) {
            Emergency emergency = optionalEmergency.get();
            emergency.setDispatcher(dispatcherId);
            emergency.setStatus("APPROVED");
            return emergencyRepository.save(emergency);
        } else {
            throw new RuntimeException("Emergency not found with ID: " + emergencyId);
        }
    }

}
