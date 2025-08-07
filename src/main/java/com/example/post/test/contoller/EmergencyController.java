package com.example.post.test.contoller;

import com.example.post.test.DTOs.DriverDto;
import com.example.post.test.DTOs.EmergencyDto;
import com.example.post.test.DTOs.EmergencyResponseDto;
import com.example.post.test.DTOs.UserDto;
import com.example.post.test.entity.Driver;
import com.example.post.test.entity.Emergency;
import com.example.post.test.entity.User;
import com.example.post.test.service.DriverService;
import com.example.post.test.service.EmergencyService;
import com.example.post.test.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/emergencies")
@Data
@Tag(name = "Emergency Management", description = "APIs for managing emergencies")
public class EmergencyController {
    @Autowired
    private EmergencyService emergencyService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public EmergencyController(EmergencyService emergencyService, UserService userService, ModelMapper modelMapper) {
        this.emergencyService = emergencyService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<EmergencyDto> createEmergency(@RequestBody EmergencyDto dto) {
        Emergency emergency = modelMapper.map(dto, Emergency.class);
        Emergency saved = emergencyService.saveEmergency(emergency);
        return new ResponseEntity<>(modelMapper.map(saved, EmergencyDto.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmergencyDto>> getAllEmergencies() {
        List<Emergency> emergencies = emergencyService.getAllEmergencies();
        List<EmergencyDto> result = emergencies.stream().map(e -> modelMapper.map(e, EmergencyDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmergencyResponseDto>> getEmergencies() {
        return new ResponseEntity<>(emergencyService.getEmergencies(), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<EmergencyDto> getEmergencyById(@PathVariable Long id) {
//        Optional<Emergency> emergency = emergencyService.getEmergencyById(id);
//        return emergency.map(e -> new ResponseEntity<>(modelMapper.map(e, EmergencyDto.class), HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
@GetMapping("/{id}")
public ResponseEntity<EmergencyDto> getEmergencyById(@PathVariable Long id) {
    Optional<Emergency> emergencyOpt = emergencyService.getEmergencyById(id);

    if (emergencyOpt.isPresent()) {
        Emergency emergency = emergencyOpt.get();

        // Map emergency to DTO
        EmergencyDto dto = modelMapper.map(emergency, EmergencyDto.class);

        // ✅ Map nested reporter manually (assuming reporter exists)
        if (emergency.getReporter() != null) {
            UserDto reporterDto = modelMapper.map(emergency.getReporter(), UserDto.class);
            dto.setReporter(reporterDto); // <- Your EmergencyDto must have a 'UserDto reporter' field
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


    @PutMapping("/{id}")
    public ResponseEntity<EmergencyDto> updateEmergency(@PathVariable Long id, @RequestBody EmergencyDto dto) {
        if (!emergencyService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dto.setId(id);
        Emergency updated = modelMapper.map(dto, Emergency.class);
        Emergency saved = emergencyService.saveEmergency(updated);
        return new ResponseEntity<>(modelMapper.map(saved, EmergencyDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmergency(@PathVariable Long id) {
        emergencyService.deleteEmergency(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/driver")
    public ResponseEntity<UserDto> getDriverForEmergency(@PathVariable Long id) {
        Optional<Emergency> emergencyOpt = emergencyService.getEmergencyById(id);
        if (emergencyOpt.isPresent() && emergencyOpt.get().getDriver() != null) {
            return new ResponseEntity<>(modelMapper.map(emergencyOpt.get().getDriver(), UserDto.class), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{emergencyId}/approve/{dispatcherId}")
    public ResponseEntity<EmergencyDto> approveEmergency(@PathVariable Long emergencyId, @PathVariable Long dispatcherId) {
        try {
            Emergency approvedEmergency = emergencyService.approveEmergency(emergencyId, dispatcherId);
            return new ResponseEntity<>(modelMapper.map(approvedEmergency, EmergencyDto.class), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/{emergencyId}/reject/{dispatcherId}")
//    public ResponseEntity<EmergencyDto> rejectEmergency(@PathVariable Long emergencyId, @PathVariable Long dispatcherId) {
//        try {
//            Emergency rejectedEmergency = emergencyService.rejectEmergency(emergencyId, dispatcherId);
//            return new ResponseEntity<>(modelMapper.map(rejectedEmergency, EmergencyDto.class), HttpStatus.OK);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    @PostMapping("/{emergencyId}/assign/{driverId}")
//    public ResponseEntity<EmergencyDto> assignEmergencyToDriver(@PathVariable Long emergencyId, @PathVariable Long driverId) {
//        try {
//            Emergency assignedEmergency = emergencyService.assignEmergencyToDriver(emergencyId, driverId);
//            return new ResponseEntity<>(modelMapper.map(assignedEmergency, EmergencyDto.class), HttpStatus.OK);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
@PutMapping("/{emergencyId}/assign/{driverId}")
public ResponseEntity<EmergencyDto> assignEmergencyToDriver(
        @PathVariable Long emergencyId,
        @PathVariable Long driverId) {
    try {
        Emergency assignedEmergency = emergencyService.assignEmergencyToDriver(emergencyId, driverId);
        return new ResponseEntity<>(modelMapper.map(assignedEmergency, EmergencyDto.class), HttpStatus.OK);
    } catch (RuntimeException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

//    @PostMapping("/panic")
//    public ResponseEntity<EmergencyDto> handlePanic(@RequestBody EmergencyDto dto) {
//        User reporter = userService.findByEmail(dto.getReporter().getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Emergency emergency = new Emergency();
//        emergency.setReporter(reporter);
//        emergency.setDescription(dto.getDescription());
//        emergency.setLatitude(dto.getLatitude());
//        emergency.setLongitude(dto.getLongitude());
//        emergency.setLocationDescription(dto.getLocationDescription());
//        emergency.setStatus(dto.getStatus());
//        emergency.setReportedAt(dto.getReportedAt());
//
//        emergencyService.saveEmergency(emergency);
//        return ResponseEntity.ok().body(modelMapper.map(emergency, EmergencyDto.class));
//    }

    @PostMapping("/panic")
    public ResponseEntity<EmergencyDto> handlePanic(@RequestBody EmergencyDto dto) {
        // The logic for this is already in your provided EmergencyController.
        // This is just a placeholder to show where the endpoint should be.
        // You can adapt your existing `/panic` endpoint if needed.
        User reporter = userService.findByEmail(dto.getReporter().getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Emergency emergency = new Emergency();
        emergency.setReporter(reporter);
        emergency.setDescription(dto.getDescription());
        emergency.setLatitude(dto.getLatitude());
        emergency.setLongitude(dto.getLongitude());
        emergency.setLocationDescription(dto.getLocationDescription());
        emergency.setStatus(dto.getStatus());
        emergency.setReportedAt(dto.getReportedAt());

        emergencyService.saveEmergency(emergency);
        return ResponseEntity.ok().body(modelMapper.map(emergency, EmergencyDto.class));
    }



    @PostMapping("/{emergencyId}/complete/{driverId}")
    public ResponseEntity<EmergencyDto> completeEmergency(@PathVariable Long emergencyId, @PathVariable Long driverId) {
        try {
            Emergency completedEmergency = emergencyService.completeEmergency(emergencyId, driverId);
            return new ResponseEntity<>(modelMapper.map(completedEmergency, EmergencyDto.class), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/drivers/{driverId}/location")
    public ResponseEntity<UserDto> updateDriverLocation(@PathVariable Long driverId, @RequestParam Double latitude, @RequestParam Double longitude) {
        try {
            User updatedDriver = emergencyService.updateDriverLocation(driverId, latitude, longitude);
            return new ResponseEntity<>(modelMapper.map(updatedDriver, UserDto.class), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/{emergencyId}/driver-response/{driverId}")
    public ResponseEntity<?> respondToAssignment(
            @PathVariable Long emergencyId,
            @PathVariable Long driverId,
            @RequestParam boolean accepted) {
        try {
            emergencyService.respondToAssignment(emergencyId, driverId, accepted);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
  }


    @GetMapping("/driver/{driverId}/assigned")
    public ResponseEntity<List<EmergencyDto>> getAssignedEmergencies(@PathVariable Long driverId) {
        List<Emergency> emergencies = emergencyService.getEmergenciesByDriver(driverId);
        List<EmergencyDto> result = emergencies.stream().map(e -> modelMapper.map(e, EmergencyDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/reporter/{emergencyId}/driver")
    public ResponseEntity<UserDto> getAssignedDriverForReporter(@PathVariable Long emergencyId) {
        Optional<Emergency> emergencyOpt = emergencyService.getEmergencyById(emergencyId);
        if (emergencyOpt.isPresent() && emergencyOpt.get().getDriver() != null) {
            return new ResponseEntity<>(modelMapper.map(emergencyOpt.get().getDriver(), UserDto.class), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/reporter/{reporterId}")
    public ResponseEntity<List<EmergencyDto>> getEmergenciesByReporter(@PathVariable Long reporterId) {
        List<Emergency> emergencies = emergencyService.getEmergenciesByReporter(reporterId);
        List<EmergencyDto> result = emergencies.stream().map(e -> modelMapper.map(e, EmergencyDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/drivers/available")
    public ResponseEntity<List<DriverDto>> getAvailableDrivers() {
        List<Driver> drivers =driverService.getAvailableDrivers();
        List<DriverDto> result = drivers.stream()
                .map(driver -> modelMapper.map(driver, DriverDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

}