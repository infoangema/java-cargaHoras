package angema.applications.hoursloader.app.company;
import angema.applications.hoursloader.core.exceptions.ExceptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CompanyDto create(@Valid @RequestBody CompanyDto data, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        CompanyDto companyDto = companyService.saveCompany(data);
        return companyDto;
    }

    @GetMapping("/read")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<CompanyDto> read() {
        List<CompanyDto> companyDto = companyService.getAllCompany();
        return companyDto;
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompanyDto readById(@PathVariable("id") Long id) {
        CompanyDto companyDto = companyService.getCompanyDtoById(id);
        return companyDto;
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompanyDto delete(@PathVariable("id") Long id) {
        CompanyDto companyDto = companyService.getCompanyDtoById(id);
        companyService.deleteCompany(companyDto);
        return companyDto;
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompanyDto update(@RequestBody CompanyDto data, @PathVariable("id") Long id) {
        CompanyDto companyDto = companyService.getCompanyDtoById(id);
        data.id = companyDto.id;
        data = companyService.saveCompany(data);
        return data;
    }
}
