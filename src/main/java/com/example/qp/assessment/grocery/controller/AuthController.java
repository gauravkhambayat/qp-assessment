package com.example.qp.assessment.grocery.controller;

import com.example.qp.assessment.grocery.dto.AuthRequest;
import com.example.qp.assessment.grocery.dto.AuthResponse;
import com.example.qp.assessment.grocery.entity.*;
import com.example.qp.assessment.grocery.jwt.JwtUtil;
import com.example.qp.assessment.grocery.service.UserDetailsImpl;
import com.example.qp.assessment.grocery.service.impl.UserServiceImpl;
import com.example.qp.assessment.grocery.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userServiceImpl;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserServiceImpl userServiceImpl, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userServiceImpl = userServiceImpl;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<?> signupAdmin(@RequestBody User request) {
        userServiceImpl.registerAdmin(request);
        return ResponseEntity.ok("Admin registered successfully");
    }
    @PostMapping("/signup/user")
    public ResponseEntity<?> signupUser(@RequestBody User request) {
        userServiceImpl.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        logger.info("admin login initiated with request : {}",request);
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        logger.info("user with same username found : {}",user);
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.info("password matched");
            // Create a UserDetailsImpl object to pass to generateToken
            UserDetailsImpl userDetails = new UserDetailsImpl(
                    user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole()
            );
            // Generate the JWT token
            String token = jwtUtil.generateToken(userDetails);
            logger.info("token got : {}",token);
            return ResponseEntity.ok(new AuthResponse(token));
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
