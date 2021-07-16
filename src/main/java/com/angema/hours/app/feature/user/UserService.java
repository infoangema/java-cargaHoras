package com.angema.hours.app.feature.user;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.core.errors.ErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
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
}
