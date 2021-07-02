package com.angema.hours.app.feature.project.controllers;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.feature.project.models.Project;
import com.angema.hours.app.feature.project.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping()
    private ResponseEntity<List<Project>> getAll () {
        List<Project> user = projectService.getAllProject();
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Project> getId (@PathVariable("id") final String id) {
        try {
            Optional<Project> user = projectService.getProjectId(Long.parseLong(id));
            if (user.isPresent()) {
                return ResponseEntity.ok().body(user.get());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (NumberFormatException e) {
            log.info(Messages.ERROR_IDCHARACTER,id);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping()
    private ResponseEntity<Project> save (@Valid @RequestBody Project data, BindingResult errorValidation) {
        if (errorValidation.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Project user = projectService.saveProject(data);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Project> delete (@PathVariable("id") final String id) {
        try {
            Optional<Project> user = projectService.getProjectId(Long.parseLong(id));
            if (user.isPresent()) {
                projectService.deleteProject(user.get());
                return ResponseEntity.ok().body(user.get());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (NumberFormatException e) {
            log.info(Messages.ERROR_IDCHARACTER,id);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping()
    private ResponseEntity<Project> update (@Valid @RequestBody Project data, BindingResult errorValidation) {
        if (errorValidation.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Optional<Project> user = projectService.getProjectId(data.getId());
            if (user.isPresent()) {
                projectService.updateProject(data,user.get());
                return ResponseEntity.ok().body(user.get());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
