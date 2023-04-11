package com.angema.hours.app.feature.company;

import com.angema.hours.app.core.exceptions.ExceptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ExceptionService exceptionService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Company> create(@Valid @RequestBody Company data, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        Company company = companyService.saveCompany(data);
        return ResponseEntity.ok().body(company);
    }

    @GetMapping("/read")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Company>> read() {
        List<Company> company = companyService.getAllCompany();
        return ResponseEntity.ok().body(company);
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Company> readById(@PathVariable("id") Long id) {
        Company company = companyService.getIdCompany(id);
        return ResponseEntity.ok().body(company);
    }



    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Company> delete(@PathVariable("id") Long id) {
        Company company = companyService.getIdCompany(id);
        companyService.deleteCompany(company);
        return ResponseEntity.ok().body(company);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Company> update(@Valid @RequestBody Company data, @PathVariable("id") Long id, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        Company company = companyService.getIdCompany(id);
        company.setName(data.getName());
        company.setDescription(data.getDescription());
        company.setCuit(data.getCuit());
        company.setDirection(data.getDirection());
        Company userUpdated = companyService.saveCompany(company);
        return ResponseEntity.ok().body(userUpdated);
    }
}
