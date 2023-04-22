package angema.applications.hoursloader.app.pdf;

import angema.applications.hoursloader.app.record.RecordDto;
import angema.applications.hoursloader.app.record.RecordService;
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

    public byte[] createPdf(List<RecordDto> recordList, String filename) throws Exception {
        Map<String, List<RecordDto>> data = new HashMap<String, List<RecordDto>>();
        data.put("recordList", recordList);
        String path = pdfGenarator.createPdf(filename, data);
        Path pdfPath = Paths.get(path);
        return Files.readAllBytes(pdfPath);
    }

    public String sendEmailByUserId(long id) {
        try {
            List<RecordDto> records = recordService.getRecordDtoByUserId(id);
            String filename = records.get(0).description + ".pdf";
            byte[] pdf = createPdf(records, filename);
            emailSenderUtil.sendEmailWithAttachmentFromByteArray(pdf, filename);
            return "Email enviado: " + filename;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
