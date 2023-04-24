package angema.applications.hoursloader.app.pdf;

import angema.applications.hoursloader.app.record.RecordDto;
import angema.applications.hoursloader.app.record.RecordService;
import angema.applications.hoursloader.app.user.UserDto;
import angema.applications.hoursloader.app.user.UserService;
import angema.applications.hoursloader.core.utils.DateUtil;
import angema.applications.hoursloader.core.utils.EmailSenderUtil;
import angema.applications.hoursloader.core.utils.PdfGenaratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service()
public class PdfService {

    @Autowired
    private PdfGenaratorUtil pdfGenarator;

    @Autowired
    private EmailSenderUtil emailSenderUtil;

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserService userService;

    @Autowired
    private DateUtil dateUtil;

    public byte[] createInforme(UserDto user, List<RecordDto> recordList) {
        try {
            PdfInformeDto informe = new PdfInformeDto();
            informe.user = user;
            informe.project = user.projects.get(0);
            informe.records = recordList;
            informe.hours = recordService.getTotalHours(recordList);
            informe.date = dateUtil.getMonthYearNameString();
            informe.description = user.projects.get(0).description;
            Map<String, PdfInformeDto> data = new HashMap<>();
            data.put("informe", informe);
            String filename = user.projects.get(0).description;
            String path = pdfGenarator.createPdf(filename, data);
            Path pdfPath = Paths.get(path);
            return Files.readAllBytes(pdfPath);
        } catch (Exception e) {
            throw new PdfException("Error al crear el informe" + e.getMessage());
        }
    }

    public String sendEmailByUser(UserDto user, List<RecordDto> records) {
        try {
            String filename = records.get(0).project.description + ".pdf";
            byte[] pdf = createInforme(user, records);
            emailSenderUtil.sendEmailWithAttachmentFromByteArray(pdf, filename);
            return "Email enviado: " + filename;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
