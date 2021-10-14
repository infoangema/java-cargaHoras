package com.angema.hours.app.feature.login;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.core.errors.ErrorService;
import com.angema.hours.app.feature.message.Message;
import com.angema.hours.app.feature.message.MessageResponse;
import com.angema.hours.app.feature.message.MessageService;
import com.angema.hours.app.feature.user.User;
import com.angema.hours.app.feature.user.UserRepository;
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
public class UserLoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ErrorService errorService;

    @Autowired
    private MessageService messageService;

    public ResponseEntity<ResponseLogin> login (UserLogin userData, BindingResult bindingResult) {
        try {
            List<String> errors = errorService.collectErrorsBindings(bindingResult);
            if (!errors.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Arrays.deepToString(errors.toArray()));
            }
            Optional<User> user = userRepository.findByMail(userData.getMail());
            if (!user.isPresent()) {
                return new ResponseEntity(new ResponseLogin(true, Messages.ERROR_USER_NOT_FOUND, null), HttpStatus.BAD_REQUEST);
            }
            if (!(user.get().getPassword().equals(userData.getPassword()))) {
                return new ResponseEntity(new ResponseLogin(true, Messages.ERROR_PASSWORD_INCORRECT, null), HttpStatus.BAD_REQUEST);
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
                    .compact();
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", "Bearer " + jwt);

            Optional<Message> message = messageService.getIdMessage(user.get().getId());
            MessageResponse messageResponse = new MessageResponse();
            if(message.isPresent()) {
                messageResponse.setUrl(message.get().getUrl());
                messageResponse.setStatus(message.get().isStatus());
                messageResponse.setMessage(message.get().getMessage());
                messageResponse.setDateTo(message.get().getDateTo());
                messageResponse.setDateFrom(message.get().getDateFrom());
            }

            UserDTO userDTO = new UserDTO(user.get().getMail(), user.get().getName(), user.get().getSurname(), user.get().getPhone(), user.get().getRol(), messageResponse);
            UserLoginBody userLoginBody = new UserLoginBody(userDTO, jwt);
            return new ResponseEntity(new ResponseLogin(false, null, userLoginBody), responseHeaders, HttpStatus.OK);
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }
}
