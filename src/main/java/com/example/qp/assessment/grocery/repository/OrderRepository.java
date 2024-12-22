package com.example.qp.assessment.grocery.repository;
import com.example.qp.assessment.grocery.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
