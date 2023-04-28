package angema.applications.hoursloader.app.user;


import angema.applications.hoursloader.core.auth.*;
import angema.applications.hoursloader.core.exceptions.ExceptionService;
import angema.applications.hoursloader.core.globalResponse.GlobalResponse;
import angema.applications.hoursloader.core.globalResponse.GlobalResponseService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto create(@RequestBody UserDto data, BindingResult bindingResult) {
        UserDto userDto = userService.saveUser(data);
        return userDto;
    }

    @GetMapping("/read")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDto> read() {
        List<UserDto> users = userService.getAllUser();
        return users;
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DEVS')")
    public UserDto getId(@PathVariable("id") Long id) {
        UserDto user = userService.getUserDtoById(id);
        return user;
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        UserDto user = userService.getUserDtoById(id);
        userService.deleteUser(user);
        return "DELETE";
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto update(@RequestBody UserDto data, @PathVariable("id") Long id, BindingResult bindingResult) {
        UserDto userDto = userService.getUserDtoById(id);
        data.id = userDto.id;
        data = userService.saveUser(data);
        return data;
    }
}
