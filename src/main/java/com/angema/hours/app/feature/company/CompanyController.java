package com.angema.hours.app.feature.company;

import com.angema.hours.app.core.errors.ErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private ErrorService errorService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    private List<Company> getAll() {
        return companyService.getAllCompany();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    private Company getId(@PathVariable("id") Long id) {
        return companyService.getIdCompany(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    private Company save(@Valid @RequestBody Company data, BindingResult bindingResult) {
        errorService.collectErrorsBindings(bindingResult);
        return companyService.saveCompany(data);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    private Company delete(@PathVariable("id") Long id) {
        Company company = companyService.getIdCompany(id);
        companyService.deleteCompany(company);
        return company;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    private Company update(@Valid @RequestBody Company data, @PathVariable("id") Long id, BindingResult bindingResult) {
        errorService.collectErrorsBindings(bindingResult);
        Company company = companyService.getIdCompany(id);
        company.setName(data.getName());
        company.setDescription(data.getDescription());
        company.setCuit(data.getCuit());
        company.setDirection(data.getDirection());
        return companyService.saveCompany(company);
    }
}
