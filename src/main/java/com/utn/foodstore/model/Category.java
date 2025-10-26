package com.utn.foodstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "La descripción es requerida")
    @Column(nullable = false, length = 500)
    private String description;

    @NotBlank(message = "La URL de imagen es requerida")
    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Boolean active = true;

}

