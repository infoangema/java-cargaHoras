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

    @Autowired
    private AuthValidator authValidator;

    @Autowired
    private ExceptionService exceptionService;

    @Autowired
    private GlobalResponseService globalResponseService;


    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse create(@Valid @RequestBody UserDto data, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        UserDto userDto = userService.saveUser(data);
        return globalResponseService.responseOK(userDto);
    }

    @GetMapping("/read")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse read() {
        List<UserDto> users = userService.getAllUser();
        return globalResponseService.responseOK(users);
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DEVS')")
    public GlobalResponse getId(@PathVariable("id") Long id) {
        UserDto user = userService.getUserDtoById(id);
        return globalResponseService.responseOK(user);
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse delete(@PathVariable("id") Long id) {
        UserDto user = userService.getUserDtoById(id);
        userService.deleteUser(user);
        return globalResponseService.responseOK("USER DELETE");
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse update(@Valid @RequestBody UserDto data, @PathVariable("id") Long id, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        UserDto userDto = userService.getUserDtoById(id);
        // todo: response error
        data.id = userDto.id;
        data = userService.saveUser(data);
        return globalResponseService.responseOK(data);
    }
}
