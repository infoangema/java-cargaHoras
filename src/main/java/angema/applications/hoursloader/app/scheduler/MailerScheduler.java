package angema.applications.hoursloader.app.scheduler;


import angema.applications.hoursloader.app.company.Company;
import angema.applications.hoursloader.app.pdf.PdfService;
import angema.applications.hoursloader.app.record.Record;
import angema.applications.hoursloader.app.record.RecordService;
import angema.applications.hoursloader.app.user.User;
import angema.applications.hoursloader.app.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Configuration
@Async
@EnableScheduling
public class MailerScheduler {

    @Autowired
    private UserService userService;

    @Autowired
    private RecordService recordService;


    @Scheduled(cron = "0 45 15 * * ?")
    public void sendEmails() {
        List<String> errors = new ArrayList<>();
        List<Company> companies = recordService.findAllCompaniesBySendEmail();
        for (Company company : companies) {
            List<User> users = userService.getUsersByCompany(company);
            for (User user : users) {
                try {
                    recordService.sendEmail(user.id);
                } catch (Exception e) {
                    errors.add("Error al enviar email del user: " + user.name + " - " +e.getMessage());
                    recordService.sendEmailAdmin(errors);
                }
            }
        }
    }
}
