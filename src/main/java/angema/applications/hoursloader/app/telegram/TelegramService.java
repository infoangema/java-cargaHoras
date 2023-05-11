package angema.applications.hoursloader.app.telegram;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TelegramService extends TelegramLongPollingBot {

    private static final String BOT_USERNAME = "angemaBot";
    private static final String BOT_TOKEN = "6233647430:AAE84YzFZULRMYV8lcDA6cj4gZ0h9OJPo4s";

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyTelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            SendMessage message = new SendMessage(chatId, "You said: " + messageText);
            message.setParseMode(ParseMode.HTML);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    public void sendMessage(String chatId, String messageText) {
        SendMessage message = new SendMessage(chatId, messageText);
        message.setParseMode(ParseMode.HTML);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void waitForResponse(String chatId, String question) {
        SendMessage message = new SendMessage(chatId, question);
        message.setParseMode(ParseMode.HTML);
        message.setReplyMarkup(new ForceReplyKeyboard());

        Message responseMessage = null;
        while (responseMessage == null) {
            try {
                responseMessage = execute(message);
                Thread.sleep(1000);
            } catch (TelegramApiException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        String responseText = responseMessage.getText();
        List<Message> msgs = getUpdates(chatId);
        // Procesar la respuesta del usuario aquí
    }

    public List<Message> getUpdates(String idUser) {
        GetUpdates request = new GetUpdates();
        request.setOffset(Integer.valueOf(idUser));
        request.setLimit(10);
        request.setTimeout(10);
        List<Message> messages = new ArrayList<>();

        try {
            List<org.telegram.telegrambots.meta.api.objects.Update> updates = execute(request);
            for (org.telegram.telegrambots.meta.api.objects.Update update : updates) {
                if (update.hasMessage() && update.getMessage().hasText()) {
                    if (update.getMessage().getChatId().equals(Long.parseLong(idUser))) {
                        messages.add(update.getMessage());
                    }
                }
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return messages;
    }

    public MessageDto getMessageDtoByDate(String idUser, String date) {
        GetUpdates request = new GetUpdates();
        request.setOffset(Integer.valueOf(idUser));
//        request.setLimit(1);
        request.setTimeout(1);
        List<MessageDto> messages = new ArrayList<>();

        try {
            List<org.telegram.telegrambots.meta.api.objects.Update> updates = execute(request);
            for (org.telegram.telegrambots.meta.api.objects.Update update : updates) {
                boolean ifHaveMsg = update.hasMessage() && update.getMessage().hasText();
                boolean ifPersonalChat = update.getMessage().getChat().getId().toString().equals(idUser);
                if (ifHaveMsg && ifPersonalChat) {
                    boolean ifReplyHaveMsg = update.getMessage().getReplyToMessage() != null;
                    if (ifReplyHaveMsg && update.getMessage().getReplyToMessage().getText().contains(date)) {
                        MessageDto messageDto = new MessageDto();
                        messageDto.idUser = idUser;
                        messageDto.msg = update.getMessage().getText();
                        messageDto.consultation = update.getMessage().getReplyToMessage().getText();
                        messages.add(messageDto);
                    }
                }
            }
        } catch (TelegramApiException e) {
            return null;
        }
        if (messages.size() > 0) {
            return messages.get(0);
        }
        return null;
    }


}
