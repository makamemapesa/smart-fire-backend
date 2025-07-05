package com.example.post.test.contoller;

import com.example.post.test.DTOs.LoginRequest;
import com.example.post.test.DTOs.UserDto;
import com.example.post.test.entity.User;
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
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

@PostMapping
public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
    User user = modelMapper.map(dto, User.class);
    User saved = userService.saveUser(user);
    return new ResponseEntity<>(modelMapper.map(saved, UserDto.class), HttpStatus.CREATED);
}

@GetMapping
public ResponseEntity<List<UserDto>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    List<UserDto> result = users.stream().map(u -> modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
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

        // Update only the fields you allow
        existingUser.setFirstName(dto.getFirstName());
        existingUser.setMiddleName(dto.getMiddleName());
        existingUser.setLastName(dto.getLastName());
        existingUser.setEmail(dto.getEmail());
        existingUser.setPhoneNumber(dto.getPhoneNumber());
        existingUser.setPassword(dto.getPassword());
        existingUser.setRoles(dto.getRoles());
        existingUser.setAddress(dto.getAddress());


        User updated = userService.saveUser(existingUser);
        return new ResponseEntity<>(modelMapper.map(updated, UserDto.class), HttpStatus.OK);
    }


@DeleteMapping("/{id}")
public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
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


}

