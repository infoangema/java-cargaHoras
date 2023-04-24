package angema.applications.hoursloader.app.user;

import angema.applications.hoursloader.app.company.CompanyService;
import angema.applications.hoursloader.app.project.Project;
import angema.applications.hoursloader.app.project.ProjectDto;
import angema.applications.hoursloader.app.user.dtos.RoleDto;
import angema.applications.hoursloader.core.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> getAllUser() {
        try {
            List<User> users = userRepository.findAll();
            List<UserDto> usersDto = new ArrayList<>();
            users.forEach(user -> usersDto.add(mapUserToDto(user)));
            return usersDto;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public UserDto getUserDtoById(final Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("Usuario no encontrado: " + id));
        return mapUserToDto(user);
    }

    public UserDto saveUser(UserDto userDto) {

        User user = mapDtoToUser(userDto);

        try {
            user = userRepository.save(user);
            return mapUserToDto(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }


    public void deleteUser(UserDto userDto) {
        try {
            userRepository.delete(mapDtoToUser(userDto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND, e);
        }
    }

    public User mapDtoToUser(UserDto userDto) {
        User user = new User();

        user.id = userDto.id;
        user.name = userDto.name;
        user.lastName = userDto.lastName;
        user.email = userDto.email;
        user.phone = userDto.phone;
        userDto.projects.forEach(projectDto -> {
            Project project = new Project();
            project.status = projectDto.status;
            project.name = projectDto.name;
            project.id = projectDto.id;
            project.description = projectDto.description;
            user.projects.add(project);
        });
        return user;
    }

    public UserDto mapUserToDto(User user) {
        UserDto userDto = new UserDto();

        userDto.id = user.id;
        userDto.name = user.name;
        userDto.lastName = user.lastName;
        userDto.email = user.email;
        userDto.phone = user.phone;

        user.projects.forEach(project -> {
            ProjectDto projectDto = new ProjectDto();
            projectDto.status = project.status;
            projectDto.name = project.name;
            projectDto.id = project.id;
            projectDto.description = project.description;
            projectDto.company = companyService.mapCompanyToDto(project.company);
            userDto.projects.add(projectDto);
        });

        return userDto;
    }

}

