package com.angema.hours.app.feature.project.controllers;

import com.angema.hours.app.feature.project.models.Project;
import com.angema.hours.app.feature.project.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping()
    private ResponseEntity<List<Project>> getAll() {
        List<Project> project = projectService.getAllProject();
        return ResponseEntity.ok().body(project);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Project> getId(@PathVariable("id") Long id) {
        Project project = projectService.getIdProject(id);
        return ResponseEntity.ok().body(project);
    }

    @PostMapping()
    private ResponseEntity<Project> save(@Valid @RequestBody Project data, BindingResult errorValidation) {
        if (errorValidation.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Project project = projectService.saveUser(data);
        return ResponseEntity.ok().body(project);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Project> delete(@PathVariable("id") Long id) {
        Project project = projectService.getIdProject(id);
        projectService.deleteUser(project);
        return ResponseEntity.ok().body(project);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Project> update(@Valid @RequestBody Project data, @PathVariable("id") Long id, BindingResult errorValidation) {
        if (errorValidation.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Project project = projectService.getIdProject(id);
        project.setName(data.getName());
        project.setDescription(data.getDescription());
        project.setIdCompany(data.getIdCompany());
        Project userUpdated = projectService.saveUser(project);
        return ResponseEntity.ok().body(userUpdated);
    }
}
