package pages.jira;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageModelBase;

import java.util.List;

public class JiraObjects{

    private final WebDriver driver;
    private final PageModelBase baseAction;

    /**
     * Object repository
     */
    @FindBy(how = How.ID, using = "username")
    public WebElement usernameField;

    @FindBy(how = How.ID, using = "password")
    public WebElement passwordField;

    @FindBy(how = How.ID, using = "login-submit")
    public WebElement loginButton;

    @FindBy(how = How.XPATH, using = ".//header[@role='banner']")
    public WebElement homeBanner;

    @FindBy(how = How.XPATH, using = ".//div[@data-testid='home-page-content']")
    public WebElement homePage;

    @FindBy(how = How.XPATH, using = ".//span[contains(text(),'Backlog')]")
    public WebElement backlog;

    @FindBy(how = How.XPATH, using = ".//div[contains(@data-testid, 'software-backlog.detail-view.issue-wrapper.backlog-issue')]")
    public WebElement cardDetailedView;

    @FindBy(how = How.XPATH, using = ".//div[@class='mffpf0-2 bWvVtC']") //<-- details
    public WebElement cardDetails;

    @FindBy(how = How.XPATH, using = ".//h2[contains(text(), 'Sprint')]") //<-- details
    public WebElement sprintDisplayInsideCard;

    @FindBy(how = How.XPATH, using = ".//h2[contains(text(), 'Development')]") //<-- details
    public WebElement developmentDisplayInsideCard;
    @FindBy(how = How.XPATH, using = ".//div[contains(text(), 'Open') and contains(., 'TestRail: Cases')]")
    public WebElement testCases;

    @FindBy(how = How.XPATH, using = ".//button/following-sibling::div[contains(text(), 'TestRail: Cases')]")
    public WebElement testCasesBack;

    @FindBy(how = How.XPATH, using = ".//div[contains(text(), 'Open ')]//div[contains(text(), 'TestRail: Runs')]")
    public WebElement testRuns;

    @FindBy(how = How.XPATH, using = ".//button/following-sibling::div[contains(text(), 'TestRail: Runs')]")
    public WebElement testRunsBack;

    @FindBy(how = How.XPATH, using = ".//p[@class='bottom']/a[@class='button']")
    public WebElement loginToTestRailButton;

    @FindBy(how = How.XPATH, using = ".//iframe[contains(@id,'com.testrail.jira.testrail-plugin__panel-references')]")
    public WebElement tcIframe1;

    @FindBy(how = How.ID, using = "tr-frame-panel-references")
    public WebElement tcIframe2;

    @FindBy(how = How.XPATH, using = ".//iframe[contains(@id,'com.testrail.jira.testrail-plugin__panel-runsreferences')]")
    public WebElement trIframe1;

    @FindBy(how = How.ID, using = "tr-frame-panel-runsreferences")
    public WebElement trIframe2;

