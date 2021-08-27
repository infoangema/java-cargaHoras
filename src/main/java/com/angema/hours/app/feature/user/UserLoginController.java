package com.angema.hours.app.feature.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/login")
public class UserLoginController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping()
    private ResponseEntity<User> login (@Valid @RequestBody UserLogin userData, BindingResult bindingResult) {
        return userService.login(userData, bindingResult);
    }
}
