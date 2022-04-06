package steps.jira;

import base.BaseUtil;
import org.openqa.selenium.WebDriver;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class JiraBot extends TelegramLongPollingBot{
    private final BaseUtil base;

    public JiraBot(BaseUtil base) {
        this.base = base;
    }

    private static WebDriver Driver;
    public static WebDriver getDriver(){
        return Driver;
    }
    public static void setWebDriver(WebDriver driver){
        Driver = driver;
    }

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
        jiraCardChecking asd = new jiraCardChecking(base);
        String command = update.getMessage().getText();
        //System.out.println(update.getMessage().getText());
        if(command.equals("/runchecker")){
            System.out.println("asd");
            base.Driver.navigate().to("https://id.atlassian.com/login");
            
        }
    }
}
