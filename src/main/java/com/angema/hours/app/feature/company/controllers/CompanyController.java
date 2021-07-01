package com.angema.hours.app.feature.company.controllers;

import com.angema.hours.app.feature.company.models.Company;
import com.angema.hours.app.feature.company.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empresas")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping()
    private ResponseEntity<List<Company>> getAll () {
        List<Company> user = companyService.getAllCompany();
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Company> getId (@PathVariable("id") final String id) {
        try {
            Optional<Company> user = companyService.getCompanyId(Integer.parseInt(id));
            if (user.isPresent())
            {
                return ResponseEntity.ok().body(user.get());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping()
    private ResponseEntity<Company> save (@Valid @RequestBody Company data, BindingResult errorValidation) {
        if (errorValidation.hasErrors())
        {
            return ResponseEntity.badRequest().build();
        }
        Company user = companyService.saveCompany(data);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Company> delete (@PathVariable("id") final String id) {
        try {
            Optional<Company> user = companyService.getCompanyId(Integer.parseInt(id));
            if (user.isPresent())
            {
                companyService.deleteCompany(user.get());
                return ResponseEntity.ok().body(user.get());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping()
    private ResponseEntity<Company> update (@Valid @RequestBody Company data, BindingResult errorValidation) {
        if (errorValidation.hasErrors())
        {
            return ResponseEntity.badRequest().build();
        }
        try {
            Optional<Company> user = companyService.getCompanyId(data.getId());
            if (user.isPresent()) {
                companyService.updateCompany(data,user.get());
                return ResponseEntity.ok().body(user.get());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
