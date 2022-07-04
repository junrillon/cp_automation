package steps.jira;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageModelBase;
import pages.jira.JiraObjects;
import pages.testrail.TestRailObjects;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JiraCardChecking {

    private final WebDriver driver;
    public PageModelBase page;

    public JiraCardChecking (PageModelBase page, Driver driver){
        this.driver = driver.get();
        this.page = page;
    }

    StringBuffer resultContent = new StringBuffer();
    String advanceSprint = null;

    @Given("I access jira website {string}")
    public void iAccessJiraWebsiteHttpsIdAtlassianComLogin(String jiraUrl) {
        //Open Chrome with URL
        driver.navigate().to(jiraUrl);
        System.out.println("I accessed jira website.");
    }

    @When("I login on Jira")
    public void iLoginInJira(DataTable credentials) {
        JiraObjects jiraObjects = new JiraObjects(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        List<List<String>> data = credentials.asLists(String.class);
        String username = data.get(1).get(0);
        String password = data.get(1).get(1);

        wait.until(ExpectedConditions.visibilityOf(jiraObjects.usernameField));
        jiraObjects.usernameField.sendKeys(username);

        wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.loginButton));
        jiraObjects.loginButton.click();

        wait.until(ExpectedConditions.visibilityOf(jiraObjects.passwordField));
        jiraObjects.passwordField.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.loginButton));
        jiraObjects.loginButton.click();

        System.out.println("I logged-in jira website.");

    }

    @When("I select project {string}")
    public void iSelectProjectBC(String projectName) {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //locate for p element that contains {projectName}
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//p[@class='name' and contains(text(),'"+ projectName +"')]")));
        driver.findElement(By.xpath(".//p[@class='name' and contains(text(),'"+ projectName +"')]")).click();
        System.out.println("I select project: "+projectName);

    }

    @When("I access backlog")
    public void iAccessBacklog() {
        JiraObjects jiraObjects = new JiraObjects(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        wait.until(ExpectedConditions.visibilityOf(jiraObjects.backlog));
        wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.backlog));
        jiraObjects.backlog.click();

        System.out.println("I clicked backlog.");


    }

    @When("I scroll to future sprint")
    public void iScrollToFutureSprint() {
        JiraObjects jiraObjects = new JiraObjects(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        JavascriptExecutor js = (JavascriptExecutor)driver;

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(jiraObjects.advanceSprintXpath)));
        WebElement sprintHeader = driver.findElement(By.xpath(jiraObjects.advanceSprintXpath));
        js.executeScript("arguments[0].scrollIntoView(true);", sprintHeader);

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(jiraObjects.sprintNumberXpath))));
        advanceSprint = driver.findElement(By.xpath(jiraObjects.sprintNumberXpath)).getText();
        System.out.println("I searched " + advanceSprint + " in backlog. \n");

    }

    @When("I check if already logged in on testrail")
    public void iCheckIfAlreadyLoggedInOnTestrail(DataTable testrailCreds) {
        JiraObjects jiraObjects = new JiraObjects(driver);
        TestRailObjects testRailObjects = new TestRailObjects(driver);
        JavascriptExecutor js = (JavascriptExecutor)driver;

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebDriverWait longwait = new WebDriverWait(driver, 60);

        List<List<String>> data = testrailCreds.asLists(String.class);
        String username = data.get(1).get(0);
        String password = data.get(1).get(1);

        String oldTab = driver.getWindowHandle();

        //check cards inside sprint and then click
        wait.until(ExpectedConditions.visibilityOf(jiraObjects.advanceSprintXpathWE));
        js.executeScript("arguments[0].scrollIntoView(true);", jiraObjects.advanceSprintXpathWE);
        System.out.println("Scrolled into per card");

        WebElement cardNumber = driver.findElement(By.xpath(jiraObjects.perCardNumberXpath + "[1]"));
        longwait.until(ExpectedConditions.elementToBeClickable(cardNumber));
        cardNumber.click();

        //check sprint element inside detailed view then scroll to it
        wait.until(ExpectedConditions.visibilityOf(jiraObjects.cardDetailedView));
        wait.until(ExpectedConditions.visibilityOf(jiraObjects.sprintDisplayInsideCard));
        js.executeScript("arguments[0].scrollIntoView(true);", jiraObjects.sprintDisplayInsideCard);

        //check testcases element inside detailed view then click
        wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testCases));
        jiraObjects.testCases.click();

        //Switch to iframes (testcases iframe 1 and 2)
        driver.switchTo().frame(jiraObjects.tcIframe1);
        longwait.until(ExpectedConditions.elementToBeClickable(jiraObjects.tcIframe2));
        driver.switchTo().frame(jiraObjects.tcIframe2);

        //check if already logged in to testrail
        int loginToTestrail = jiraObjects.loginToTestRail.size();
        System.out.println(loginToTestrail);
        if(loginToTestrail > 0){
            System.out.println("Not yet logged in on testrail.");
            wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.loginToTestRailButton));
            jiraObjects.loginToTestRailButton.click();

            for(String winHandle : driver.getWindowHandles()){
                driver.switchTo().window(winHandle);
            }

            wait.until(ExpectedConditions.visibilityOf(testRailObjects.username));
            testRailObjects.username.sendKeys(username);

            wait.until(ExpectedConditions.visibilityOf(testRailObjects.password));
            testRailObjects.password.sendKeys(password);

            wait.until(ExpectedConditions.elementToBeClickable(testRailObjects.loginButton));
            testRailObjects.loginButton.click();

            boolean testrailHeader_isPresent = testRailObjects.testrailHeader.isDisplayed();
            if(testrailHeader_isPresent){
                System.out.println("Successfully logged in on testrail");
                driver.close();

                driver.switchTo().window(oldTab);
                driver.navigate().refresh();
            }

            iCheckIfAlreadyLoggedInOnTestrail(testrailCreds);
            //navigateToFutureSprint();

        } else {
            System.out.println("Already logged in on testrail.");

            //Switch back to default frame
            driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testCasesBack));
            jiraObjects.testCasesBack.click();
        }

    }

    @When("I check cards in future sprint")
    public void iCheckCardsInFutureSprint() {
        JiraObjects jiraObjects = new JiraObjects(driver);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebDriverWait longwait = new WebDriverWait(driver, 30);
        JavascriptExecutor js = (JavascriptExecutor)driver;

        String perCardTitleXpath = jiraObjects.perCardTitleXpath;
        String perCardNumberXpath = jiraObjects.perCardNumberXpath;
        String perCardAssignee = jiraObjects.perCardAssigneeXpath;
        String perCardTester = jiraObjects.perCardTesterXpath;
        String perCardStatus = jiraObjects.perCardStatusXpath;
        String perCardXpath = jiraObjects.perCardXpath;

        String testCases_stats;
        String testRuns_stats;

        String result;
        String issueCount = driver.findElement(By.xpath(jiraObjects.issueCountXpath)).getText();
        String count = issueCount.replace(" issues","");
        int converted_issueCount = Integer.parseInt(count);

        System.out.println("Checking every card now.");
        if(converted_issueCount != 0){
            for (int i = 1; i <= converted_issueCount; i++) {
                String perCard = perCardXpath + "[" + i + "]";
                String perCardIssueType = perCard + jiraObjects.perCardIssueType;

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(perCardIssueType)));
                String issueType = driver.findElement(By.xpath(perCardIssueType)).getAttribute("title");
                if (!issueType.equalsIgnoreCase("bug")) {

                    WebElement issueContent = driver.findElement(By.xpath(perCard));
                    js.executeScript("arguments[0].scrollIntoView(true);", issueContent);

                    //locate card status element and get text
                    System.out.println("Checking card status");
                    WebElement cardStatus = driver.findElement(By.xpath(perCard + perCardStatus));
                    wait.until(ExpectedConditions.visibilityOf(cardStatus));
                    String extractedCardStatus = cardStatus.getText();

                    //locate card title element and get text
                    System.out.println("Checking card title");
                    WebElement cardTitle = driver.findElement(By.xpath(perCard + perCardTitleXpath));
                    wait.until(ExpectedConditions.visibilityOf(cardTitle));
                    String extractedCardTitle = cardTitle.getText();

                    //check if card has tester
                    System.out.println("Checking card tester");
                    int cardTester_isNull = driver.findElements(By.xpath(perCard + perCardTester)).size();
                    String extractedCardTester;

                    if (cardTester_isNull > 0) {
                        //locate card title element and get text
                        extractedCardTester = driver.findElement(By.xpath(perCard + perCardTester)).getText();
                        //String testerName = extractedCardTester;
                        String[] splitStr = extractedCardTester.split("\\s+");

                        // Switch statement over above string
                        switch (splitStr[0]) {
                            case "Jun":
                                extractedCardTester = "Juuuun";
                                break;
                            case "Christian":
                                extractedCardTester = "mxxx67";
                                break;
                            case "Jean":
                                extractedCardTester = "jeanpaola";
                                break;
                            case "jerald":
                                extractedCardTester = "jeraldmm";
                                break;
                            case "Marjorie":
                                extractedCardTester = "Marj0819";
                                break;
                            case "Robert":
                                extractedCardTester = "robertcaneta";
                                break;
                            case "Sherylle":
                                extractedCardTester = "Sheeey";
                                break;
                            case "Bianca":
                                extractedCardTester = "yangcavelez ";
                                break;
                            case "Arvin":
                                switch (splitStr[1]) {
                                    case "Dacio":
                                        extractedCardTester = "daysofdash";
                                        break;
                                    case "Oliva":
                                        extractedCardTester = "threem06";
                                        break;
                                    default:
                                }
                                break;
                            case "sysadmin":
                                extractedCardTester = "sysadmin";
                                break;
                            default:
                                extractedCardTester = "None";
                        }
                    } else {
                        extractedCardTester = "None";
                    }
                    System.out.println(extractedCardTester);

                    //check if card has assignee/dev
                    int cardAssignee_isNull = driver.findElements(By.xpath(perCard + perCardAssignee)).size();
                    String extractedCardAssignee;
                    if (cardAssignee_isNull > 0) {
                        //locate card assignee element and get text
                        extractedCardAssignee = driver.findElement(By.xpath(perCard + perCardAssignee)).getAttribute("alt");
                    } else {
                        extractedCardAssignee = "Assignee: None";
                    }

                    //check cards inside sprint and then click
                    WebElement cardNumber = driver.findElement(By.xpath(perCard + perCardNumberXpath));
                    wait.until(ExpectedConditions.visibilityOf(cardNumber));
                    String extractedCardNumber = cardNumber.getAttribute("title");

                    //Click card
                    wait.until(ExpectedConditions.elementToBeClickable(issueContent));
                    js.executeScript("arguments[0].click()", issueContent);

                    //check sprint element inside detailed view then scroll to it
                    WebElement sprint = jiraObjects.sprintDisplayInsideCard;
                    wait.until(ExpectedConditions.visibilityOf(jiraObjects.cardDetailedView));
                    wait.until(ExpectedConditions.visibilityOf(sprint));
                    js.executeScript("arguments[0].scrollIntoView(true);", sprint);

                    //check testcases element inside detailed view then click
                    wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testCases));
                    jiraObjects.testCases.click();

                    //Switch to iframes (testcases iframe 1 and 2)
                    driver.switchTo().frame(jiraObjects.tcIframe1);
                    longwait.until(ExpectedConditions.elementToBeClickable(jiraObjects.tcIframe2));
                    driver.switchTo().frame(jiraObjects.tcIframe2);

                    int testCases = jiraObjects.testCases_status.size();
                    if (testCases > 0) {
                        testCases_stats = "None";
                    } else {
                        testCases_stats = "Already have test cases.";
                    }

                    //Switch back to default frame
                    driver.switchTo().defaultContent();
                    wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testCasesBack));
                    jiraObjects.testCasesBack.click();

                    wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testRuns));
                    jiraObjects.testRuns.click();

                    //Switch to iframes
                    driver.switchTo().frame(jiraObjects.trIframe1);
                    longwait.until(ExpectedConditions.elementToBeClickable(jiraObjects.trIframe2));
                    driver.switchTo().frame(jiraObjects.trIframe2);

                    //wait.until(ExpectedConditions.elementToBeClickable((WebElement) locator.testRuns_status()));
                    int testRuns = jiraObjects.testRuns_status.size();
                    if (testRuns > 0) {
                        testRuns_stats = "None";
                    } else {
                        testRuns_stats = "Already have test runs.";
                    }

                    driver.switchTo().defaultContent();
                    wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testRunsBack));
                    jiraObjects.testRunsBack.click();
                    //⚠
                    result = ("(" + extractedCardNumber + ") " + extractedCardTitle + "\n" +
                            "•Tester: @" + extractedCardTester + "  |  •" + extractedCardAssignee + "\n" +
                            "•Status: " + extractedCardStatus + "\n" +
                            "- Test Cases: " + testCases_stats + "\n" + "- Test Runs: " + testRuns_stats + "\n\n");

                    boolean isAppended;
                    if (testCases > 0 || testRuns > 0) {
                        isAppended = true;
                        resultContent.append(result);
                    } else {
                        isAppended = false;
                    }

                    System.out.println("isAppended: " + isAppended + "\n" +
                            i + ". (" + extractedCardNumber + ") " + extractedCardTitle + "\n" +
                            "•Tester: @" + extractedCardTester + "  |  •" + extractedCardAssignee + "\n" +
                            "•Status: " + extractedCardStatus + "\n" +
                            "─ Test Cases: " + testCases_stats + "\n" + "─ Test Runs: " + testRuns_stats + "\n");

                } else {
                    System.out.println("Bug Card");
                }
            }
        } else {
            System.out.println("No card/s available in this sprint.");
            resultContent.append("No card/s available in this sprint.");
        }
    }

    @Then("I send results in telegram")
    public void iSendResultsInTelegram(DataTable telegramCreds) {
        List<List<String>> data = telegramCreds.asLists(String.class);
        String token = data.get(1).get(0);
        String chatId = data.get(1).get(1);

        String resultContentString = resultContent.toString();
        int resultContentSize = resultContentString.length();
        if (resultContentSize == 0){
            resultContent.append("All cards have tc/tr. Thank you for your cooperation! \uD83D\uDE4F\uD83C\uDFFC");
            resultContentString = resultContent.toString();
        }

        try {
            URL url = new URL("https://api.telegram.org/bot"+token+"/sendMessage?chat_id="+chatId+"&text="+advanceSprint+"%0A"+ URLEncoder.encode(resultContentString, StandardCharsets.UTF_8));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            String errorMessage = connection.getResponseMessage();
            System.out.println(status + ": " + errorMessage + "\n" + url);

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}


