package angema.applications.hoursloader.core.auth;

import angema.applications.hoursloader.core.exceptions.ExceptionService;
import angema.applications.hoursloader.core.globalResponse.GlobalResponse;
import angema.applications.hoursloader.core.globalResponse.GlobalResponseService;
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

    // DOC | AUTH | PASO-3:
    // Recibe requests, valida datos contra la base de datos y return objecto AuthResponse con usuario y token incluido
    @PostMapping(path = "/login")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @ResponseBody
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public GlobalResponse login(@RequestBody AuthRequest authRequest, @RequestParam("grant_type") String grantType) throws AuthException {
        Auth user = authValidator.validate(authRequest, grantType);
        AuthResponse authResponse = authService.login(user);
        return globalResponseService.responseOK(authResponse);
    }

}
