package com.angema.hours.app.feature.company.services;

import com.angema.hours.app.feature.company.models.Company;
import com.angema.hours.app.feature.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompany () {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyId (final Long id) {
        return companyRepository.findById(id);
    }

    public Company saveCompany (Company data) {
        return companyRepository.save(data);
    }

    public void deleteCompany (Company company) {
        companyRepository.delete(company);
    }

    public void updateCompany (Company data, Company company) {
        company.setName(data.getName());
        company.setDescription(data.getDescription());
        company.setCuit(data.getCuit());
        company.setDirection(data.getDirection());
        companyRepository.save(company);
    }
}
