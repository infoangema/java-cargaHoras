package angema.applications.hoursloader.app.company;

import angema.applications.hoursloader.core.exceptions.ExceptionService;
import angema.applications.hoursloader.core.globalResponse.GlobalResponse;
import angema.applications.hoursloader.core.globalResponse.GlobalResponseService;
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

    @Autowired
    private GlobalResponseService globalResponseService;
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse create(@Valid @RequestBody CompanyDto data, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        CompanyDto companyDto = companyService.saveCompany(data);
        return globalResponseService.responseOK(companyDto);
    }

    @GetMapping("/read")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse read() {
        List<CompanyDto> companyDto = companyService.getAllCompany();
        return globalResponseService.responseOK(companyDto);
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse readById(@PathVariable("id") Long id) {
        CompanyDto companyDto = companyService.getCompanyDtoById(id);
        return globalResponseService.responseOK(companyDto);
    }



    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse delete(@PathVariable("id") Long id) {
        CompanyDto companyDto = companyService.getCompanyDtoById(id);
        companyService.deleteCompany(companyDto);
        return globalResponseService.responseOK(companyDto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse update(@Valid @RequestBody CompanyDto data, @PathVariable("id") Long id, BindingResult bindingResult) {

        exceptionService.collectErrorsBindings(bindingResult);

        CompanyDto companyDto = companyService.getCompanyDtoById(id);
        data.id = id;

        return globalResponseService.responseOK(data);
    }
}
