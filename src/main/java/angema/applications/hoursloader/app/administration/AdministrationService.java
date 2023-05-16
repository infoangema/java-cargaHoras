package angema.applications.hoursloader.app.administration;

import angema.applications.hoursloader.app.company.Company;
import angema.applications.hoursloader.app.company.CompanyRepository;
import angema.applications.hoursloader.app.project.Project;
import angema.applications.hoursloader.app.project.ProjectService;
import angema.applications.hoursloader.app.record.RecordService;
import angema.applications.hoursloader.app.user.User;
import angema.applications.hoursloader.app.user.UserRepository;
import angema.applications.hoursloader.app.user.UserService;
import angema.applications.hoursloader.core.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private AdministrationRepository administrationRepository;

    @Autowired
    private DateUtil dateUtil;


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
            administrationDto.completedHours = recordService.getMissingRecordsCount(user.id, projects.get(0).id) == 0;
            boolean ifExistFileToActualMonth = administrationRepository.isCurrentMonthInvoiceUploaded(user.id, projects.get(0).id);
            administrationDto.uploadActualBillFile = ifExistFileToActualMonth;
            administrationDto.projectId = projects.get(0).id;
            administrationDto.projectName = projects.get(0).name;
            Long companyId = projectService.findCompanyIdByProjectId(projects.get(0).id);
            Company company = companyRepository.findById(companyId).get();
//            administrationDto.companyId = company.id;
            administrationDto.companyId = 1L;
//            administrationDto.companyName = company.name;
            administrationDto.companyName = "Cardif";
            list.add(administrationDto);
        });
        return list;
    }

    public void setActiveTelegram(Long id, Boolean status) {
        userService.setActiveTelegramStatus(id, status);
    }

    public void saveFile(byte[] file, Long userId, Long projectId, Long companyId, String type) {
        try {

            // Crear una instancia de Archivo
            Administracion archivo = new Administracion();
            archivo.userId = userId;
            archivo.projectId = projectId;
            archivo.companyId = companyId;
            archivo.date = dateUtil.getDateStringFormatDdMmYyyy(new Date());
            if (type.equals("haberes")) {
                archivo.haber = file;
            }

            if (type.equals("facturas")) {
                archivo.factura = file;
            }
            administrationRepository.save(archivo);

        } catch (Exception e) {
            throw new AdministracionException("Error al procesar el archivo: " + e.getMessage());
        }
    }

    public byte[] getFacturaByUserId(Long id, Long id1) {
        return administrationRepository.getCurrentMonthInvoice(id, id1);
    }

}
