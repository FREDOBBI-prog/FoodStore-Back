package com.utn.foodstore.service;

import com.utn.foodstore.model.Category;
import com.utn.foodstore.model.Product;
import com.utn.foodstore.repository.CategoryRepository;
import com.utn.foodstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        // Agregar el nombre de la categoría a cada producto
        products.forEach(this::setCategoryName);
        return products;
    }

    public Optional<Product> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(this::setCategoryName);
        return product;
    }

    public List<Product> findByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        products.forEach(this::setCategoryName);
        return products;
    }

    public List<Product> findByAvailable(Boolean available) {
        List<Product> products = productRepository.findByAvailable(available);
        products.forEach(this::setCategoryName);
        return products;
    }

    public Product save(Product product) {
        if (!categoryRepository.existsById(product.getCategoryId())) {
            throw new RuntimeException("La categoría no existe");
        }
        Product savedProduct = productRepository.save(product);
        setCategoryName(savedProduct);
        return savedProduct;
    }

    public Product update(Long id, Product product) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        if (!categoryRepository.existsById(product.getCategoryId())) {
            throw new RuntimeException("La categoría no existe");
        }
        product.setId(id);
        Product updatedProduct = productRepository.save(product);
        setCategoryName(updatedProduct);
        return updatedProduct;
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productRepository.deleteById(id);
    }

    // Método auxiliar para agregar el nombre de la categoría al producto
    private void setCategoryName(Product product) {
        Optional<Category> category = categoryRepository.findById(product.getCategoryId());
        category.ifPresent(cat -> product.setCategoryName(cat.getName()));
    }

}

