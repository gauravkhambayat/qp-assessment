package com.example.qp.assessment.grocery.service.impl;

import com.example.qp.assessment.grocery.entity.Role;
import com.example.qp.assessment.grocery.entity.User;
import com.example.qp.assessment.grocery.repository.UserRepository;
import com.example.qp.assessment.grocery.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void registerAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setPassword(user.getPassword());
        user.setRole(Role.ROLE_ADMIN);
        userRepository.save(user);
    }

    @Override
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setPassword(user.getPassword());
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
    }
}
