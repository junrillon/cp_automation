package Steps.Jira;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class JiraBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "Jira_Checker_Bot";
    }

    @Override
    public String getBotToken() {
        return "5266678102:AAFdXQxtUGGhRn14vWmXISQMZh2dK3dwkRg";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String command = update.getMessage().getText();
        if(command.equals("/runchecker")){

        }
    }
}
