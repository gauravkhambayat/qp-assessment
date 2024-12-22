package com.example.qp.assessment.grocery.service.impl;
import com.example.qp.assessment.grocery.entity.CustomUserDetails;
import com.example.qp.assessment.grocery.entity.GroceryItem;
import com.example.qp.assessment.grocery.entity.Order;
import com.example.qp.assessment.grocery.entity.OrderItem;
import com.example.qp.assessment.grocery.exception.InvalidInventoryException;
import com.example.qp.assessment.grocery.exception.ItemNotFoundException;
import com.example.qp.assessment.grocery.repository.*;
import com.example.qp.assessment.grocery.dto.*;
import com.example.qp.assessment.grocery.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final GroceryItemRepository groceryItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    //private final JwtFilter jwtFilter;

    public OrderServiceImpl(GroceryItemRepository groceryItemRepository,
                            OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository/*, JwtFilter jwtFilter*/) {
        this.groceryItemRepository = groceryItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        //this.jwtFilter = jwtFilter;
    }

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        logger.info("Started creating order for : {}",orderRequest);
        List<String> productNames = new ArrayList<>();
        //UUID userId = jwtFilter.getCurrentUserId();
        // Fetch all grocery items based on the requested item IDs
        List<UUID> itemIds = orderRequest.getItems().stream()
                .map(OrderItemRequest::getItemId)
                .collect(Collectors.toList());

        List<GroceryItem> groceryItems = groceryItemRepository.findAllById(itemIds);

        // Check if all requested items exist in the database
        if (groceryItems.size() != itemIds.size()) {
            throw new ItemNotFoundException("One or more items in the order do not exist.");
        }

        double totalPrice = 0.0;

        // Process each item, validate inventory, and calculate total price
        for (OrderItemRequest orderItemRequest : orderRequest.getItems()) {
            GroceryItem groceryItem = groceryItems.stream()
                    .filter(item -> item.getItemId().equals(orderItemRequest.getItemId()))
                    .findFirst()
                    .orElseThrow(() -> new ItemNotFoundException("Item with ID " + orderItemRequest.getItemId() + " not found."));

            if (groceryItem.getInventory() < orderItemRequest.getQuantity()) {
                throw new InvalidInventoryException("Insufficient inventory for item: " + groceryItem.getName());
            }

            // Deduct inventory
            groceryItem.setInventory(groceryItem.getInventory() - orderItemRequest.getQuantity());
            groceryItemRepository.save(groceryItem);
            productNames.add(groceryItem.getName());
            // Add to total price
            totalPrice += groceryItem.getPrice() * orderItemRequest.getQuantity();
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        UUID userId = customUserDetails.getUserId();
        // Create and save the Order entity
        logger.info("user id retrieved is : {}",userId);
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        order.setCreatedAt(LocalDateTime.now());
        order = orderRepository.save(order);
        logger.info("order has been saved in orders table");

        // Create and save OrderItem entities
        Order finalOrder = order;
        List<OrderItem> orderItems = orderRequest.getItems().stream()
                .map(orderItemRequest -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(finalOrder.getOrderId()); // Use the order ID from the saved Order
                    orderItem.setItemId(orderItemRequest.getItemId());
                    orderItem.setQuantity(orderItemRequest.getQuantity());
                    return orderItem;
                })
                .collect(Collectors.toList());

// Save all OrderItem entities
        orderItemRepository.saveAll(orderItems);
        logger.info("order items have been stored in order_items table");

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setMessage("Order placed successfully!");
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setProductNames(productNames);
        orderResponse.setTotalPrice(totalPrice);
        logger.info("order response : {}",orderResponse);
        return orderResponse;
    }

/*    public UUID getCurrentUserId() {
        return UUID.fromString((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }*/

}
