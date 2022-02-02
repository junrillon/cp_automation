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

import java.util.List;

public class Login extends BaseUtil{

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

    @And("^I search ([^\"]*) in backlog")
    public void searchBacklog(String sprint, DataTable testrailCreds) throws InterruptedException {
        JiraObjects jiraObjects = new JiraObjects(base.Driver);
        TestRailObjects testRailObjects = new TestRailObjects(base.Driver);
        Locator locator = new Locator(base.Driver);

        WebDriverWait wait = new WebDriverWait(base.Driver, 5);
        WebDriverWait longwait = new WebDriverWait(base.Driver, 15);

        String advanceSprint = sprint.replace('_',' ');

        List<List<String>> data = testrailCreds.asLists(String.class);
        String username = data.get(1).get(0);
        String password = data.get(1).get(1);

        String oldTab = base.Driver.getWindowHandle();

        Thread.sleep(3000);
        WebElement sprintHeader = base.Driver.findElement(By.xpath("//div[@class='header-left']/div[contains(text(),'"+ advanceSprint +"')]"));
        ((JavascriptExecutor) base.Driver).executeScript("arguments[0].scrollIntoView(true);", sprintHeader);
        Thread.sleep(500);
        System.out.println("Searched " + advanceSprint + " in backlog. \n");
        System.out.println(advanceSprint + "\n");

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

        for(int i = 1; i <= converted_issueCount; i++){
            WebElement cardStatus = base.Driver.findElement(By.xpath(advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardStatus));
            String extractedCardStatus = cardStatus.getText();

            WebElement cardTitle = base.Driver.findElement(By.xpath(advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardTitleXpath));
            String extractedCardTitle = cardTitle.getText();

            int cardTester_isNull = base.Driver.findElements(By.xpath(advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardTester)).size();
            String extractedCardTester;
            if(cardTester_isNull > 0){
                extractedCardTester = base.Driver.findElement(By.xpath(advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardTester)).getText();
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
                        if(splitStr[1] == "Dacio"){
                            extractedCardTester = "@daysofdash";
                        } else {
                            extractedCardTester = "@threem06";
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

            WebElement cardAssignee = base.Driver.findElement(By.xpath(advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardAssignee));
            String extractedCardAssignee = cardAssignee.getAttribute("alt");

            WebElement cardNumber = base.Driver.findElement(By.xpath(advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]["+i+"]" + perCardNumberXpath));
            String extractedCardNumber = cardNumber.getAttribute("title");
            cardNumber.click();

            Thread.sleep(1000);

            WebElement sprintInsideDetails = base.Driver.findElement(By.xpath("//h2[contains(text(), 'Sprint')]"));
            wait.until(ExpectedConditions.visibilityOf(jiraObjects.cardDetailedView));
            ((JavascriptExecutor) base.Driver).executeScript("arguments[0].scrollIntoView(true);", sprintInsideDetails);
            Thread.sleep(500);

            wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testCases));
            jiraObjects.testCases.click();
            Thread.sleep(2000);

            //Switch to iframe
            base.Driver.switchTo().frame(base.Driver.findElement(By.xpath("//div[@id='ghx-detail-view']//iframe[contains(@id,'com.testrail.jira.testrail-plugin__panel-references')]")));
            longwait.until(ExpectedConditions.elementToBeClickable(base.Driver.findElement(By.xpath("//iframe[@id='tr-frame-panel-references']"))));
            base.Driver.switchTo().frame(base.Driver.findElement(By.xpath("//iframe[@id='tr-frame-panel-references']")));

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

                //wait.until(ExpectedConditions.visibilityOf((WebElement) locator.testCases_status()));
                int testCases = locator.testCases_status().size();
                if (testCases > 0) {
                    testCases_stats = "None";
                } else {
                    testCases_stats = "Already have test cases.";
                }
                //    System.out.println("tc done");

                base.Driver.switchTo().defaultContent();
                wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testCasesBack));
                jiraObjects.testCasesBack.click();
                Thread.sleep(500);

                wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testRuns));
                jiraObjects.testRuns.click();
                Thread.sleep(500);

                //Switch to iframe
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

                System.out.println(i + ". " + "(" + extractedCardNumber + ") " + extractedCardTitle + "\n" +
                        "•Tester: " + extractedCardTester + "  |  •" + extractedCardAssignee + "\n" +
                        "•Status: " + extractedCardStatus + "\n" +
                        "─ Test Cases: " + testCases_stats + "\n" + "─ Test Runs: " + testRuns_stats + "\n");
                Thread.sleep(1000);
            }
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