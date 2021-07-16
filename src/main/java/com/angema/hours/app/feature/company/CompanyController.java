package com.angema.hours.app.feature.company;

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
        return companyService.saveCompany(data, bindingResult);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    private Company delete(@PathVariable("id") Long id) {
        return companyService.deleteCompany(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    private Company update(@PathVariable("id") Long id, @Valid @RequestBody Company data, BindingResult bindingResult) {
        return companyService.updateCompany(data, id, bindingResult);
    }
}
