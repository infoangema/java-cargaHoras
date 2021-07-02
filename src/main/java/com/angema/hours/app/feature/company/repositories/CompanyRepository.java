package com.angema.hours.app.feature.company.repositories;

import com.angema.hours.app.feature.company.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository <Company, Long> {

}
