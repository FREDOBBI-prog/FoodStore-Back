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
        // Convertir el campo items de JSON string a lista de objetos
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
            
            // Convertir los items a JSON string
            String itemsJson = objectMapper.writeValueAsString(orderData.get("items"));
            order.setItems(itemsJson);
            
            Order savedOrder = orderService.save(order);
            parseOrderItems(savedOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
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
            
            Order updatedOrder = orderService.update(id, order);
            parseOrderItems(updatedOrder);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> statusData) {
        try {
            String status = statusData.get("status");
            Order updatedOrder = orderService.updateStatus(id, status);
            parseOrderItems(updatedOrder);
            return ResponseEntity.ok(updatedOrder);
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

    // Método auxiliar para parsear los items de JSON a objetos
    private void parseOrderItems(Order order) {
        try {
            List<OrderItem> items = objectMapper.readValue(
                order.getItems(), 
                objectMapper.getTypeFactory().constructCollectionType(List.class, OrderItem.class)
            );
            // Aquí podrías agregar los items parseados a un campo transient si lo necesitas
        } catch (JsonProcessingException e) {
            // Si falla el parseo, dejamos el campo items como está (JSON string)
        }
    }

}

