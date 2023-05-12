package angema.applications.hoursloader.app.administration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}
