package com.angema.hours.app.feature.project;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.core.errors.ErrorService;
import com.angema.hours.app.feature.company.Company;
import com.angema.hours.app.feature.company.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ErrorService errorService;

    public List<Project> getAllProject() {
        try {
            return projectRepository.findAll();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Project getIdProject(final Long id) {
        try {
            Optional<Project> project = projectRepository.findById(id);
            if (!project.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND);
            }
            return project.get();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Project saveProject(Project project, BindingResult bindingResult) {
        try {
            List<String> errors = errorService.collectErrorsBindings(bindingResult);
            if (!errors.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Arrays.deepToString(errors.toArray()));
            }
            Optional<Company> company = companyRepository.findById(project.getCompany().getId());
            if (!company.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.ERROR_NOT_EXISTS_COMPANY);
            }
            project.setCompany(company.get());
            project.setStatus(true);
            return projectRepository.save(project);
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Project deleteProject(final Long id) {
        try {
            Optional<Project> project = projectRepository.findById(id);
            if (!project.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND);
            }
            projectRepository.delete(project.get());
            return project.get();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Project updateProject(Project data, final Long id, BindingResult bindingResult) {
        try {
            List<String> errors = errorService.collectErrorsBindings(bindingResult);
            if (!errors.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Arrays.deepToString(errors.toArray()));
            }
            Optional<Project> project = projectRepository.findById(id);
            if (!project.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND);
            }
            Optional<Company> company = companyRepository.findById(data.getCompany().getId());
            if (!company.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.ERROR_NOT_EXISTS_COMPANY);
            }
            project.get().setName(data.getName());
            project.get().setDescription(data.getDescription());
            project.get().setCompany(company.get());
            project.get().setStatus(data.isStatus());
            return projectRepository.save(project.get());
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }
}
