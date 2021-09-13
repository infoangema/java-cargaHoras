package com.angema.hours.app.feature.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth/login")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping()
    private ResponseEntity<ResponseLogin> login (@Valid @RequestBody UserLogin userData, BindingResult bindingResult) {
        return userLoginService.login(userData, bindingResult);
    }
}
