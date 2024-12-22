package com.example.qp.assessment.grocery.repository;
import com.example.qp.assessment.grocery.entity.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, UUID> {
}
