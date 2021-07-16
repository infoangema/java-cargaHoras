package com.angema.hours.app.feature.company;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.core.errors.ErrorService;
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
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ErrorService errorService;

    public List<Company> getAllCompany() {
        try {
            return companyRepository.findAll();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Company getIdCompany(final Long id) {
        try {
            Optional<Company> company = companyRepository.findById(id);
            if (!company.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_COMPANY_NOT_FOUND);
            }
            return company.get();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Company saveCompany(Company company, BindingResult bindingResult) {
        try {
            List<String> errors = errorService.collectErrorsBindings(bindingResult);
            if (!errors.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Arrays.deepToString(errors.toArray()));
            }
            return companyRepository.save(company);
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Company deleteCompany(final Long id) {
        try {
            Optional<Company> company = companyRepository.findById(id);
            if (!company.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_COMPANY_NOT_FOUND);
            }
            companyRepository.delete(company.get());
            return company.get();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Company updateCompany(Company data, final Long id, BindingResult bindingResult) {
        try {
            List<String> errors = errorService.collectErrorsBindings(bindingResult);
            if (!errors.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Arrays.deepToString(errors.toArray()));
            }
            Optional<Company> company = companyRepository.findById(id);
            if (!company.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_COMPANY_NOT_FOUND);
            }
            company.get().setName(data.getName());
            company.get().setDescription(data.getDescription());
            company.get().setCuit(data.getCuit());
            company.get().setDirection(data.getDirection());
            return companyRepository.save(company.get());
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }
}
