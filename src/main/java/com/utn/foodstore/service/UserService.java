package com.utn.foodstore.service;

import com.utn.foodstore.model.User;
import com.utn.foodstore.repository.UserRepository;
import com.utn.foodstore.util.Sha256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
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

    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        // Hashear la contraseña antes de guardar
        user.setPassword(Sha256Util.hash(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        user.setId(id);
        // Si se proporciona una nueva contraseña, hashearla
        // Si la contraseña ya está hasheada (64 caracteres hex), no la hasheamos de nuevo
        if (user.getPassword() != null && user.getPassword().length() != 64) {
            user.setPassword(Sha256Util.hash(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

}