    //---------------------------------------------------
    @FindBy(how = How.XPATH, using = ".//div[contains(text(), 'Open') and contains(., 'Status Time Free')]")
    public WebElement statusTimeFree;


    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'index.php?/auth/login')]")
    public List<WebElement> TestrailUrl;

    @FindBy(how = How.XPATH, using = ".//section[@id='content']/p")
    public List<WebElement> testCases_status;

    @FindBy(how = How.XPATH, using = ".//section[@id='content']/p")
    public List<WebElement> testRuns_status;

    @FindBy(how = How.XPATH, using = ".//section[@id='content']//p[@class='top']/em")
    public List<WebElement> loginToTestRail;

    @FindBy(how = How.XPATH, using = ".//div[contains(concat(' ',@class,' '), ' ghx-sprint-planned ')][1]")
    public WebElement advanceSprintXpathWE;

    @FindBy(how = How.XPATH, using = ".//div[contains(concat(' ',@class,' '), ' ghx-sprint-planned ')][1]//div[contains(concat(' ',@class,' '), ' ghx-no-issues ')]")
    public WebElement advanceSprintNoIssue;

    @FindBy(how = How.XPATH, using = ".//button[contains(@aria-label, 'Story - Change issue type')]")
    public WebElement changeIssueTypeButton;

    //---------------------------------------------------
    @FindBy(how = How.XPATH, using = ".//a[contains(@data-testid, 'issue.views.issue-base.foundation.breadcrumbs.current-issue.item')]//span")
    public WebElement detailedViewCardNumber;

    @FindBy(how = How.XPATH, using = ".//h1[contains(@data-testid, 'issue.views.issue-base.foundation.summary.heading')]")
    public WebElement detailedViewCardTitle;

    @FindBy(how = How.XPATH, using = ".//button[contains(@data-testid, 'issue.views.issue-base.foundation.change-issue-type.button')]//img")
    public WebElement detailedViewCardIssueType;

    public JiraObjects(WebDriver driver) {
        this.driver = driver;
        this.baseAction = new PageModelBase(this.driver);
        PageFactory.initElements(driver, this);
    }

    public String advanceSprintXpath = "//div[contains(@data-testid, 'software-backlog.card-list.container')][2]";
    public String issueCountXpath = "//div[contains(text(), '(') and contains(normalize-space(.), ' issues')]";
    public String sprintNumberXpath = "//div[contains(text(),'Sprint')]";
    public String perCardStoryPoints = "//span[contains(@data-testid, 'issue-field-number.ui.badge')]/span";
    public String perCardAssignee = "//span[@id[contains(., 'avatar-label')]]"; // <-- get Alt
    public String perCardTester = "//div[contains(@aria-label, 'Tester')]";
    public String perCardStatus = "//div[contains(@aria-label, 'Status')]";

    public String subTaskIcon = "//span[@role='img' and @aria-label='subatskIcon']";
    public String subTaskShowIcon = "//button[contains(@aria-label, 'Show subtasks')]";
    public String subTaskHideIcon = "//button[contains(@aria-label, 'Hide subtasks')]";

    public String subTasks = "/following-sibling::div[position() >= 1 and position() <= 1]/div/div[%d]" +
            "//div[@data-testid='software-backlog.card-list.card.card-contents.card-container']";

    public String getSprintId() {
        WebElement sprintHeader = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(advanceSprintXpath)));

        // Get the sprint ID and remove the prefix "HEADER-DROP-"
        String sprintId = sprintHeader.getAttribute("aria-controls")
                .replace("HEADER-DROP-", "");

        return sprintId;
    }

    public String getSprintNumber() {
        WebElement futureSprintNumber = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOf(driver.findElement
                (By.xpath(advanceSprintXpath + sprintNumberXpath))));

        return futureSprintNumber.getText();
    }

    public String firstCard(){
        //Get the sprint id by calling the getSprint() method
        String sprintId = getSprintId();

        //Return the xpath
        return advanceSprintXpath + "//div[contains(@data-testid, 'software-backlog.card-list.id-"+sprintId+"')]/div/div[1]";
    }

    public String perCard(int position){
        //Get the sprint id by calling the getSprint() method
        String sprintId = getSprintId();

        //Return the xpath
        return advanceSprintXpath + "//div[contains(@data-testid, 'software-backlog.card-list.id-"+sprintId+"')]/div/div["+position+"]";
    }

    public List<WebElement> cardElements(){
        //Get the sprint id by calling the getSprint() method
        String sprintId = getSprintId();

        //Return the xpath
        List<WebElement> cardCount = driver.findElements
                (By.xpath(advanceSprintXpath + "//div[contains(@data-testid, 'software-backlog.card-list.id-"+sprintId+"')]/div/div"));

        return cardCount;
    }

    public String getIssueCount(){
        //Get the text in this element
        String issueCount = driver.findElement
                (By.xpath(advanceSprintXpath + issueCountXpath)).getText();

        //Removes any non-digit characters from the issueCount string
        return issueCount.replaceAll("\\D", "");
    }

    public String getCardStatus(int position){
        String perCard = perCard(position);

        WebElement cardStatus = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(perCard + perCardStatus)));

        String extractedCardStatus = cardStatus.getText();
        System.out.println("Card Status: " + extractedCardStatus);

        return extractedCardStatus;
    }

    public String getCardStoryPoints(int position){
        String perCard = perCard(position);

        WebElement cardStoryPoints = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(perCard + perCardStoryPoints)));

        String extractedCardStoryPoints = cardStoryPoints.getText();
        System.out.println("Card Story Points: " + extractedCardStoryPoints);

        return extractedCardStoryPoints;
    }

