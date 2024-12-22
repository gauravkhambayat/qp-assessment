package com.example.qp.assessment.grocery.service;

import com.example.qp.assessment.grocery.entity.GroceryItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface GroceryService {
    GroceryItem addGroceryItem(GroceryItem item);
    GroceryItem updateGroceryItem(UUID id, GroceryItem item);
    //void deleteGroceryItem(UUID id);
    List<GroceryItem> getAllGroceryItems();

    void removeGroceryItem(UUID itemId);


    void updateInventory(UUID itemId, Integer inventory);

}
