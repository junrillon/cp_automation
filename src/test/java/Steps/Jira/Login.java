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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Login extends BaseUtil{

//    public static void main(String[] args){
//        try {
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot(new JiraBot());
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//
//    }

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
        WebDriverWait wait = new WebDriverWait(base.Driver, 10);

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
        wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.backlog));
        jiraObjects.backlog.click();
        System.out.println("Clicked backlog.");
    }

    StringBuffer resultContent = new StringBuffer();
    String advanceSprint = null;

    @And("^Navigate to future sprint in backlog")
    public void navigateToFutureSprint() throws InterruptedException {
        JiraObjects jiraObjects = new JiraObjects(base.Driver);
        WebDriverWait wait = new WebDriverWait(base.Driver, 5);

        Thread.sleep(2000);
        WebElement sprintHeader = base.Driver.findElement(By.xpath(jiraObjects.advanceSprintXpath));
        ((JavascriptExecutor) base.Driver).executeScript("arguments[0].scrollIntoView(true);", sprintHeader);

        wait.until(ExpectedConditions.visibilityOf(base.Driver.findElement(By.xpath(jiraObjects.advanceSprintXpath + jiraObjects.sprintNumber))));
        advanceSprint = base.Driver.findElement(By.xpath(jiraObjects.advanceSprintXpath + jiraObjects.sprintNumber)).getText();
        System.out.println("Searched " + advanceSprint + " in backlog. \n");
    }

    @And("^Check cards in future sprint")
    public void checkCardsInFutureSprint() throws InterruptedException {
        JiraObjects jiraObjects = new JiraObjects(base.Driver);
        Locator locator = new Locator(base.Driver);

        WebDriverWait wait = new WebDriverWait(base.Driver, 5);
        WebDriverWait longwait = new WebDriverWait(base.Driver, 15);


        String advanceSprint = jiraObjects.advanceSprintXpath;
        String perCardTitleXpath = jiraObjects.perCardTitleXpath;
        String perCardNumberXpath = jiraObjects.perCardNumberXpath;
        String perCardAssignee = jiraObjects.perCardAssignee;
        String perCardTester = jiraObjects.perCardTester;
        String perCardStatus = jiraObjects.perCardStatus;
        String testCases_stats;
        String testRuns_stats;

        String result;

        String issueCount = base.Driver.findElement(By.xpath(advanceSprint + jiraObjects.issueCount)).getText();
        String count = issueCount.replace(" issues","");
        int converted_issueCount = Integer.parseInt(count);

        for(int i = 1; i <= converted_issueCount; i++){
            //locate card status element and get text
            WebElement cardStatus = base.Driver.findElement(By.xpath(advanceSprint + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardStatus));
            String extractedCardStatus = cardStatus.getText();

            //locate card title element and get text
            WebElement cardTitle = base.Driver.findElement(By.xpath(advanceSprint + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardTitleXpath));
            String extractedCardTitle = cardTitle.getText();

            //check if card has tester
            int cardTester_isNull = base.Driver.findElements(By.xpath(advanceSprint + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardTester)).size();
            String extractedCardTester;

            if(cardTester_isNull > 0){
                //locate card title element and get text
                extractedCardTester = base.Driver.findElement(By.xpath(advanceSprint + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardTester)).getText();
                //String testerName = extractedCardTester;
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
            int cardAssignee_isNull = base.Driver.findElements(By.xpath(advanceSprint + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardAssignee)).size();
            String extractedCardAssignee;
            if(cardAssignee_isNull > 0){
                //locate card assignee element and get text
                extractedCardAssignee = base.Driver.findElement(By.xpath(advanceSprint + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardAssignee)).getAttribute("alt");
            } else {
                extractedCardAssignee = "Assignee: None";
            }

            //check cards inside sprint and then click
            WebElement cardNumber = base.Driver.findElement(By.xpath(advanceSprint + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardNumberXpath));
            String extractedCardNumber = cardNumber.getAttribute("title");
            wait.until(ExpectedConditions.elementToBeClickable(cardNumber));
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
            Thread.sleep(1500);

            //Switch to iframes
            base.Driver.switchTo().frame(base.Driver.findElement(By.xpath("//div[@id='ghx-detail-view']//iframe[contains(@id,'com.testrail.jira.testrail-plugin__panel-references')]")));
            longwait.until(ExpectedConditions.elementToBeClickable(base.Driver.findElement(By.xpath("//iframe[@id='tr-frame-panel-references']"))));
            base.Driver.switchTo().frame(base.Driver.findElement(By.xpath("//iframe[@id='tr-frame-panel-references']")));

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
            if (testRuns > 0) {
                testRuns_stats = "None";
            } else {
                testRuns_stats = "Already have test runs.";
            }

            base.Driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testRunsBack));
            jiraObjects.testRunsBack.click();
            Thread.sleep(500);

            result = ("⚠ (" + extractedCardNumber + ") " + extractedCardTitle + "%0A" +
                    "•Tester: " + extractedCardTester + "  |  •" + extractedCardAssignee + "%0A" +
                    "•Status: " + extractedCardStatus + "%0A" +
                    "─ Test Cases: " + testCases_stats + "%0A" + "─ Test Runs: " + testRuns_stats + "%0A%0A");

            boolean isAppended;
            if(testCases > 0 || testRuns > 0){
                isAppended = true;
                resultContent.append(result);
            } else {
                isAppended = false;
            }

            System.out.println("isAppended: " + isAppended + "\n" +
                    i + ". (" + extractedCardNumber + ") " + extractedCardTitle + "\n" +
                    "•Tester: " + extractedCardTester + "  |  •" + extractedCardAssignee + "\n" +
                    "•Status: " + extractedCardStatus + "\n" +
                    "─ Test Cases: " + testCases_stats + "\n" + "─ Test Runs: " + testRuns_stats + "\n");

            Thread.sleep(1000);
        }

    }

    @And("^I send results in telegram")
    public void sendResultsInTelegram(DataTable telegramCreds){
        List<List<String>> data = telegramCreds.asLists(String.class);
        String token = data.get(1).get(0);
        String chatId = data.get(1).get(1);

        try {
            URL url = new URL("https://api.telegram.org/bot"+token+"/sendMessage?chat_id="+chatId+"&text="+advanceSprint+"%0A"+resultContent.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println(status + ": " + url);

//            if(status != 200){
//                sendResultsInTelegram(telegramCreds);
//            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @And("^Check if logged in to testrail")
    public void checkIfLoggedIntoTestrail(DataTable testrailCreds) throws InterruptedException {
        JiraObjects jiraObjects = new JiraObjects(base.Driver);
        TestRailObjects testRailObjects = new TestRailObjects(base.Driver);
        Locator locator = new Locator(base.Driver);

        WebDriverWait wait = new WebDriverWait(base.Driver, 5);
        WebDriverWait longwait = new WebDriverWait(base.Driver, 15);

        List<List<String>> data = testrailCreds.asLists(String.class);
        String username = data.get(1).get(0);
        String password = data.get(1).get(1);

        String oldTab = base.Driver.getWindowHandle();

        String advanceSprintXpath = "//div[@class='ghx-backlog-container ghx-sprint-planned js-sprint-container ghx-open ui-droppable' and div[@class='ghx-backlog-header js-sprint-header' and div[@class='header-left' and div[@class='ghx-name' and contains(text(), '"+ advanceSprint +"')]]]]";
        String perCardNumberXpath = jiraObjects.perCardNumberXpath;

        //check cards inside sprint and then click
        WebElement cardNumber = base.Driver.findElement(By.xpath(advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')][1]" + perCardNumberXpath));
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
        System.out.println("Logged in to testrail: "+loginToTestrail);
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

            checkIfLoggedIntoTestrail(testrailCreds);

        } else {
            System.out.println("Already logged in to testrail.");

            //Switch back to default frame
            base.Driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testCasesBack));
            jiraObjects.testCasesBack.click();
            Thread.sleep(500);

            checkCardsInFutureSprint();

        }

    }

}