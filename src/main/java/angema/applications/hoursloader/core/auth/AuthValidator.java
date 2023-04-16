package angema.applications.hoursloader.core.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;


import java.util.*;

@Component
public class AuthValidator {

    @Autowired
    private AuthRepository authRepository;

    @Value("${configs.auth.timezone}")
    private String TIMEZONE;

    public static final String CLIENT_CREDENTIALS = "client_credentials";
    private String USERNAME_KEY = "angema_devs";
    private String PASSWORD_KEY = "Secret..";

    public boolean validate(MultiValueMap<String, String> params) {
        return Objects.nonNull(params.getFirst(USERNAME_KEY)) && Objects.nonNull(params.getFirst(PASSWORD_KEY));
    }


    // DOC | AUTH | PASO-4:
    // Valida request y datos del login y returns AuthUserLoggedIn

    public Auth validate(AuthRequest authRequest, String grantType) throws AuthException {

        if (!grantType.equals(CLIENT_CREDENTIALS)) {
            messageException("Invalid grant_type");
        }

        if (isNull(authRequest)) {
            messageException("Invalid user or password");
        }

        Optional<Auth> authOpt = authRepository.findByUserNameAndPasswordAndActiveTrue(authRequest.email, authRequest.password);
        if (!authOpt.isPresent()) {
            messageException("Invalid user or password");
        }

        Auth auth = authOpt.get();//auth

        // get day of Date now
//        Date date = new Date(); // your date
//        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
//        cal.setTime(date);
//        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
//        // get hour of Date now
//        String hour = String.valueOf(new Date().getHours());
//        String userName = authRequest.user;
//        String password = authRequest.password;

//        if (!userName.equals(USERNAME_KEY + day) || !password.equals(PASSWORD_KEY + hour)) {
//            message("Invalid user or password");
//        }
//        List<String> roles = new ArrayList<>();
//        user.roles.forEach(role -> roles.add(role.name));
//        user.roles.forEach( r -> {
//            roles.add(r.name);
//        });
        return auth;
    }

    private boolean isNull(AuthRequest authRequest) {
        return Objects.isNull(authRequest)
                || authRequest.email == null
                || authRequest.email.equals("")
                || authRequest.password == null
                || authRequest.password.equals("");
    }

    public void messageException(String message) throws AuthException {
        throw new AuthException(message);
    }
}
