package com.example.qp.assessment.grocery.service.impl;


import com.example.qp.assessment.grocery.entity.GroceryItem;
import com.example.qp.assessment.grocery.exception.InvalidInventoryException;
import com.example.qp.assessment.grocery.exception.ItemNotFoundException;
import com.example.qp.assessment.grocery.repository.GroceryItemRepository;
import com.example.qp.assessment.grocery.service.GroceryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroceryServiceImpl implements GroceryService {
    private static final Logger logger = LoggerFactory.getLogger(GroceryServiceImpl.class);

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Override
    public GroceryItem addGroceryItem(GroceryItem item) {
        logger.info("inside service layer");
        logger.info("item to save is: {}",item);
        return groceryItemRepository.save(item);
    }

    @Override
    public GroceryItem updateGroceryItem(UUID id, GroceryItem item) {
        GroceryItem existingGroceryItem = groceryItemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item not found for this id"));
        existingGroceryItem.setName(item.getName());
        existingGroceryItem.setDescription(item.getDescription());
        existingGroceryItem.setPrice(item.getPrice());
        existingGroceryItem.setInventory(item.getInventory());
        return groceryItemRepository.save(existingGroceryItem);
    }

    @Override
    public List<GroceryItem> getAllGroceryItems() {
        List<GroceryItem> groceryItemList = groceryItemRepository.findAll();
        
        return groceryItemList;
    }

    @Override
    public void removeGroceryItem(UUID itemId) {
        GroceryItem existingGroceryItem = groceryItemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("Item not found for this id"));;
        groceryItemRepository.delete(existingGroceryItem);
    }

    @Override
    public void updateInventory(UUID itemId, Integer inventory) {
        GroceryItem item = groceryItemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found for this id"));

        if (inventory < 0) {
            throw new InvalidInventoryException("Inventory cannot be negative");
        }

        item.setInventory(inventory);
        groceryItemRepository.save(item);
    }


}
