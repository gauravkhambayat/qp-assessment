package com.example.qp.assessment.grocery.service;


import com.example.qp.assessment.grocery.dto.OrderRequest;
import com.example.qp.assessment.grocery.dto.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
}
