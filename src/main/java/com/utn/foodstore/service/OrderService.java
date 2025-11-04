package com.utn.foodstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.foodstore.model.Order;
import com.utn.foodstore.model.OrderItem;
import com.utn.foodstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<Order> findAll() {
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Order> findByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order update(Long id, Order order) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado");
        }
        order.setId(id);
        return orderRepository.save(order);
    }

    public Order updateStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado");
        }
        orderRepository.deleteById(id);
    }

    // helper para convertir json string a lista de items
    public List<OrderItem> parseItems(String itemsJson) {
        try {
            return objectMapper.readValue(itemsJson, new TypeReference<List<OrderItem>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al parsear los items del pedido", e);
        }
    }

    // helper para convertir lista de items a json string
    public String stringifyItems(List<OrderItem> items) {
        try {
            return objectMapper.writeValueAsString(items);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir los items a JSON", e);
        }
    }

}

