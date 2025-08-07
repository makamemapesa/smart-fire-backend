package com.example.post.test.contoller;

import com.example.post.test.DTOs.LoginRequest;
import com.example.post.test.DTOs.UserDto;
import com.example.post.test.entity.User;
import com.example.post.test.enums.Roles;
import com.example.post.test.enums.Roles;
import com.example.post.test.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
//@CrossOrigin("*") // Allows requests from Android frontend
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    // ✅ Create user (used by admin in web, not app registration)
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        User user = modelMapper.map(dto, User.class);
        User saved = userService.saveUser(user);
        return new ResponseEntity<>(modelMapper.map(saved, UserDto.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> result = users.stream()
                .map(u -> modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(u -> new ResponseEntity<>(modelMapper.map(u, UserDto.class), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto dto) {
        Optional<User> existingUserOpt = userService.getUserById(id);
        if (existingUserOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User existingUser = existingUserOpt.get();

        // Update only allowed fields
        existingUser.setFirstName(dto.getFirstName());
        existingUser.setMiddleName(dto.getMiddleName());
        existingUser.setLastName(dto.getLastName());
        existingUser.setEmail(dto.getEmail());
        existingUser.setPhoneNumber(dto.getPhoneNumber());
        existingUser.setPassword(dto.getPassword());
        existingUser.setAddress(dto.getAddress());
        existingUser.setRole(dto.getRole());

        User updated = userService.saveUser(existingUser);
        return new ResponseEntity<>(modelMapper.map(updated, UserDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ✅ Login endpoint used by Android LoginActivity
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> optionalUser = userService.findByEmailAndPassword(
                loginRequest.getEmail(), loginRequest.getPassword());

        if (optionalUser.isPresent()) {
            UserDto userDto = modelMapper.map(optionalUser.get(), UserDto.class);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    // ✅ Android Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<?> registerUserFromApp(@RequestBody UserDto userDto) {
        if (!userDto.getRole().equalsIgnoreCase("REPORTER")) {
            return ResponseEntity.badRequest().body("Only REPORTERs can self-register.");
        }

//        // Prevent duplicate registration
//        if (userService.findByEmail(userDto.getEmail()) != null) {
//            return ResponseEntity.badRequest().body("Email already registered.");
//        }

         //Set role explicitly to REPORTER to prevent abuse
        User user = modelMapper.map(userDto, User.class);
        user.setRole(String.valueOf(Roles.REPORTER));


        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(modelMapper.map(savedUser, UserDto.class), HttpStatus.CREATED);
    }
}
