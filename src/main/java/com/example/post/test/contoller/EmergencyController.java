package com.example.post.test.contoller;

import com.example.post.test.DTOs.EmergencyDto;
import com.example.post.test.DTOs.EmergencyResponseDto;
import com.example.post.test.entity.Emergency;
import com.example.post.test.service.EmergencyService;
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
public class EmergencyController {
    @Autowired
    private EmergencyService emergencyService;

    @Autowired
    private ModelMapper modelMapper;

    public EmergencyController(EmergencyService emergencyService, ModelMapper modelMapper) {
        this.emergencyService = emergencyService;
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

    @GetMapping("/{id}")
    public ResponseEntity<EmergencyDto> getEmergencyById(@PathVariable Long id) {
        Optional<Emergency> emergency = emergencyService.getEmergencyById(id);
        return emergency.map(e -> new ResponseEntity<>(modelMapper.map(e, EmergencyDto.class), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
}