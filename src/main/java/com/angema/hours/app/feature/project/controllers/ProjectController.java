package com.angema.hours.app.feature.project.controllers;

import com.angema.hours.app.core.exceptions.ExceptionService;
import com.angema.hours.app.feature.project.models.Project;
import com.angema.hours.app.feature.project.services.ProjectService;
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
@RequestMapping("/proyectos")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ExceptionService exceptionService;

    @GetMapping()
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Project>> getAll() {
        List<Project> project = projectService.getAllProject();
        return ResponseEntity.ok().body(project);
    }

    @GetMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> getId(@PathVariable("id") Long id) {
        Project project = projectService.getIdProject(id);
        return ResponseEntity.ok().body(project);
    }

    @PostMapping()
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> save( @RequestBody Project data, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        Project project = projectService.saveProject(data);
        return ResponseEntity.ok().body(project);
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> delete(@PathVariable("id") Long id) {
        Project project = projectService.getIdProject(id);
        projectService.deleteProject(project);
        return ResponseEntity.ok().body(project);
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> update(@RequestBody Project data, @PathVariable("id") Long id, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        Project project = projectService.getIdProject(id);
        project.setName(data.getName());
        project.setDescription(data.getDescription());
        project.setCompany(data.getCompany());
        Project userUpdated = projectService.saveProject(data);
        return ResponseEntity.ok().body(userUpdated);
    }
}
