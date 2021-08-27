package com.angema.hours.app.feature.user;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.core.Roles;
import com.angema.hours.app.core.errors.ErrorService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ErrorService errorService;

    public List<User> getAllUser() {
        try {
            return userRepository.findAll();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public User getIdUser(final Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND);
            }
            return user.get();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public User saveUser(User user, BindingResult bindingResult) {
        try {
            List<String> errors = errorService.collectErrorsBindings(bindingResult);
            if (!errors.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Arrays.deepToString(errors.toArray()));
            }
            user.setRol(Roles.USER);
            user.setStatus(true);
            return userRepository.save(user);
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public User deleteUser(final Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND);
            }
            userRepository.delete(user.get());
            return user.get();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public User updateUser(User data, final Long id, BindingResult bindingResult) {
        try {
            List<String> errors = errorService.collectErrorsBindings(bindingResult);
            if (!errors.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Arrays.deepToString(errors.toArray()));
            }
            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND);
            }
            user.get().setMail(data.getMail());
            user.get().setPassword(data.getPassword());
            user.get().setName(data.getName());
            user.get().setSurname(data.getSurname());
            user.get().setPhone(data.getPhone());
            return userRepository.save(user.get());
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public ResponseEntity<User> login (UserLogin userData, BindingResult bindingResult) {
        try {
            List<String> errors = errorService.collectErrorsBindings(bindingResult);
            if (!errors.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Arrays.deepToString(errors.toArray()));
            }
            Optional<User> user = userRepository.findByMail(userData.getMail());
            if (!user.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND);
            }
            if (!(user.get().getPassword().equals(userData.getPassword()))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.ERROR_PASSWORD_INCORRECT);
            }
            final String KEY = "120383..";
            long time = System.currentTimeMillis();
            String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, KEY)
                    .setSubject(user.get().getId().toString())
                    .setIssuedAt(new Date(time))
                    .setExpiration(new Date(time + 900000))
                    .claim("mail", user.get().getMail())
                    .claim("name", user.get().getName())
                    .claim("surname", user.get().getSurname())
                    .claim("phone", user.get().getPhone())
                    .claim("rol", user.get().getRol())
                    .claim("status", user.get().isStatus())
                    .compact();
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", jwt);
            return ResponseEntity.ok().headers(responseHeaders).body(user.get());
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }
}
