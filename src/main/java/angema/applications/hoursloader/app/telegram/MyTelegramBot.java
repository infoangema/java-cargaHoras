package angema.applications.hoursloader.app.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MyTelegramBot extends TelegramLongPollingBot {

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

//    public <T extends BotApiMethod<?>> T executeApiMethod(T method) throws TelegramApiException {
//        return execute(method);
//    }
}
