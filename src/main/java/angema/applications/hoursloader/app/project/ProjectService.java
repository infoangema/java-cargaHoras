package angema.applications.hoursloader.app.project;


import angema.applications.hoursloader.app.company.CompanyDto;
import angema.applications.hoursloader.app.user.UserService;
import angema.applications.hoursloader.core.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import angema.applications.hoursloader.app.company.CompanyService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProjectService {
    @Autowired
    private UserService userService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CompanyService companyService;

    public List<ProjectDto> getAllProject() {
        try {
            List<Project> projects = projectRepository.findAllByOrderByIdAsc();
            List<ProjectDto> projectDtos = new ArrayList<>();
            projects.forEach(project -> projectDtos.add(mapProjectToDto(project)));
            return projectDtos;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public ProjectDto getProjectDtoById(final Long id) {

        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return mapProjectToDto(project.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND);
        }
    }

    public ProjectDto saveProject(ProjectDto projectDto) {

        Project project = mapDtoToProject(projectDto);

        try {
            return mapProjectToDto(projectRepository.save(project));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public void deleteProject(ProjectDto projectDto) {
        try {
            projectRepository.delete(mapDtoToProject(projectDto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND, e);
        }
    }

    private Project mapDtoToProject(ProjectDto projectDto) {
        Project project = new Project();

        project.id = projectDto.id;
        project.name = projectDto.name;
        project.description = projectDto.description;
        // todo: corregir dto de company.
        project.company = companyService.mapDtoToCompany(projectDto.company);
        project.status = projectDto.status;
        return project;
    }
    private ProjectDto mapProjectToDto(Project project) {
        ProjectDto projectDto = new ProjectDto();

        projectDto.id = project.id;
        projectDto.name = project.name;
        projectDto.description = project.description;
        projectDto.company = companyService.mapCompanyToDto(project.company);
        projectDto.status = project.status;
        return projectDto;
    }

}

