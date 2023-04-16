package angema.applications.hoursloader.app.project;
import angema.applications.hoursloader.app.company.Company;
import angema.applications.hoursloader.app.company.CompanyDto;
import angema.applications.hoursloader.app.user.UserDto;
import angema.applications.hoursloader.core.auth.Auth;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectDto {
    public Long id;
    public String name;
    public String description;
    public CompanyDto company;
    public boolean status;
}