//    public List<WebElement> getCardTester(){
//        //Get the sprint id by calling the getSprint() method
//        String sprintId = getSprintId();
//        String perCard =  advanceSprintXpath + "//div[contains(@data-testid, 'software-backlog.card-list.id-"+sprintId+"')]/div/div";
//
//        List<WebElement> cardTester = driver.findElements
//                (By.xpath(perCard + perCardTester));
//
//        return cardTester;
//    }

    public String getCardTester(int position){
        String perCard = perCard(position);
        WebElement cardTester = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(perCard + perCardTester)));

        return cardTester.getText();
    }

    public Boolean isTesterNull(int position){
        String perCard = perCard(position);

        int cardTesterCount = driver.findElements(By.xpath(perCard + perCardTester)).size();

        boolean isTesterNull = (cardTesterCount == 0);
        return isTesterNull;
    }

    public String getCardAssignee(int position){
        String perCard = perCard(position);

        try {
            List<WebElement> assignee = driver.findElements(By.xpath(perCard + perCardAssignee));
            String extractedCardAssignee = assignee.get(0).getAttribute("innerHTML");

            if (!extractedCardAssignee.equals("Unassigned")) {
                extractedCardAssignee = extractedCardAssignee.replace("Assignee: ", "");
                System.out.println("Card Assignee: " + extractedCardAssignee);

                return extractedCardAssignee;
            } else {
                return "Assignee: Unassigned";
            }
        } catch (TimeoutException e){
            System.out.println("Card Assignee: Unassigned");
            return "Assignee: Unassigned";
        }

    }

    public Boolean isAssigneeNull(int position){
        String perCard = perCard(position);

        int cardAssigneeCount = driver.findElements(By.xpath(perCard + perCardAssignee)).size();

        return (cardAssigneeCount == 0);
    }

    public boolean isTestCasesNull(){
        return testCases_status.size() > 0;
    }

    public boolean isTestRunsNull(){
        return testRuns_status.size() > 0;
    }

    public String getTestCasesStatus() {
        if (isTestCasesNull()) {
            return "None";
        } else {
            return "Already have test cases.";
        }
    }
    public String getTestRunsStatus() {
        if (isTestRunsNull()) {
            return "None";
        } else {
            return "Already have test runs.";
        }
    }

    public String getCardNumber(){
        WebElement cardNumber = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOf(detailedViewCardNumber));

        return cardNumber.getText();
    }

    public String getCardTitle(){
        WebElement cardTitle = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOf(detailedViewCardTitle));

        return cardTitle.getText();
    }

    public String getCardIssueType(){
        WebElement cardTitle = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOf(detailedViewCardIssueType));

        String extractedCardIssueType = cardTitle.getAttribute("alt");
        System.out.println("Card Issue Type: " + extractedCardIssueType);

        return extractedCardIssueType;
    }

    public String getFormattedTester(String extractedCardTester) {
        String[] splitStr = extractedCardTester.split("\\s+");

        if (splitStr.length > 0) {
            switch (splitStr[0]) {
                case "Jun":
                    return "@Juuuun";
                case "Christian":
                    return "@mxxx67";
                case "Jean":
                    return "@jeanpaola";
                case "Marjorie":
                    return "@Marj0819";
                case "Robert":
                    return "@robertcaneta";
                case "Sherylle":
                    return "@Sheeey";
                case "Bianca":
                    return "@yangcavelez";
                case "Arvin":
                    return "@daysofdash";
                case "sysadmin":
                    return "sysadmin";
            }
        }

        return "None";
    }

    public String buildResultContent(String extractedCardNumber, String extractedCardTitle,
                                     String extractedCardTester, String extractedCardAssignee,
                                     String extractedCardStatus, String extractedCardSP,
                                     String subTasksCount, String testCasesStatus, String testRunsStatus) {
        //⚠ \u2139\ufe0f
        return "\u2139\ufe0f (" + extractedCardNumber + ") " + extractedCardTitle + "\n" +
                "•Tester: " + extractedCardTester + "  |  •" + extractedCardAssignee + "\n" +
                "•Status: " + extractedCardStatus + " | •Story Points: " + extractedCardSP + "\n" +
                "•Subtask: "  + subTasksCount + "\n" +
                "- Test Cases: " + testCasesStatus + "\n" + "- Test Runs: " + testRunsStatus + "\n\n";
    }

    public Boolean hasSubTasks(int position){
        String perCard = perCard(position);
        int subTaskIconCount = driver.findElements(By.xpath(perCard + subTaskIcon)).size();

        return subTaskIconCount > 0;
    }

    public void showSubtasks(int position){
        String perCard = perCard(position);

        WebElement showSubtaskIcon = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(perCard + subTaskShowIcon)));

        showSubtaskIcon.click();
    }

    public void hideSubtasks(int position){
        String perCard = perCard(position);

        WebElement hideSubtaskIcon = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(perCard + subTaskHideIcon)));

        hideSubtaskIcon.click();
    }

    public List<WebElement> subTaskCards(int position){
        String perCard = perCard(position);

        List<WebElement> subtaskCards = driver.findElements
                (By.xpath(perCard + "/following-sibling::div[position() >= 1 and position() <= 1]" +
                        "//div[@data-testid='software-backlog.card-list.card.card-contents.card-container']"));

        return subtaskCards;
    }


    public String perSubTask(int position, int subTaskIndex){
        String perCard = perCard(position);
        String subTaskCardPosition = String.format(subTasks, subTaskIndex);

        return perCard + subTaskCardPosition;
    }

    public String getSubTaskStatus(int position, int subTaskIndex){
        String perCard = perCard(position);
        String subTaskCardPosition = String.format(subTasks, subTaskIndex);

        WebElement cardStatus = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(perCard + subTaskCardPosition + perCardStatus)));

        return cardStatus.getText();
    }


    public String getSubTaskTester(int position, int subTaskIndex){
        String perCard = perCard(position);
        String subTaskCardPosition = String.format(subTasks, subTaskIndex);

        WebElement cardTester = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(perCard + subTaskCardPosition + perCardTester)));

        return cardTester.getText();
    }

    public String getSubTaskStoryPoints(int position, int subTaskIndex){
        String perCard = perCard(position);
        String subTaskCardPosition = String.format(subTasks, subTaskIndex);

        WebElement cardStoryPoints = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(perCard + subTaskCardPosition + perCardStoryPoints)));

        return cardStoryPoints.getText();
    }

    public String getSubTaskAssignee(int position, int subTaskIndex){
        String perCard = perCard(position);
        String subTaskCardPosition = String.format(subTasks, subTaskIndex);

        try {
            List<WebElement> assignee = driver.findElements(By.xpath(perCard + subTaskCardPosition + perCardAssignee));
            String extractedCardAssignee = assignee.get(0).getAttribute("innerHTML");
            System.out.println("Card Assignee: " + extractedCardAssignee);

            if (!extractedCardAssignee.equals("Unassigned")) {
                extractedCardAssignee = extractedCardAssignee.replace("Assignee: ", "");

                return extractedCardAssignee;
            } else {
                return "Assignee: Unassigned";
            }
        } catch (TimeoutException e){
            System.out.println("Card Assignee: Unassigned");
            return "Assignee: Unassigned";
        }
    }

    public void getSubTasksCount(int position){
        boolean hasSubTasks = hasSubTasks(position);

        if(hasSubTasks){
            showSubtasks(position);

            int subTasksCount = subTaskCards(position).size();
            System.out.println("subTasksCount: " + subTasksCount);

            hideSubtasks(position);
        }
    }
}
