package com.angema.hours.app.core.auth;

import com.angema.hours.app.core.exceptions.ExceptionService;
import com.angema.hours.app.core.globalResponse.GlobalResponse;
import com.angema.hours.app.core.globalResponse.GlobalResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthValidator authValidator;

    @Autowired
    private GlobalResponseService globalResponseService;

    @Autowired
    private ExceptionService exceptionService;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @ResponseBody
    public GlobalResponse login(@RequestBody MultiValueMap<String, String> formParams, @RequestParam("grant_type") String grantType) throws AuthException {
        AuthUserLoggedIn user = authValidator.validate(formParams, grantType);
        AuthResponse token = authService.login(user);
        return globalResponseService.response(token, "/auth/login");
    }

    @PostMapping(path = "/login")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @ResponseBody
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public GlobalResponse login(@RequestBody AuthRequest authRequest, @RequestParam("grant_type") String grantType) throws AuthException {
        AuthUserLoggedIn user = authValidator.validate(authRequest, grantType);
        AuthResponse authResponse = authService.login(user);
        return globalResponseService.response(authResponse, "/auth/login");
    }

    @GetMapping(path = "/users")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @ResponseBody
    public GlobalResponse getUserRoles() {
        List<Auth> user = authService.getUsers();
        return globalResponseService.response(user, "/auth/user");
    }

    @GetMapping()
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Auth>> getAll() {
        List<Auth> user = authService.getAllUser();
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    //@PreAuthorize("hasRole('ADMIN')")
    public Auth getId(@PathVariable("id") Long id) {
        return authService.getIdUser(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    // @PreAuthorize("hasRole('ADMIN')")
    public Auth save(@Valid @RequestBody Auth data, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        return authService.saveUser(data);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    // @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        Auth user = authService.getIdUser(id);
        authService.deleteUser(user);
        return "DELETE";
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    // @PreAuthorize("hasRole('ADMIN')")
    public Auth update(@Valid @RequestBody Auth data, @PathVariable("id") Long id, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        Auth user = authService.getIdUser(id);
        user.setEmail(data.getEmail());
        user.setPassword(data.getPassword());
        user.setName(data.getName());
        user.setUserName(data.getUserName());
        user.setPhone(data.getPhone());
        return authService.saveUser(user);
    }
}
