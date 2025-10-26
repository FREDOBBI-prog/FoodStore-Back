package com.utn.foodstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de usuario es requerido")
    @Column(nullable = false)
    private Long userId;

    @NotBlank(message = "El nombre de usuario es requerido")
    @Column(nullable = false)
    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @NotBlank(message = "El estado es requerido")
    @Column(nullable = false)
    private String status; // "pending", "processing", "completed", "cancelled"

    @Column(nullable = false, columnDefinition = "TEXT")
    private String items; // JSON string de los items

    @NotNull(message = "El subtotal es requerido")
    @Column(nullable = false)
    private Double subtotal;

    @NotNull(message = "El costo de envío es requerido")
    @Column(nullable = false)
    private Double shipping;

    @NotNull(message = "El total es requerido")
    @Column(nullable = false)
    private Double total;

    @NotBlank(message = "La dirección de entrega es requerida")
    @Column(nullable = false, length = 500)
    private String deliveryAddress;

    @NotBlank(message = "El teléfono es requerido")
    @Column(nullable = false)
    private String phone;

    @NotBlank(message = "El método de pago es requerido")
    @Column(nullable = false)
    private String paymentMethod; // "efectivo", "tarjeta", "transferencia"

    @Column(length = 1000)
    private String notes;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}

