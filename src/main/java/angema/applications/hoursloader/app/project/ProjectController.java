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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ExceptionService exceptionService;

    @Autowired
    private GlobalResponseService globalResponseService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse create( @RequestBody ProjectDto data, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        ProjectDto projectDto = projectService.saveProject(data);
        return globalResponseService.responseOK(projectDto);
    }

    @GetMapping("/read")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse read() {
        List<ProjectDto> projectDto = projectService.getAllProject();
        return globalResponseService.responseOK(projectDto);
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse readById(@PathVariable("id") Long id) {
        ProjectDto projectDto = projectService.getProjectDtoById(id);
        return globalResponseService.responseOK(projectDto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse delete(@PathVariable("id") Long id) {
        ProjectDto projectDto = projectService.getProjectDtoById(id);
        projectService.deleteProject(projectDto);
        return globalResponseService.responseOK(projectDto);
    }

    @PutMapping("/read/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse update(@RequestBody ProjectDto data, @PathVariable("id") Long id, BindingResult bindingResult) {

        exceptionService.collectErrorsBindings(bindingResult);
        ProjectDto projectDto = projectService.getProjectDtoById(id);
        // todo: response error
        data.id = projectDto.id;
        data = projectService.saveProject(data);
        return globalResponseService.responseOK(data);
    }
}
