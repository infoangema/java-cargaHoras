package angema.applications.hoursloader.app.project;

import angema.applications.hoursloader.core.globalResponse.GlobalResponse;
import angema.applications.hoursloader.core.globalResponse.GlobalResponseService;
import angema.applications.hoursloader.core.exceptions.ExceptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProjectDto create(@RequestBody ProjectDto data, BindingResult bindingResult) {
        ProjectDto projectDto = projectService.saveProject(data);
        return projectDto;
    }

    @GetMapping("/read")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ProjectDto> read() {
        List<ProjectDto> projectDto = projectService.getAllProject();
        return projectDto;
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProjectDto readById(@PathVariable("id") Long id) {
        ProjectDto projectDto = projectService.getProjectDtoById(id);
        return projectDto;
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        ProjectDto projectDto = projectService.getProjectDtoById(id);
        projectService.deleteProject(projectDto);
        return "DELETE";
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProjectDto update(@RequestBody ProjectDto data, @PathVariable("id") Long id, BindingResult bindingResult) {
        ProjectDto projectDto = projectService.getProjectDtoById(id);
        data.id = projectDto.id;
        data = projectService.saveProject(data);
        return data;
    }
}
