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
    private ResponseEntity<List<User>> getAll() {
        List<User> user = userService.getAllUser();
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}")
    private ResponseEntity<User> getId(@PathVariable("id") Long id) {
        User user = userService.getUserId(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping()
    private ResponseEntity<User> save(@Valid @RequestBody User data, BindingResult errorValidation) {
        if (errorValidation.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        User user = userService.saveUser(data);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<User> delete(@PathVariable("id") Long id) {
        User user = userService.getUserId(id);
        userService.deleteUser(user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    private ResponseEntity<User> update(@Valid @RequestBody User data, @PathVariable("id") Long id, BindingResult errorValidation) {
        if (errorValidation.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        userService.getUserId(id);
        User userUpdated = userService.saveUser(data);
        return ResponseEntity.ok().body(userUpdated);
    }
}
