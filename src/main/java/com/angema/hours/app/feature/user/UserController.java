package com.angema.hours.app.feature.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    private List<User> getAll() {
        return userService.getAllUser();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    private User getId(@PathVariable("id") Long id) {
        return userService.getIdUser(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    private User save(@Valid @RequestBody User data, BindingResult bindingResult) {
        return userService.saveUser(data, bindingResult);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    private User delete(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    private User update(@PathVariable("id") Long id, @Valid @RequestBody User data, BindingResult bindingResult) {
        return userService.updateUser(data, id, bindingResult);
    }
}
