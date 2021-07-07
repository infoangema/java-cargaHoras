package com.angema.hours.app.feature.project.services;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.feature.project.models.Project;
import com.angema.hours.app.feature.project.repositories.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }

    public Project getIdProject(final Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return project.get();
        } else {
            log.info(Messages.ERROR_USER_NOT_FOUND, id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND);
        }
    }

    public Project saveUser(Project project) {
        return projectRepository.save(project);
    }

    public void deleteUser(Project project) {
        try {
            projectRepository.delete(project);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND, e);
        }
    }
}
