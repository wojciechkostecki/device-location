package pl.wojciechkostecki.devicelocation.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wojciechkostecki.devicelocation.exception.LoginAlreadyUsedException;
import pl.wojciechkostecki.devicelocation.mapper.UserMapper;
import pl.wojciechkostecki.devicelocation.model.dto.UserDTO;
import pl.wojciechkostecki.devicelocation.repository.UserRepository;
import pl.wojciechkostecki.devicelocation.security.TokenUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenUtil tokenUtil;

    public AuthController(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenUtil = tokenUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new LoginAlreadyUsedException("There is a user with given login");
        } else {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(userMapper.toEntity(userDTO));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO request) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails user = (UserDetails) authenticate.getPrincipal();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, tokenUtil.generateAccessToken(user))
                .body(userMapper.toDto(user));
    }
}
