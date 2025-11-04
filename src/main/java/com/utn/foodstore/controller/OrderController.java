package com.utn.foodstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.foodstore.model.Order;
import com.utn.foodstore.model.OrderItem;
import com.utn.foodstore.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.findAll();
        // parseo los items de json a objetos para el front
        orders.forEach(this::parseOrderItems);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.findById(id)
                .map(order -> {
                    parseOrderItems(order);
                    return ResponseEntity.ok(order);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.findByUserId(userId);
        orders.forEach(this::parseOrderItems);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
        List<Order> orders = orderService.findByStatus(status);
        orders.forEach(this::parseOrderItems);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody Map<String, Object> orderData) {
        try {
            Order order = new Order();
            order.setUserId(Long.valueOf(orderData.get("userId").toString()));
            order.setUserName(orderData.get("userName").toString());
            order.setStatus(orderData.get("status").toString());
            order.setSubtotal(Double.valueOf(orderData.get("subtotal").toString()));
            order.setShipping(Double.valueOf(orderData.get("shipping").toString()));
            order.setTotal(Double.valueOf(orderData.get("total").toString()));
            order.setDeliveryAddress(orderData.get("deliveryAddress").toString());
            order.setPhone(orderData.get("phone").toString());
            order.setPaymentMethod(orderData.get("paymentMethod").toString());
            
            if (orderData.containsKey("notes")) {
                order.setNotes(orderData.get("notes").toString());
            }
            
            // convierto los items a json string para guardar en bd
            String itemsJson = objectMapper.writeValueAsString(orderData.get("items"));
            order.setItems(itemsJson);
            
            Order saved = orderService.save(order);
            parseOrderItems(saved);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @Valid @RequestBody Map<String, Object> orderData) {
        try {
            Order order = orderService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
            
            if (orderData.containsKey("status")) {
                order.setStatus(orderData.get("status").toString());
            }
            if (orderData.containsKey("deliveryAddress")) {
                order.setDeliveryAddress(orderData.get("deliveryAddress").toString());
            }
            if (orderData.containsKey("phone")) {
                order.setPhone(orderData.get("phone").toString());
            }
            if (orderData.containsKey("paymentMethod")) {
                order.setPaymentMethod(orderData.get("paymentMethod").toString());
            }
            if (orderData.containsKey("notes")) {
                order.setNotes(orderData.get("notes").toString());
            }
            
            Order updated = orderService.update(id, order);
            parseOrderItems(updated);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> statusData) {
        try {
            String status = statusData.get("status");
            Order updated = orderService.updateStatus(id, status);
            parseOrderItems(updated);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            orderService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // helper para parsear items de json a lista de objetos
    private void parseOrderItems(Order order) {
        try {
            List<OrderItem> items = objectMapper.readValue(
                order.getItems(), 
                objectMapper.getTypeFactory().constructCollectionType(List.class, OrderItem.class)
            );
            // si queres podes agregar los items a un campo transient
        } catch (JsonProcessingException e) {
            // si falla el parseo, dejo el campo items como json string
        }
    }

}

