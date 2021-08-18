package pl.wojciechkostecki.devicelocation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wojciechkostecki.devicelocation.mapper.UserMapper;
import pl.wojciechkostecki.devicelocation.model.dto.UserDTO;
import pl.wojciechkostecki.devicelocation.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("There is a user with given login");
        } else {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(userMapper.toEntity(userDTO));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }    
}
