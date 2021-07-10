package com.angema.hours.app.feature.user.services;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.feature.user.models.User;
import com.angema.hours.app.feature.user.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository usuarioRepository;

    public List<User> getAllUser() {
        return usuarioRepository.findAll();
    }

    public User getIdUser(final Long id) {
        Optional<User> user = usuarioRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND);
        }
    }

    public User saveUser(User user) {
        return usuarioRepository.save(user);
    }

    public void deleteUser(User user) {
        try {
            usuarioRepository.delete(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND, e);
        }
    }
}
