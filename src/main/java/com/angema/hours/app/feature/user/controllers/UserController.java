package com.angema.hours.app.feature.user.controllers;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.feature.user.services.UserService;
import com.angema.hours.app.feature.user.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    private ResponseEntity<List<User>> getAll () {
        List<User> user = userService.getAllUser();
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}")
    private ResponseEntity<User> getId (@PathVariable("id") final String id) {
        try {
            Optional<User> user = userService.getUserId(Long.parseLong(id));
            if (user.isPresent()) {
                return ResponseEntity.ok().body(user.get());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (NumberFormatException e) {
            log.info(Messages.ERROR_IDCHARACTER,id);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping()
    private ResponseEntity<User> save (@Valid @RequestBody User data, BindingResult errorValidation) {
        if (errorValidation.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        User user = userService.saveUser(data);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<User> delete (@PathVariable("id") final String id) {
        try {
            Optional<User> user = userService.getUserId(Long.parseLong(id));
            if (user.isPresent()) {
                userService.deleteUser(user.get());
                return ResponseEntity.ok().body(user.get());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (NumberFormatException e) {
            log.info(Messages.ERROR_IDCHARACTER,id);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping()
    private ResponseEntity<User> update (@Valid @RequestBody User data, BindingResult errorValidation) {
        if (errorValidation.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Optional<User> user = userService.getUserId(data.getId());
            if (user.isPresent()) {
                userService.updateUser(data,user.get());
                return ResponseEntity.ok().body(user.get());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
