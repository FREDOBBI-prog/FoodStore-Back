package com.utn.foodstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    
    private Long productId;
    private String name;
    private Double price;
    private Integer qty;
    private String imageUrl;
    
}

