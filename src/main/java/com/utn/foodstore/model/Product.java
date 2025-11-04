package com.utn.foodstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "La descripción es requerida")
    @Column(nullable = false, length = 1000)
    private String description;

    @NotNull(message = "El precio es requerido")
    @Min(value = 0, message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Double price;

    @NotNull(message = "El stock es requerido")
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Boolean available = true;

    @NotBlank(message = "La URL de imagen es requerida")
    @Column(nullable = false)
    private String imageUrl;

    @NotNull(message = "La categoría es requerida")
    @Column(nullable = false)
    private Long categoryId;

    @Transient // no se persiste, solo lo uso para mostrar en el front
    private String categoryName;

}

