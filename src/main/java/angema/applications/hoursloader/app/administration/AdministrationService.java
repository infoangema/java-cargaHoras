package angema.applications.hoursloader.app.administration;
import angema.applications.hoursloader.app.company.Company;
import angema.applications.hoursloader.app.company.CompanyRepository;
import angema.applications.hoursloader.app.project.Project;
import angema.applications.hoursloader.app.project.ProjectService;
import angema.applications.hoursloader.app.record.Record;
import angema.applications.hoursloader.app.record.RecordDto;
import angema.applications.hoursloader.app.record.RecordService;
import angema.applications.hoursloader.app.user.User;
import angema.applications.hoursloader.app.user.UserRepository;
import angema.applications.hoursloader.app.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdministrationService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserService userService;


    public List<AdministrationDto> getAllAdministration() {
        List<AdministrationDto> list = new ArrayList<>();
        List<User> users = userRepository.findByProjects_Company_Id(1L);
        users.forEach(user -> {
            log.info("User: " + user.toString());
            AdministrationDto administrationDto = new AdministrationDto();
            administrationDto.userId = user.id;
            administrationDto.userName = user.name;
            administrationDto.useTelegram = user.activeTelegram;
            List<Project> projects = projectService.findProjectByUserId(user.id);
//            administrationDto.incomeFileStatus = user.incomeFileStatus;
//            administrationDto.billFileStatus = user.billFileStatus;
            Integer hours = recordService.findTotalsHoursForCurrentMonth(user.id);
            administrationDto.hours = hours;
            administrationDto.projectId = projects.get(0).id;
            administrationDto.projectName = projects.get(0).name;
            Long companyId = projectService.findCompanyIdByProjectId(projects.get(0).id);
            Company company = companyRepository.findById(companyId).get();
            administrationDto.companyId = company.id;
            administrationDto.companyName = company.name;
            list.add(administrationDto);
        });
        return list;
    }

    public void setActiveTelegram(Long id, Boolean status) {
        userService.setActiveTelegramStatus(id, status);
    }
}
