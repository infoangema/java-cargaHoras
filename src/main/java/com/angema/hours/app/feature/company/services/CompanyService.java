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
        try {
            return companyRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Company getIdCompany(final Long id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            return company.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_COMPANY_NOT_FOUND);
        }
    }

    public Company saveCompany(Company company) {
        try {
            return companyRepository.save(company);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public void deleteCompany(Company company) {
        try {
            companyRepository.delete(company);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_COMPANY_NOT_FOUND, e);
        }
    }
}
