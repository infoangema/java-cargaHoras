package angema.applications.hoursloader.app.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/telegram")
public class TelegramController {

    @Autowired
    private TelegramService telegramService;

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestBody TelegramMsgDto messageDto) {
        telegramService.sendMessage(messageDto.idUser, messageDto.msg);
        return "Mensaje enviado";
    }

    @PostMapping("/sendMessage-await")
    public String sendMessageAndWaitForResponse(@RequestBody TelegramMsgDto messageDto) {
        telegramService.waitForResponse(messageDto.idUser, messageDto.msg);
        return "Mensaje enviado";
    }

}
