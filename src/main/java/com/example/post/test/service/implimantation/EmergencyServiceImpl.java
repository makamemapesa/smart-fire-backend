package com.example.post.test.service.implimantation;
import com.example.post.test.DTOs.*;
import com.example.post.test.entity.Driver;
import com.example.post.test.entity.Emergency;
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
    private UserRepository userRepository ;
    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;

    public EmergencyServiceImpl(EmergencyRepository emergencyRepository, DriverRepository driverRepository, ModelMapper modelMapper) {
        this.emergencyRepository = emergencyRepository;
        this.driverRepository = driverRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Emergency saveEmergency(Emergency emergency) {
        return emergencyRepository.save(emergency);
    }

    @Override
    public List<Emergency> getAllEmergencies() {
        return emergencyRepository.findAll();
    }

    @Override
    public List<EmergencyResponseDto> getEmergencies() {
        List<Emergency> emergencies = emergencyRepository.findAll();
        List<EmergencyResponseDto> emergencyResponseDtoList = new ArrayList<>();
        for(Emergency emergency: emergencies){
            Driver driver = driverRepository.findById(emergency.getDriverId()).orElse(null);
            EmergencyResponseDto emergencyResponseDto = new EmergencyResponseDto();
            emergencyResponseDto.setEmergency(modelMapper.map(emergency, EmergencyDto.class));
            emergencyResponseDto.setReporter(modelMapper.map(emergency.getReporter(), UserDto.class));
            emergencyResponseDto.setDriver(modelMapper.map(driver, DriverDto.class));
            emergencyResponseDto.setCar(modelMapper.map(driver.getCar(), CarDto.class));
            emergencyResponseDtoList.add(emergencyResponseDto);
        }
        return emergencyResponseDtoList;
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
}
