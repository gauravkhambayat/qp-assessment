package com.example.qp.assessment.grocery.service;


import com.example.qp.assessment.grocery.entity.User;

public interface UserService {
    void registerAdmin(User user);
    void registerUser(User user);
}
