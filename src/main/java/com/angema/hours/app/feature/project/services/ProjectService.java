package com.angema.hours.app.feature.project.services;

import com.angema.hours.app.feature.project.models.Project;
import com.angema.hours.app.feature.project.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProject () {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectId (final Long id) {
        return projectRepository.findById(id);
    }

    public Project saveProject (Project data) {
        return projectRepository.save(data);
    }

    public void deleteProject (Project project) {
        projectRepository.delete(project);
    }

    public void updateProject (Project data, Project project) {
        project.setName(data.getName());
        project.setDescription(data.getDescription());
        project.setIdCompany(data.getIdCompany());
        projectRepository.save(project);
    }
}
