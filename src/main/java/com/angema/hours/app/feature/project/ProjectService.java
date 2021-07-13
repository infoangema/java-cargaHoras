package com.angema.hours.app.feature.project;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.feature.company.Company;
import com.angema.hours.app.feature.company.CompanyRepository;
import com.angema.hours.app.feature.company.CompanyService;
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

    @Autowired
    private CompanyRepository companyRepository;

    public List<Project> getAllProject() {
        try {
            return projectRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Project getIdProject(final Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return project.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND);
        }
    }

    public Project saveProject(Project project) {
        Optional<Company> dataCompany = companyRepository.findById(project.getCompany().getId());
        if (!dataCompany.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_NOT_EXISTS_COMPANY);
        }
        try {
            projectRepository.save(project);
            project.setCompany(dataCompany.get());
            return project;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public void deleteProject(Project project) {
        try {
            projectRepository.delete(project);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND, e);
        }
    }
}
