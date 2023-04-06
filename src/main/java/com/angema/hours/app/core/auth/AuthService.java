package com.angema.hours.app.core.auth;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthJwt authJwt;

    @Autowired
    private DateUtil dateUtil;

    @Value("${configs.auth.token.expiration:86400}")
    private int EXPIRATION_TIME;

    @Autowired
    private AuthRepository authRepository;

    public AuthResponse login(AuthUserLoggedIn user) {
        user.roles.stream().forEach( role -> {
            role.auths = null;
        });

        AuthResponse response = AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(authJwt.generateToken(user))
                .refreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                .issuedAt(dateUtil.getDateMillis() + "")
                .clientId(user.userName)
                .expiresIn(EXPIRATION_TIME)
                .clientData(user)
                .build();
        return response;
    }

    public AuthUserLoggedIn getPayloadObject(String token) {
        return authJwt.getPayLoadObject(token);
    }

    public List<Auth> getUsers() {
        return authRepository.findAll();
    }

    public List<Auth> getAllUser() {
        try {
            return authRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Auth getIdUser(final Long id) {
        Optional<Auth> user = authRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND);
        }
    }

    public Auth saveUser(Auth user) {
        try {
            return authRepository.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public void deleteUser(Auth user) {
        try {
            authRepository.delete(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND, e);
        }
    }
}
