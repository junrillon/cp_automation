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
import pages.testrail.TestRailPage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JiraCardChecking {

    private final WebDriver driver;
    private final PageModelBase baseAction;
    private final JiraObjects jiraObjects;

    public JiraCardChecking (Driver driver){
        this.driver = driver.get();
        this.jiraObjects = new JiraObjects(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    ArrayList<ArrayList<StringBuilder>> resultContainer = new ArrayList<ArrayList<StringBuilder>>(); // Initialize the outer array
    StringBuilder resultContent;
    int maxInnerArraySize = 5;
    String futureSprint;

    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 5000;


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
        //p[@class='name' and contains(text(),'"+ projectName +"')]
        WebElement project = wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath(".//a[@href='https://clickplaycorp.atlassian.net/browse/"+projectName+"']")));
        baseAction.clickButton(project);
        System.out.println("I select project: "+projectName);

    }

    @When("I access backlog")
    public void iAccessBacklog() {
        JiraObjects jiraObjects = new JiraObjects(driver);

        baseAction.clickButton(jiraObjects.backlog);
        System.out.println("I clicked backlog.");
    }

    @When("I scroll to future sprint")
    public void iScrollToFutureSprint() {
        JiraObjects jiraObjects = new JiraObjects(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(jiraObjects.advanceSprintXpath)));
        WebElement sprintHeader = driver.findElement(By.xpath(jiraObjects.advanceSprintXpath));
        baseAction.scrollIntoView(sprintHeader);

        //Get the sprint number
        futureSprint = jiraObjects.getSprintNumber();
        System.out.println("I scroll to Sprint " + futureSprint);
    }

    @When("I check if already logged in on testrail")
    public void iCheckIfAlreadyLoggedInOnTestrail(DataTable testrailCreds) throws InterruptedException {
        JiraObjects jiraObjects = new JiraObjects(driver);
        TestRailPage testRailPage = new TestRailPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebDriverWait longwait = new WebDriverWait(driver, 60);

        List<List<String>> data = testrailCreds.asLists(String.class);
        String username = data.get(1).get(0);
        String password = data.get(1).get(1);

        String oldTab = driver.getWindowHandle();

        //Wait for the future sprint to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(jiraObjects.advanceSprintXpath)));

        //Initialization of the WebElement + wait for the element
        WebElement firstCard = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath(jiraObjects.firstCard())));

        //Scroll into the element then click
        baseAction.scrollIntoView(firstCard);
        baseAction.clickButton(firstCard);

        //Wait for the card detailed view to be visible
        wait.until(ExpectedConditions.visibilityOf(jiraObjects.cardDetailedView));

        //check testcases element inside detailed view then click
        wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testCases));
        baseAction.scrollIntoView(jiraObjects.storyPointsDisplayInsideCard);

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        baseAction.clickButton(jiraObjects.testCases);

        //Switch to iframes (testcases iframe 1 and 2)
        baseAction.switchToFrame(jiraObjects.tcIframe1);
        baseAction.switchToFrame(jiraObjects.tcIframe2);

        //check if already logged in to testrail
        int loginToTestrail = jiraObjects.loginToTestRail.size();
        if(loginToTestrail > 0){
            System.out.println("Not yet logged in on testrail.");
            wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.loginToTestRailButton));

            jiraObjects.loginToTestRailButton.click();

            for(String winHandle : driver.getWindowHandles()){
                driver.switchTo().window(winHandle);
            }

            wait.until(ExpectedConditions.visibilityOf(testRailPage.username));
            testRailPage.username.sendKeys(username);

            wait.until(ExpectedConditions.visibilityOf(testRailPage.password));
            testRailPage.password.sendKeys(password);

            wait.until(ExpectedConditions.elementToBeClickable(testRailPage.loginButton));
            testRailPage.loginButton.click();

            boolean testrailHeader_isPresent = testRailPage.testrailHeader.isDisplayed();
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
            baseAction.clickButton(jiraObjects.testCasesBack);
        }

    }

    @When("I check cards in future sprint")
    public void iCheckCardsInFutureSprint() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        String testCasesStatus;
        String testRunsStatus;

        // Create an array to store the extracted text and iteration count
        List<String> extractedCardNumbers = new ArrayList<>();

        // Find the elements and get their count
        List<WebElement> mainCardElements = jiraObjects.cardElements();
        int convertedIssueCount = mainCardElements.size();
        System.out.println("converted_issueCount: " + convertedIssueCount);

        System.out.println("Checking each card...");
        if(convertedIssueCount > 0){
            for (int i = 0; i < convertedIssueCount; i++) {
                //WebElement init for Cards, scrollIntoView, then click
                WebElement cards = wait.until(ExpectedConditions.visibilityOf(mainCardElements.get(i)));
                baseAction.scrollIntoView1(cards);

                int subTasksCount = 0;
                int cardIndex = i + 1;

                //WebElement init for Card, then click
                WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(jiraObjects.perCard(cardIndex))));
                baseAction.clickButton(card);

                //Wait for the card detailed view to be visible
                wait.until(ExpectedConditions.visibilityOf(jiraObjects.cardDetailedView));

                //Get the card number
                String extractedCardNumber = jiraObjects.getCardNumber();

                // Check if the text already exists in the array
                boolean cardNumberExists = extractedCardNumbers.contains(extractedCardNumber);

                //Get card issue type
                String issueType = jiraObjects.getCardIssueType();

                if (!issueType.equalsIgnoreCase("bug") && !cardNumberExists) {

                    //Get card status
                    String extractedCardStatus = jiraObjects.getCardStatus(cardIndex);

                    //Get card title
                    String extractedCardTitle = jiraObjects.getCardTitle();

                    //Get the story points
                    String extractedCardSP = jiraObjects.getCardStoryPoints(cardIndex);

                    //Get the card tester
                    String extractedCardTester = jiraObjects.getCardTester(cardIndex);

                    //format the extracted tester for preparation for telegram message
                    extractedCardTester = jiraObjects.getFormattedTester(extractedCardTester);

                    //Get the card assignee
                    card.click();
                    String extractedCardAssignee = jiraObjects.getCardAssignee(cardIndex);

                    //Scroll into TestRail: Cases in detailed card view
                    wait.until(ExpectedConditions.elementToBeClickable(jiraObjects.testCases));
                    System.out.println("Scroll in Development H2");
                    baseAction.scrollIntoView(jiraObjects.storyPointsDisplayInsideCard);

                    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                    baseAction.clickButton(jiraObjects.testCases);

                    //Switch to iframes (testcases iframe 1 and 2)
                    baseAction.switchToFrame(jiraObjects.tcIframe1);
                    baseAction.switchToFrame(jiraObjects.tcIframe2);

                    //Check if the card has test cases
                    testCasesStatus = jiraObjects.getTestCasesStatus();

                    //Switch back to default frame
                    driver.switchTo().defaultContent();
                    baseAction.clickButton(jiraObjects.testCasesBack);
                    baseAction.clickButton(jiraObjects.testRuns);

                    //Switch to iframes
                    baseAction.switchToFrame(jiraObjects.trIframe1);
                    baseAction.switchToFrame(jiraObjects.trIframe2);

                    //Check if the card has test cases
                    testRunsStatus = jiraObjects.getTestRunsStatus();

                    driver.switchTo().defaultContent();
                    baseAction.clickButton(jiraObjects.testRunsBack);

                    //Extracted card details are assigned to resultContent variable
                    resultContent = new StringBuilder((jiraObjects.buildResultContent(extractedCardNumber, extractedCardTitle,
                            extractedCardTester, extractedCardAssignee, extractedCardStatus, extractedCardSP, String.valueOf(subTasksCount), testCasesStatus, testRunsStatus)));

                    //Call the processResultContainer() method, to check if the result is need to add in the telegram message
                    processResultContainer(testCasesStatus, testRunsStatus, extractedCardSP, resultContent);

                    //Display the result in the log
                    System.out.println(cardIndex + ". (" + extractedCardNumber + ") " + extractedCardTitle + "\n" +
                            "•Tester: " + extractedCardTester + "  |  •" + extractedCardAssignee + "\n" +
                            "•Status: " + extractedCardStatus + " | •Story Points: " + extractedCardSP + "\n" +
                            "•Subtask: "  + subTasksCount + "\n" +
                            "─ Test Cases: " + testCasesStatus + "\n" + "─ Test Runs: " + testRunsStatus + "\n");

                    //Format the extracted card numbers and add it to the extractedCardNumbers array
                    String flag = String.format("Card Number: %s, Count: %d", extractedCardNumber, i + 1);
                    extractedCardNumbers.add(flag);

                    //Call processSubTasks() method
                    processSubTasks(cardIndex);

                    // Refind the card elements
                    //retryFindMainCardElements(wait, mainCardElements, 5, i);
                    int retryCount = 0;
                    while (retryCount < 3) {
                        try {
                            // Re-find the cards element to refresh the reference
                            mainCardElements = jiraObjects.cardElements();
                            wait.until(ExpectedConditions.visibilityOf(mainCardElements.get(i)));

                            // Break the loop if element is found without stale exception
                            break;
                        } catch (StaleElementReferenceException e) {
                            // Wait for a short duration before retrying
                            Thread.sleep(1000);

                            // Re-find the cards element to refresh the reference
                            mainCardElements = jiraObjects.cardElements();
                            wait.until(ExpectedConditions.visibilityOf(mainCardElements.get(i)));

                            retryCount++;
                        }
                    }
                } else {
                    // Scroll to the next element if it exists
                    if (i < convertedIssueCount - 1) {
                        WebElement nextElement = mainCardElements.get(i + 1);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", nextElement);
                    }
                }
            }
        }
    }

    @Then("I send results in telegram")
    public void iSendResultsInTelegram(DataTable telegramCreds) {
        List<List<String>> data = telegramCreds.asLists(String.class);
        String token = data.get(1).get(0);
        String chatId = data.get(1).get(1);

        StringBuilder resultContent = new StringBuilder("All cards have tc/tr. Thank you for your cooperation! \uD83D\uDE4F\uD83C\uDFFC ");
        if (resultContainer.isEmpty()) {
            // If the outer array is empty or the last inner array is full, create a new inner array
            ArrayList<StringBuilder> innerArray = new ArrayList<StringBuilder>();
            innerArray.add(resultContent);
            resultContainer.add(innerArray);
        }

        for (ArrayList<StringBuilder> innerArray : resultContainer) {
            String formattedResult = String.join(" ", innerArray);

            int retries = 0;
            boolean sentSuccessfully = false;
            while (retries < MAX_RETRIES && !sentSuccessfully) {
                try {
                    URL url = new URL("https://api.telegram.org/bot" + token + "/sendMessage?chat_id=" + chatId + "&text=" + futureSprint + "%0A" + URLEncoder.encode(formattedResult, StandardCharsets.UTF_8));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);

                    int status = connection.getResponseCode();
                    String errorMessage = connection.getResponseMessage();
                    System.out.println(status + ": " + errorMessage + "\n" + url);

                    sentSuccessfully = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    retries++;
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            if (!sentSuccessfully) {
                System.err.println("Failed to send message after " + MAX_RETRIES + " retries.");
            }
        }
    }

    public void processResultContainer(String isTestCasesNull, String isTestRunNull, String isStoryPointsNull, StringBuilder resultContent) {
        boolean isAppended = isTestCasesNull.equals("None") || isTestRunNull.equals("None") || isStoryPointsNull.equals("-");

        if (isAppended) {
            if (resultContainer.isEmpty() || resultContainer.get(resultContainer.size() - 1).size() == maxInnerArraySize) {
                // If the outer array is empty or the last inner array is full, create a new inner array
                ArrayList<StringBuilder> innerArray = new ArrayList<StringBuilder>();
                innerArray.add(resultContent);
                resultContainer.add(innerArray);

            } else {
                // Otherwise, add the element to the last inner array
                resultContainer.get(resultContainer.size() - 1).add(resultContent);
            }
        }
    }

    public void processSubTasks(int cardIndex) {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        boolean hasSubTask = jiraObjects.hasSubTasks(cardIndex);
        if (hasSubTask) {
            // Show subTasks
            jiraObjects.showSubtasks(cardIndex);

            // Find the elements and get their count
            List<WebElement> subTaskCardElements = jiraObjects.subTaskCards(cardIndex);
            int subTasksCount = subTaskCardElements.size();
            System.out.println("Count: " + subTasksCount);

            for (int subTaskIndex = 0; subTaskIndex < subTasksCount; subTaskIndex++) {
                int x = subTaskIndex + 1;

                WebElement subCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(jiraObjects.perSubTask(cardIndex, x))));
                baseAction.scrollIntoView1(subCard);
                baseAction.clickButton(subCard);

                // Wait for the card detailed view to be visible
                wait.until(ExpectedConditions.visibilityOf(jiraObjects.cardDetailedView));

                // Get subTask card number
                String extractedCardNumber = jiraObjects.getCardNumber();

                // Get subTask card title
                String extractedCardTitle = jiraObjects.getCardTitle();

                // Get subTask card status
                String extractedCardStatus = jiraObjects.getSubTaskStatus(cardIndex, x);

                // Get subTask card tester
                String extractedCardTester = jiraObjects.getSubTaskTester(cardIndex, x);
                extractedCardTester = jiraObjects.getFormattedTester(extractedCardTester);

                // Get subTask card story points
                String extractedCardSP = jiraObjects.getSubTaskStoryPoints(cardIndex, x);

                // Get subTask card assignee
                baseAction.clickButton(subCard);
                String extractedCardAssignee = jiraObjects.getSubTaskAssignee(cardIndex, x);

                // Scroll into TestRail: Cases in detailed card view
                baseAction.scrollIntoView(jiraObjects.storyPointsDisplayInsideCard);
                baseAction.clickButton(jiraObjects.testCases);

                // Switch to iframes (testcases iframe 1 and 2)
                baseAction.switchToFrame(jiraObjects.tcIframe1);
                baseAction.switchToFrame(jiraObjects.tcIframe2);

                // Check if the card has test cases
                String testCasesStatus = jiraObjects.getTestCasesStatus();

                // Switch back to default frame
                driver.switchTo().defaultContent();
                baseAction.clickButton(jiraObjects.testCasesBack);
                baseAction.clickButton(jiraObjects.testRuns);

                // Switch to iframes
                baseAction.switchToFrame(jiraObjects.trIframe1);
                baseAction.switchToFrame(jiraObjects.trIframe2);

                // Check if the card has test runs
                String testRunsStatus = jiraObjects.getTestRunsStatus();

                // Switch to default frame then click back
                driver.switchTo().defaultContent();
                baseAction.clickButton(jiraObjects.testRunsBack);

                // Extracted card details are assigned to resultContent variable
                StringBuilder resultContent = new StringBuilder(jiraObjects.buildResultContent(extractedCardNumber, extractedCardTitle,
                        extractedCardTester, extractedCardAssignee, extractedCardStatus, extractedCardSP,
                        "0", testCasesStatus, testRunsStatus));

                // Call the processResultContainer() method, to check if the result needs to be added to the telegram message
                processResultContainer(testCasesStatus, testRunsStatus, extractedCardSP, resultContent);

                // Display the result in the log
                System.out.println(x + ". (" + extractedCardNumber + ") " + extractedCardTitle + "\n" +
                        "•Tester: " + extractedCardTester + "  |  •" + extractedCardAssignee + "\n" +
                        "•Status: " + extractedCardStatus + " | •Story Points: " + extractedCardSP + "\n" +
                        "•Subtask: 0 \n" +
                        "─ Test Cases: " + testCasesStatus + "\n" + "─ Test Runs: " + testRunsStatus + "\n");
            }

            // Hide subTasks
            jiraObjects.hideSubtasks(cardIndex);
        }
    }

    public void retryFindMainCardElements(WebDriverWait wait, List<WebElement> elements, int maxRetryCount, int index) throws InterruptedException {
        int retryCount = 0;

        while (retryCount < maxRetryCount) {
            try {
                // Re-find the cards element to refresh the reference
                wait.until(ExpectedConditions.visibilityOf(elements.get(index)));

                break; // Break the loop if element is found without a stale exception
            } catch (StaleElementReferenceException e) {
                Thread.sleep(1000); // Wait for a short duration before retrying

                retryCount++;
            }
        }
    }
}


