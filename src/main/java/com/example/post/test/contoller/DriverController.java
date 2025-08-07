package com.example.post.test.contoller;

import com.example.post.test.DTOs.DriverDto;
import com.example.post.test.DTOs.DriverLoginDto;
import com.example.post.test.entity.Driver;
import com.example.post.test.entity.DriverLocation;
import com.example.post.test.repository.DriverLocationRepository;
import com.example.post.test.service.DriverService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverLocationRepository locationRepo;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<DriverDto> createDriver(@RequestBody DriverDto dto) {
        Driver driver = modelMapper.map(dto, Driver.class);
        Driver saved = driverService.saveDriver(driver);
        return new ResponseEntity<>(modelMapper.map(saved, DriverDto.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DriverDto>> getAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        List<DriverDto> result = drivers.stream()
                .map(d -> modelMapper.map(d, DriverDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriverById(@PathVariable Long id) {
        Optional<Driver> driver = driverService.getDriverById(id);
        return driver.map(d -> new ResponseEntity<>(modelMapper.map(d, DriverDto.class), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDto> updateDriver(@PathVariable Long id, @RequestBody DriverDto dto) {
        if (!driverService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dto.setId(id);
        Driver updated = modelMapper.map(dto, Driver.class);
        Driver saved = driverService.saveDriver(updated);
        return new ResponseEntity<>(modelMapper.map(saved, DriverDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/delete")
    public ResponseEntity<DriverDto> deactivateDriver(@RequestBody DriverDto dto) {
        Driver driver = modelMapper.map(dto, Driver.class);
        Driver saved = driverService.saveDriver(driver);
        return new ResponseEntity<>(modelMapper.map(saved, DriverDto.class), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginDriver(@RequestBody DriverLoginDto loginDto) {
        Optional<Driver> driverOpt = driverService.getDriverByEmail(loginDto.getEmail());

        if (driverOpt.isPresent()) {
            Driver driver = driverOpt.get();
            if (driver.getPassword().equals(loginDto.getPassword())) {
                DriverDto responseDto = modelMapper.map(driver, DriverDto.class);
                return new ResponseEntity<>(responseDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Driver not found", HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/{id}/location")
//    public ResponseEntity<?> updateLocation(@PathVariable Long id, @RequestBody DriverLocation loc) {
//        loc.setDriverId(id);
//        loc.setUpdatedAt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));
//        locationRepo.save(loc);
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping("/location")
//    public ResponseEntity<?> getLocation(@RequestParam Long driverId) {
//        return locationRepo.findByDriverId(driverId)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("No location"));
//    }
}
