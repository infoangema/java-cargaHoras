package com.angema.hours.app.feature.user;

import com.angema.hours.app.core.exceptions.ExceptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private ExceptionService exceptionService;

    @GetMapping()
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAll() {
        List<User> user = userService.getAllUser();
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    //@PreAuthorize("hasRole('ADMIN')")
    public User getId(@PathVariable("id") Long id) {
        return userService.getIdUser(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    // @PreAuthorize("hasRole('ADMIN')")
    public User save(@Valid @RequestBody User data, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        return userService.saveUser(data);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    // @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        User user = userService.getIdUser(id);
        userService.deleteUser(user);
        return "DELETE";
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    // @PreAuthorize("hasRole('ADMIN')")
    public User update(@Valid @RequestBody User data, @PathVariable("id") Long id, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        User user = userService.getIdUser(id);
        user.setMail(data.getMail());
        user.setPassword(data.getPassword());
        user.setName(data.getName());
        user.setSurname(data.getSurname());
        user.setPhone(data.getPhone());
        return userService.saveUser(user);
    }
}
