package angema.applications.hoursloader.core.auth;

import angema.applications.hoursloader.app.user.dtos.RoleDto;
import angema.applications.hoursloader.core.utils.DateUtil;
import angema.applications.hoursloader.core.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

    // DOC | AUTH | PASO-5:
    // genera token y response

    public AuthResponse login(Auth auth) {
        auth.roles.stream().forEach( role -> {
            role.auths = null;
        });

        List<RoleDto> roleDtos = new ArrayList<>();

        auth.roles.stream().forEach( role -> {
            RoleDto roleDto = new RoleDto(role.id, role.description);
            roleDtos.add(roleDto);
        });

        AuthDto authDto = new AuthDto(auth.id, auth.userName, auth.active, auth.user.id, roleDtos);

        AuthResponse response = AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(authJwt.generateToken(authDto))
                .refreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                .issuedAt(dateUtil.getDateMillis() + "")
                .userId(auth.user.id)
                .expiresIn(EXPIRATION_TIME)
                .authDto(authDto)
                .build();
        return response;
    }

//    public Auth getPayloadObject(String token) {
//        return authJwt.getPayLoadObject(token);
//    }

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

    public Auth getAuthById(final Long id) {
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
