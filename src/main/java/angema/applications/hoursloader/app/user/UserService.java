package angema.applications.hoursloader.app.user;

import angema.applications.hoursloader.core.Messages;
import angema.applications.hoursloader.core.auth.Auth;
import angema.applications.hoursloader.core.auth.AuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private AuthRepository authRepository;

    public List<UserDto> getAllUser() {
        try {
            List<Auth> users = authRepository.findAll();
            List<UserDto> usersDto = new ArrayList<>();
            users.forEach(user -> usersDto.add(mapUserToDto(user)));
            return usersDto;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public UserDto getUserDtoById(final Long id) {

        Optional<Auth> user = authRepository.findById(id);
        if (user.isPresent()) {
            return mapUserToDto(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND);
        }
    }

    public UserDto saveUser(UserDto userDto) {

        Auth user = mapDtoToUser(userDto);

        try {
            return mapUserToDto(authRepository.save(user));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }



    public void deleteUser(UserDto userDto) {
        try {
            authRepository.delete(mapDtoToUser(userDto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND, e);
        }
    }

    private static Auth mapDtoToUser(UserDto userDto) {
        Auth user = new Auth();

        user.id = userDto.id;
        user.userName = userDto.userName;
        user.name = userDto.name;
        user.lastName = userDto.lastName;
        user.email = userDto.email;
        user.password = userDto.password;
        user.phone = userDto.phone;
        user.active = userDto.active;

        return user;
    }
    private static UserDto mapUserToDto(Auth user) {
        UserDto userDto = new UserDto();

        userDto.id = user.id;
        userDto.userName = user.userName;
        userDto.name = user.name;
        userDto.lastName = user.lastName;
        userDto.email = user.email;
        userDto.password = user.password;
        userDto.phone = user.phone;
        userDto.active = user.active;

        return userDto;
    }

}

