package angema.applications.hoursloader.app.scheduler;

import angema.applications.hoursloader.app.project.ProjectDto;
import angema.applications.hoursloader.app.record.Record;
import angema.applications.hoursloader.app.record.RecordDto;
import angema.applications.hoursloader.app.record.RecordService;
import angema.applications.hoursloader.app.telegram.MessageDto;
import angema.applications.hoursloader.app.telegram.TelegramService;
import angema.applications.hoursloader.app.user.UserDto;
import angema.applications.hoursloader.app.user.UserService;
import angema.applications.hoursloader.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;
@Configuration
@EnableScheduling
@Async
public class MailerScheduler {

    @Autowired
    private UserService userService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private TelegramService telegramService;

    @Autowired
    private DateUtil dateUtil;

//    @PostConstruct
//    public void runOnStartup() {
////        findTelegramMsg();
////        sendTelegramMsg();
//    }

    @Async
    @Scheduled(cron = "0 0 */1 * * *")
    public void findTelegramMsg() {
        //feat: enviar mensajes de telegram a los usuarios para la carga de horas.
        List<UserDto> userList = userService.getAllUserToSendTelegramMsg();
        userList.forEach(user -> {
            try {
                // feat: verificar si hay dias faltantes y enviar mensaje.
                if (msgVerificator(user)) return;

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Async
    @Scheduled(cron = "0 3 17 * * *")
    public void sendTelegramMsg() {
        //feat: enviar mensajes de telegram a los usuarios para la carga de horas.
        List<UserDto> userList = userService.getAllUserToSendTelegramMsg();
        userList.forEach(user -> {
            msgVerificator(user);
            try {
                // feat: verificar si la fecha actual esta cargada y enviar registro.
                String todayStr = dateUtil.getDateStringFormatDdMmYyyy(new Date());
                Record recorToDay = recordService.getRecordByDate(user.id, todayStr);
                if(recorToDay == null){
                    String msg = todayStr +": Esta fecha no la cargaste " + user.name + ", ¿la queres cargar ahora?.";
                    telegramService.sendMessage(user.telegramUserId, msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private boolean msgVerificator(UserDto user) {
        // feat: verificar si hay dias faltantes y enviar mensaje.
        List<String> dateList = recordService.getMissingDaysString(user.id);

        if (dateList.isEmpty()) {
            return true;
        }
        dateList.forEach(date -> {
            // feat: obtener update de mensajes de telegram.
            MessageDto message = telegramService.getMessageDtoByDate(user.telegramUserId, date);

            if (message == null) {
                String msg2 = date +": Esta fecha no la cargaste " + user.name + ", ¿la queres cargar ahora?.";
                telegramService.sendMessage(user.telegramUserId, msg2);

                return;
            }

            // todo: guardar registro.
            RecordDto recordDto = new RecordDto();
            recordDto.date = date;
            recordDto.hours = 8;
            recordDto.description = message.msg;
            recordDto.user = new UserDto();
            recordDto.user.id = user.id;
            recordDto.project = new ProjectDto();
            recordDto.project.id = user.projects.get(0).id;
            recordService.saveRecord(recordDto);
        });
        return false;
    }

    //    @Scheduled(cron = "0 45 15 * * ?")
//    public void sendEmails() {
//        List<String> errors = new ArrayList<>();
//        List<Company> companies = recordService.findAllCompaniesBySendEmail();
//        for (Company company : companies) {
//            List<User> users = userService.getUsersByCompany(company);
//            for (User user : users) {
//                try {
//                    recordService.sendEmail(user.id);
//                } catch (Exception e) {
//                    errors.add("Error al enviar email del user: " + user.name + " - " +e.getMessage());
//                    recordService.sendEmailAdmin(errors);
//                }
//            }
//        }
//    }
}
