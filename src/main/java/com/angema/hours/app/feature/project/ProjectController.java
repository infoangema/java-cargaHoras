package com.angema.hours.app.feature.project;

import com.angema.hours.app.core.errors.ErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private ErrorService errorService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    private List<Project> getAll() {
        return projectService.getAllProject();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    private Project getId(@PathVariable("id") Long id) {
        return projectService.getIdProject(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    private Project save(@Valid @RequestBody Project data, BindingResult bindingResult) {
        errorService.collectErrorsBindings(bindingResult);
        return projectService.saveProject(data);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    private Project delete(@PathVariable("id") Long id) {
        Project project = projectService.getIdProject(id);
        projectService.deleteProject(project);
        return project;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    private Project update(@Valid @RequestBody Project data, @PathVariable("id") Long id, BindingResult bindingResult) {
        errorService.collectErrorsBindings(bindingResult);
        Project project = projectService.getIdProject(id);
        project.setName(data.getName());
        project.setDescription(data.getDescription());
        project.setCompany(data.getCompany());
        return projectService.saveProject(data);
    }
}
