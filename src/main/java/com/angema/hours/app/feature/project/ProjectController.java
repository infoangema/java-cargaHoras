package com.angema.hours.app.feature.project;

import com.angema.hours.app.core.exceptions.ExceptionService;
import com.angema.hours.app.core.globalResponse.GlobalResponse;
import com.angema.hours.app.core.globalResponse.GlobalResponseService;
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
    public ResponseEntity<ProjectDto> create( @RequestBody ProjectDto data, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        ProjectDto projectDto = projectService.saveProject(data);
        return ResponseEntity.ok().body(projectDto);
    }

    @GetMapping("/read")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse read() {
        List<ProjectDto> projectDto = projectService.getAllProject();
        return globalResponseService.responseOK(projectDto);
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProjectDto> readById(@PathVariable("id") Long id) {
        ProjectDto projectDto = projectService.getProjectDtoById(id);
        return ResponseEntity.ok().body(projectDto);
    }



    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProjectDto> delete(@PathVariable("id") Long id) {
        ProjectDto projectDto = projectService.getProjectDtoById(id);
        projectService.deleteProject(projectDto);
        return ResponseEntity.ok().body(projectDto);
    }

    @PutMapping("/read/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProjectDto> update(@RequestBody ProjectDto data, @PathVariable("id") Long id, BindingResult bindingResult) {

        exceptionService.collectErrorsBindings(bindingResult);
        ProjectDto projectDto = projectService.getProjectDtoById(id);
        // todo: response error
        data.id = projectDto.id;
        data = projectService.saveProject(data);
        return ResponseEntity.ok().body(data);
    }
}
