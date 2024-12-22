package com.example.qp.assessment.grocery.repository;
import com.example.qp.assessment.grocery.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
