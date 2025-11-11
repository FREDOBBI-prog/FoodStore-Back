package com.utn.foodstore.config;

import com.utn.foodstore.model.Category;
import com.utn.foodstore.model.Product;
import com.utn.foodstore.model.User;
import com.utn.foodstore.repository.CategoryRepository;
import com.utn.foodstore.repository.ProductRepository;
import com.utn.foodstore.repository.UserRepository;
import com.utn.foodstore.util.Sha256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        
        // creo usuarios de prueba
        User admin = new User();
        admin.setName("Administrador");
        admin.setEmail("admin@food.com");
        admin.setPassword(Sha256Util.hash("admin123"));
        admin.setRole("admin");
        userRepository.save(admin);

        User cliente = new User();
        cliente.setName("Cliente Test");
        cliente.setEmail("cliente@food.com");
        cliente.setPassword(Sha256Util.hash("cliente123"));
        cliente.setRole("cliente");
        userRepository.save(cliente);

        User adrian = new User();
        adrian.setName("Adrian Fredes");
        adrian.setEmail("adrianfredes12@gmail.com");
        adrian.setPassword(Sha256Util.hash("123456"));
        adrian.setRole("admin");
        userRepository.save(adrian);

        // creo categorias de prueba
        Category pizzas = new Category();
        pizzas.setName("Pizzas");
        pizzas.setDescription("Deliciosas pizzas artesanales");
        pizzas.setImageUrl("https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400");
        pizzas.setActive(true);
        categoryRepository.save(pizzas);

        Category hamburguesas = new Category();
        hamburguesas.setName("Hamburguesas");
        hamburguesas.setDescription("Hamburguesas gourmet con ingredientes frescos");
        hamburguesas.setImageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400");
        hamburguesas.setActive(true);
        categoryRepository.save(hamburguesas);

        Category bebidas = new Category();
        bebidas.setName("Bebidas");
        bebidas.setDescription("Bebidas frías y refrescantes");
        bebidas.setImageUrl("https://images.unsplash.com/photo-1437418747212-8d9709afab22?w=400");
        bebidas.setActive(true);
        categoryRepository.save(bebidas);

        Category postres = new Category();
        postres.setName("Postres");
        postres.setDescription("Postres caseros y deliciosos");
        postres.setImageUrl("https://images.unsplash.com/photo-1551024506-0bccd828d307?w=400");
        postres.setActive(true);
        categoryRepository.save(postres);

        // creo productos de prueba
        Product pizza1 = new Product();
        pizza1.setName("Pizza Margarita");
        pizza1.setDescription("Pizza clásica con tomate, mozzarella y albahaca fresca");
        pizza1.setPrice(1200.0);
        pizza1.setStock(15);
        pizza1.setAvailable(true);
        pizza1.setImageUrl("https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=400");
        pizza1.setCategoryId(pizzas.getId());
        productRepository.save(pizza1);

        Product pizza2 = new Product();
        pizza2.setName("Pizza Pepperoni");
        pizza2.setDescription("Pizza con abundante pepperoni y queso mozzarella");
        pizza2.setPrice(1400.0);
        pizza2.setStock(12);
        pizza2.setAvailable(true);
        pizza2.setImageUrl("https://images.unsplash.com/photo-1628840042765-356cda07504e?w=400");
        pizza2.setCategoryId(pizzas.getId());
        productRepository.save(pizza2);

        Product burger1 = new Product();
        burger1.setName("Hamburguesa Clásica");
        burger1.setDescription("Hamburguesa con carne 100% vacuna, lechuga, tomate y cebolla");
        burger1.setPrice(1000.0);
        burger1.setStock(20);
        burger1.setAvailable(true);
        burger1.setImageUrl("https://images.unsplash.com/photo-1550547660-d9450f859349?w=400");
        burger1.setCategoryId(hamburguesas.getId());
        productRepository.save(burger1);

        Product burger2 = new Product();
        burger2.setName("Hamburguesa Doble");
        burger2.setDescription("Doble carne, doble queso, bacon y salsa especial");
        burger2.setPrice(1500.0);
        burger2.setStock(10);
        burger2.setAvailable(true);
        burger2.setImageUrl("https://images.unsplash.com/photo-1553979459-d2229ba7433b?w=400");
        burger2.setCategoryId(hamburguesas.getId());
        productRepository.save(burger2);

        Product bebida1 = new Product();
        bebida1.setName("Coca Cola 500ml");
        bebida1.setDescription("Bebida cola refrescante");
        bebida1.setPrice(300.0);
        bebida1.setStock(50);
        bebida1.setAvailable(true);
        bebida1.setImageUrl("https://images.unsplash.com/photo-1554866585-cd94860890b7?w=400");
        bebida1.setCategoryId(bebidas.getId());
        productRepository.save(bebida1);

        Product bebida2 = new Product();
        bebida2.setName("Agua Mineral 500ml");
        bebida2.setDescription("Agua mineral sin gas");
        bebida2.setPrice(200.0);
        bebida2.setStock(60);
        bebida2.setAvailable(true);
        bebida2.setImageUrl("https://images.unsplash.com/photo-1548839140-29a749e1cf4d?w=400");
        bebida2.setCategoryId(bebidas.getId());
        productRepository.save(bebida2);

        Product postre1 = new Product();
        postre1.setName("Brownie con Helado");
        postre1.setDescription("Brownie de chocolate caliente con helado de vainilla");
        postre1.setPrice(600.0);
        postre1.setStock(8);
        postre1.setAvailable(true);
        postre1.setImageUrl("https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=400");
        postre1.setCategoryId(postres.getId());
        productRepository.save(postre1);

        Product postre2 = new Product();
        postre2.setName("Cheesecake");
        postre2.setDescription("Tarta de queso con frutos rojos");
        postre2.setPrice(700.0);
        postre2.setStock(6);
        postre2.setAvailable(true);
        postre2.setImageUrl("https://images.unsplash.com/photo-1533134242443-d4fd215305ad?w=400");
        postre2.setCategoryId(postres.getId());
        productRepository.save(postre2);

        System.out.println("✅ Datos de prueba cargados exitosamente");
    }

}

