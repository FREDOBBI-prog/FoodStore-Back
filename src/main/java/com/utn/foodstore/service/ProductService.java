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
        // le agrego el nombre de la categoria a cada producto
        for (Product p : products) {
            setCategoryName(p);
        }
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
        Product saved = productRepository.save(product);
        setCategoryName(saved);
        return saved;
    }

    public Product update(Long id, Product product) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        if (!categoryRepository.existsById(product.getCategoryId())) {
            throw new RuntimeException("La categoría no existe");
        }
        product.setId(id);
        Product updated = productRepository.save(product);
        setCategoryName(updated);
        return updated;
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productRepository.deleteById(id);
    }

    // metodo helper para poner el nombre de la categoria al producto
    private void setCategoryName(Product product) {
        Optional<Category> category = categoryRepository.findById(product.getCategoryId());
        if (category.isPresent()) {
            product.setCategoryName(category.get().getName());
        }
    }

}

