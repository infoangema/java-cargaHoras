package angema.applications.hoursloader.app.company;
import angema.applications.hoursloader.core.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<CompanyDto> getAllCompany() {
        try {
            List<Company> companies = companyRepository.findAllByOrderByIdAsc();
            List<CompanyDto> companyDtos = new ArrayList<>();
            companies.forEach(company -> companyDtos.add(mapCompanyToDto(company)));
            return companyDtos;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public CompanyDto getCompanyDtoById(final Long id) {

        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            return mapCompanyToDto(company.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND);
        }
    }

    public CompanyDto saveCompany(CompanyDto companyDto) {

        Company company = mapDtoToCompany(companyDto);

        try {
            return mapCompanyToDto(companyRepository.save(company));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }



    public void deleteCompany(CompanyDto companyDto) {
        try {
            companyRepository.delete(mapDtoToCompany(companyDto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND, e);
        }
    }


    public Company mapDtoToCompany(CompanyDto companyDto) {
        Company company = new Company();

        company.id = companyDto.id;
        company.name = companyDto.name;
        company.description = companyDto.description;
        company.direction = companyDto.direction;
        company.cuit = companyDto.cuit;
        return company;
    }
    public CompanyDto mapCompanyToDto(Company company) {
        try {
            CompanyDto companyDto = new CompanyDto();

            companyDto.id = company.id;
            companyDto.name = company.name;
            companyDto.description = company.description;
            companyDto.direction = company.direction;
            companyDto.cuit = company.cuit;
            return companyDto;
        } catch (Exception e) {
            return null;
        }
    }
}
