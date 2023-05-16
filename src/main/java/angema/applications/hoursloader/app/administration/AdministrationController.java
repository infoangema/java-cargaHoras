package angema.applications.hoursloader.app.administration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/administration")
public class AdministrationController {

    @Autowired
    private AdministrationService administrationService;

    @GetMapping("/read")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<AdministrationDto> read() {
        List<AdministrationDto> list = administrationService.getAllAdministration();
        return list;
    }

    @PutMapping("/user-id/{userId}/status/{status}/set-active-telegram")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void setActiveTelegram(@PathVariable("userId") Long id, @PathVariable("status") Boolean status) {
        administrationService.setActiveTelegram(id, status);
    }

//    @PostMapping("/upload-file/user-id/{userId}/project-id/{projectId}/company-id/{companyId}/upload-file-{type}")
//    public void uploadFile(@RequestBody byte[] fileData, @PathVariable("userId") Long userId, @PathVariable("projectId") Long projectId, @PathVariable("companyId") Long companyId, @PathVariable("type") String type) {
//        administrationService.saveFile(fileData, userId, projectId, companyId, type);
//    }

    @PostMapping("/upload-file/user-id/{userId}/project-id/{projectId}/company-id/{companyId}/upload-file-{type}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void uploadFile2(@RequestParam("pdfFile") MultipartFile file, @PathVariable("userId") Long userId, @PathVariable("projectId") Long projectId, @PathVariable("companyId") Long companyId, @PathVariable("type") String type) {
        byte[] fileBlob = null;
        try {
            fileBlob = file.getBytes();
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
        }
        administrationService.saveFile(fileBlob, userId, projectId, companyId, type);
    }
}
