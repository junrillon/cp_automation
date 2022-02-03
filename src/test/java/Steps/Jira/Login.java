package Steps.Jira;

import Base.BaseUtil;
import Pages.Jira.JiraObjects;
import Pages.TestRail.TestRailObjects;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Login extends BaseUtil{

    public static void main(String[] args){
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new JiraBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private static HttpURLConnection connection;
    private final BaseUtil base;

    public Login(BaseUtil base) {
        this.base = base;
    }

    @Given("^I access jira website ([^\"]*)$")
    public void accessJiraWebsite(String feUrl){
        //Open Chrome with URL
        base.Driver.navigate().to(feUrl);
        base.Driver.manage().window().maximize();
        System.out.println("Accessed jira website.");
    }

    @When("^I input username ([^\"]*) and password ([^\"]*)$")
    public void inputCredentials(String username, String password){
        JiraObjects jiraObjects = new JiraObjects(base.Driver);
        WebDriverWait wait = new WebDriverWait(base.Driver, 5);

        int homeBanner = base.Driver.findElements(By.xpath("//header[@role='banner']")).size();
        if(homeBanner == 0){
            WebElement usernameField = jiraObjects.username;
            WebElement pwField = jiraObjects.password;
            WebElement loginBtn = jiraObjects.loginButton;

            wait.until(ExpectedConditions.visibilityOf(usernameField));
            usernameField.sendKeys(username);

            wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
            loginBtn.click();

            wait.until(ExpectedConditions.visibilityOf(pwField));
            pwField.sendKeys(password);

            wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
            loginBtn.click();

            System.out.println("Input credentials then logged in.");

        } else {
            System.out.println("You're already logged in.");
        }


    }

    @And("^I select project ([^\"]*)$")
    public void selectProject(String projectName) throws InterruptedException {
        JiraObjects jiraObjects = new JiraObjects(base.Driver);
        WebDriverWait wait = new WebDriverWait(base.Driver, 5);

        WebElement homePage = jiraObjects.homePage;
        wait.until(ExpectedConditions.visibilityOf(homePage));

        Thread.sleep(5000);
        WebElement project  = base.Driver.findElement(By.xpath("//p[@class='name' and contains(text(),'"+ projectName +"')]"));
        wait.until(ExpectedConditions.elementToBeClickable(project));
        project.click();
        System.out.println("Select project: "+projectName);
    }

    @And("^I access backlog")
    public void accessBacklog(){
        JiraObjects jiraObjects = new JiraObjects(base.Driver);
        WebDriverWait wait = new WebDriverWait(base.Driver, 5);

        wait.until(ExpectedConditions.visibilityOf(jiraObjects.backlog));
        boolean x = jiraObjects.backlog.isDisplayed();

        wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.backlog));
        jiraObjects.backlog.click();
        System.out.println("Clicked backlog.");
    }

    StringBuffer resultContent = new StringBuffer();
    String advanceSprint = null;

    @And("^I search ([^\"]*) in backlog")
    public void searchBacklog(String sprint, DataTable testrailCreds) throws InterruptedException {
        JiraObjects jiraObjects = new JiraObjects(base.Driver);
        TestRailObjects testRailObjects = new TestRailObjects(base.Driver);
        JiraBot jiraBot = new JiraBot();
        Locator locator = new Locator(base.Driver);

        WebDriverWait wait = new WebDriverWait(base.Driver, 5);
        WebDriverWait longwait = new WebDriverWait(base.Driver, 15);

        SendMessage message = new SendMessage();

        advanceSprint = sprint.replace('_',' ');

        List<List<String>> data = testrailCreds.asLists(String.class);
        String username = data.get(1).get(0);
        String password = data.get(1).get(1);

        String oldTab = base.Driver.getWindowHandle();

        Thread.sleep(3000);
        WebElement sprintHeader = base.Driver.findElement(By.xpath("//div[@class='header-left']/div[contains(text(),'"+ advanceSprint +"')]"));
        ((JavascriptExecutor) base.Driver).executeScript("arguments[0].scrollIntoView(true);", sprintHeader);
        Thread.sleep(500);
        System.out.println("Searched " + advanceSprint + " in backlog. \n");
        System.out.println(advanceSprint);

        String issueCount = base.Driver.findElement(By.xpath("//div[@class='header-left']/div[contains(text(),'"+ advanceSprint +"')]/following-sibling::div[@class='ghx-issue-count']")).getText();
        String count = issueCount.replace(" issues","");
        int converted_issueCount = Integer.parseInt(count);
        //planned
        String advanceSprintXpath = "//div[@class='ghx-backlog-container ghx-sprint-planned js-sprint-container ghx-open ui-droppable' and div[@class='ghx-backlog-header js-sprint-header' and div[@class='header-left' and div[@class='ghx-name' and contains(text(), '"+ advanceSprint +"')]]]]";
        String activeSprintXpath = "//div[@class='ghx-backlog-container ghx-sprint-active js-sprint-container ghx-open ui-droppable' and div[@class='ghx-backlog-header js-sprint-header' and div[@class='header-left' and div[@class='ghx-name' and contains(text(), '"+ advanceSprint +"')]]]]";
        String perCardXpath = "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]";
        String perCardTitleXpath = "//span[@class='ghx-inner']";
        String perCardNumberXpath = "//span[@class='ghx-end ghx-items-container']/a";
        String perCardAssignee = "//span[@class='ghx-end ghx-items-container']/img"; // <-- get Alt
        String perCardTester = "//div[contains(concat(' ',@class,' '), ' ghx-plan-extra-fields ')]//span[3]/span";
        String perCardStatus = "//div[contains(concat(' ',@class,' '), ' ghx-plan-extra-fields ')]//span[1]/span";
        String testCases_stats;
        String testRuns_stats;

        String result = null;
        String token = "5266678102:AAFdXQxtUGGhRn14vWmXISQMZh2dK3dwkRg";

        for(int i = 1; i <= converted_issueCount; i++){
            //locate card status element and get text
            WebElement cardStatus = base.Driver.findElement(By.xpath(advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardStatus));
            String extractedCardStatus = cardStatus.getText();

            //locate card title element and get text
            WebElement cardTitle = base.Driver.findElement(By.xpath(advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardTitleXpath));
            String extractedCardTitle = cardTitle.getText();

            //check if card has tester
            int cardTester_isNull = base.Driver.findElements(By.xpath(advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardTester)).size();
            String extractedCardTester;
            if(cardTester_isNull > 0){
                //locate card title element and get text
                extractedCardTester = base.Driver.findElement(By.xpath(advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardTester)).getText();
                String testerName = extractedCardTester;
                String[] splitStr = extractedCardTester.split("\\s+");

                // Switch statement over above string
                switch (splitStr[0]) {
                    case "Jun":
                        extractedCardTester = "@Juuuun";
                        break;
                    case "Christian":
                        extractedCardTester = "@mxxx67";
                        break;
                    case "Jean":
                        extractedCardTester = "@jeanpaola";
                        break;
                    case "jerald":
                        extractedCardTester = "@jerald Manamtam";
                        break;
                    case "Mae":
                        extractedCardTester = "@maaarmas";
                        break;
                    case "Marjorie":
                        extractedCardTester = "@Marj0819";
                        break;
                    case "Robert":
                        extractedCardTester = "@robertcaneta";
                        break;
                    case "Sherylle":
                        extractedCardTester = "@Sheeey";
                        break;
                    case "Bianca":
                        extractedCardTester = "@yangcavelez ";
                        break;
                    case "Arvin":
                        switch(splitStr[1])
                        {
                            case "Dacio":
                                extractedCardTester = "@daysofdash";
                                break;
                            case "Oliva":
                                extractedCardTester = "@threem06";
                                break;
                            default:
                        }
                        break;
                    case "sysadmin":
                        extractedCardTester = "sysadmin";
                        break;
                    default:
                        extractedCardTester = "None";
                        System.out.println("no match");
                }
            } else {
                extractedCardTester = "None";
            }

            //check if card has assignee/dev
            int cardAssignee_isNull = base.Driver.findElements(By.xpath(advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardAssignee)).size();
            String extractedCardAssignee;
            if(cardAssignee_isNull > 0){
                //locate card assignee element and get text
                extractedCardAssignee = base.Driver.findElement(By.xpath(advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardAssignee)).getAttribute("alt");
            } else {
                extractedCardAssignee = "Assignee: None";
            }

            //check cards inside sprint and then click
            WebElement cardNumber = base.Driver.findElement(By.xpath(advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardNumberXpath));
            String extractedCardNumber = cardNumber.getAttribute("title");
            cardNumber.click();

            Thread.sleep(1000);

            //check sprint element inside detailed view then scroll to it
            WebElement sprintInsideDetails = base.Driver.findElement(By.xpath("//h2[contains(text(), 'Sprint')]"));
            wait.until(ExpectedConditions.visibilityOf(jiraObjects.cardDetailedView));
            ((JavascriptExecutor) base.Driver).executeScript("arguments[0].scrollIntoView(true);", sprintInsideDetails);
            Thread.sleep(500);

            //check testcases element inside detailed view then click
            wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testCases));
            jiraObjects.testCases.click();
            Thread.sleep(2000);

            //Switch to iframes
            base.Driver.switchTo().frame(base.Driver.findElement(By.xpath("//div[@id='ghx-detail-view']//iframe[contains(@id,'com.testrail.jira.testrail-plugin__panel-references')]")));
            longwait.until(ExpectedConditions.elementToBeClickable(base.Driver.findElement(By.xpath("//iframe[@id='tr-frame-panel-references']"))));
            base.Driver.switchTo().frame(base.Driver.findElement(By.xpath("//iframe[@id='tr-frame-panel-references']")));

            //check if already logged in to testrail
            int loginToTestrail = locator.loginToTestRail().size();
            if(loginToTestrail > 0){
                System.out.println("Not yet logged in to testrail.");
                wait.until(ExpectedConditions.elementToBeClickable(locator.loginToTestRailButton()));
                locator.loginToTestRailButton().click();

                for(String winHandle : base.Driver.getWindowHandles()){
                    base.Driver.switchTo().window(winHandle);
                }

                wait.until(ExpectedConditions.visibilityOf(testRailObjects.username));
                testRailObjects.username.sendKeys(username);

                wait.until(ExpectedConditions.visibilityOf(testRailObjects.password));
                testRailObjects.password.sendKeys(password);

                wait.until(ExpectedConditions.elementToBeClickable(testRailObjects.loginButton));
                testRailObjects.loginButton.click();

                boolean testrailHeader_isPresent = testRailObjects.testrailHeader.isDisplayed();
                if(testrailHeader_isPresent){
                    System.out.println("Successfully logged in to testrail");
                    base.Driver.close();

                    Thread.sleep(500);
                    base.Driver.switchTo().window(oldTab);
                    base.Driver.navigate().refresh();
                }

                searchBacklog(advanceSprint, testrailCreds);

            } else {

                int testCases = locator.testCases_status().size();
                if (testCases > 0) {
                    testCases_stats = "None";
                } else {
                    testCases_stats = "Already have test cases.";
                }

                //Switch back to default frame
                base.Driver.switchTo().defaultContent();
                wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testCasesBack));
                jiraObjects.testCasesBack.click();
                Thread.sleep(500);

                wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testRuns));
                jiraObjects.testRuns.click();
                Thread.sleep(500);

                //Switch to iframes
                base.Driver.switchTo().frame(base.Driver.findElement(By.xpath("//div[@id='ghx-detail-view']//iframe[contains(@id,'com.testrail.jira.testrail-plugin__panel-runsreferences')]")));
                longwait.until(ExpectedConditions.elementToBeClickable(base.Driver.findElement(By.xpath("//iframe[@id='tr-frame-panel-runsreferences']"))));
                base.Driver.switchTo().frame(base.Driver.findElement(By.xpath("//iframe[@id='tr-frame-panel-runsreferences']")));

                //wait.until(ExpectedConditions.elementToBeClickable((WebElement) locator.testRuns_status()));
                int testRuns = locator.testRuns_status().size();
                if (testCases > 0) {
                    testRuns_stats = "None";
                } else {
                    testRuns_stats = "Already have test runs.";
                }
                //    System.out.println("tr done");

                base.Driver.switchTo().defaultContent();
                wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testRunsBack));
                jiraObjects.testRunsBack.click();
                Thread.sleep(500);

                result = (i + ". (" + extractedCardNumber + ") " + extractedCardTitle + "%0A" +
                        "•Tester: " + extractedCardTester + "  |  •" + extractedCardAssignee + "%0A" +
                        "•Status: " + extractedCardStatus + "%0A" +
                        "─ Test Cases: " + testCases_stats + "%0A" + "─ Test Runs: " + testRuns_stats + "%0A%0A");

                System.out.println(i + ". (" + extractedCardNumber + ") " + extractedCardTitle + "\n" +
                        "•Tester: " + extractedCardTester + "  |  •" + extractedCardAssignee + "\n" +
                        "•Status: " + extractedCardStatus + "\n" +
                        "─ Test Cases: " + testCases_stats + "\n" + "─ Test Runs: " + testRuns_stats + "\n");
                resultContent.append(result);
                Thread.sleep(1000);
            }
        }
    }

    @And("^I send results in telegram")
    public void sendResultsInTelegram(DataTable telegramCreds){
        List<List<String>> data = telegramCreds.asLists(String.class);
        String token = data.get(1).get(0);
        String chatId = data.get(1).get(1);

        try {
            URL url = new URL("https://api.telegram.org/bot"+token+"/sendMessage?chat_id="+chatId+"&text="+advanceSprint+"%0A"+resultContent.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println(status + ": " + url);

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


}


//    int projectSize = base.Driver.findElements(By.xpath("//p[@class='name' and contains(text(),'"+ projectName +"')]")).size();
//        while(projectSize == 0) {
//                System.out.println("Waiting for project lists");
//                Thread.sleep(5000);
//
//                if(projectSize > 0){
//                WebElement project  = base.Driver.findElement(By.xpath("//p[@class='name' and contains(text(),'"+ projectName +"')]"));
//                wait.until(ExpectedConditions.visibilityOf(project));
//                project.click();
//                }
//                }

//base.Driver.navigate().to("https://api.telegram.org/bot"+token+"/sendMessage?chat_id=@jirachecker&text="+ result);
//base.Driver.navigate().back();

//        try {
//            URL url = new URL("https://api.telegram.org/bot"+token+"/sendMessage?chat_id=@jirachecker&text="+ resultContent.toString());
//            connection = (HttpURLConnection) url.openConnection();
//
//            connection.setRequestMethod("POST");
//            connection.setConnectTimeout(5000);
//            connection.setReadTimeout(5000);
//
//            int status = connection.getResponseCode();
//            System.out.println(status + ": " + url);
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            System.out.println("here1");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("here2");
//
//        } catch (Exception e){
//            e.printStackTrace();
//            System.out.println("here3");
//        }
