package com.angema.hours.app.feature.company.repositories;

import com.angema.hours.app.feature.company.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository <Company, Long> {

    public Optional<Company> findById(int id);
}
