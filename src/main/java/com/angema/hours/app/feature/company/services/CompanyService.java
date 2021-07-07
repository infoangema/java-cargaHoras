package com.angema.hours.app.feature.company.services;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.feature.company.models.Company;
import com.angema.hours.app.feature.company.repositories.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    public Company getIdCompany(final Long id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            return company.get();
        } else {
            log.info(Messages.ERROR_USER_NOT_FOUND, id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND);
        }
    }

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    public void deleteCompany(Company company) {
        try {
            companyRepository.delete(company);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND, e);
        }
    }
}
