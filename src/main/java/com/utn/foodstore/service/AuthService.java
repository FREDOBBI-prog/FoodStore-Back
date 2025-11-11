package com.utn.foodstore.service;

import com.utn.foodstore.model.User;
import com.utn.foodstore.repository.UserRepository;
import com.utn.foodstore.util.Sha256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        user.setPassword(Sha256Util.hash(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String hashedPassword = Sha256Util.hash(password);
            if (user.getPassword().equals(hashedPassword)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}

