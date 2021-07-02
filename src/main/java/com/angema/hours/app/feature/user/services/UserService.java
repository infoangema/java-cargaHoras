package com.angema.hours.app.feature.user.services;

import com.angema.hours.app.feature.user.models.User;
import com.angema.hours.app.feature.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository usuarioRepository;

    public List<User> getAllUser () {
        return usuarioRepository.findAll();
    }

    public Optional<User> getUserId (final Long id) {
        return usuarioRepository.findById(id);
    }

    public User saveUser (User data) {
        return usuarioRepository.save(data);
    }

    public void deleteUser (User user) {
        usuarioRepository.delete(user);
    }

    public void updateUser (User data, User user) {
        user.setMail(data.getMail());
        user.setPassword(data.getPassword());
        user.setName(data.getName());
        user.setSurname(data.getSurname());
        user.setPhone(data.getPhone());
        usuarioRepository.save(user);
    }
}
