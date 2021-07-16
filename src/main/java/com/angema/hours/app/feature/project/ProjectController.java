package com.angema.hours.app.feature.project;

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
        return projectService.saveProject(data, bindingResult);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    private Project delete(@PathVariable("id") Long id) {
        return projectService.deleteProject(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    private Project update(@PathVariable("id") Long id, @Valid @RequestBody Project data, BindingResult bindingResult) {
        return projectService.updateProject(data, id, bindingResult);
    }
}
