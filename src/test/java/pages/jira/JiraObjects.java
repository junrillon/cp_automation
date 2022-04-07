package pages.jira;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class JiraObjects {

    private WebDriver driver;
    public JiraObjects(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

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

    @FindBy(how = How.ID, using = "ghx-detail-view")
    public WebElement cardDetailedView;

    @FindBy(how = How.XPATH, using = ".//div[@class='mffpf0-2 bWvVtC']") //<-- details
    public WebElement cardDetails;

    @FindBy(how = How.XPATH, using = ".//h2[contains(text(), 'Sprint')]") //<-- details
    public WebElement sprintDisplayInsideCard;

    @FindBy(how = How.XPATH, using = ".//div[contains(concat(' ',@class,' '), ' sc-1ddwfnu-3 ') and contains(text(), 'TestRail: Cases')]")
    public WebElement testCases;

    @FindBy(how = How.XPATH, using = ".//div[@class='anythr-2 gphqXW' and contains(text(), 'TestRail: Cases')]")
    public WebElement testCasesBack;

    @FindBy(how = How.XPATH, using = ".//div[contains(concat(' ',@class,' '), ' sc-1ddwfnu-3 fXigRG ') and contains(text(), 'TestRail: Runs')]")
    public WebElement testRuns;

    @FindBy(how = How.XPATH, using = ".//div[@class='anythr-2 gphqXW' and contains(text(), 'TestRail: Runs')]")
    public WebElement testRunsBack;

    @FindBy(how = How.XPATH, using = ".//p[@class='bottom']/a[@class='button']")
    public WebElement loginToTestRailButton;

    @FindBy(how = How.XPATH, using = ".//iframe[contains(@id,'com.testrail.jira.testrail-plugin__panel-references')]")
    public WebElement tcIframe1;

    @FindBy(how = How.XPATH, using = ".//iframe[@id='tr-frame-panel-references']")
    public WebElement tcIframe2;

    @FindBy(how = How.XPATH, using = ".//iframe[contains(@id,'com.testrail.jira.testrail-plugin__panel-runsreferences')]")
    public WebElement trIframe1;

    @FindBy(how = How.XPATH, using = ".//iframe[@id='tr-frame-panel-runsreferences']")
    public WebElement trIframe2;
    //---------------------------------------------------

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'index.php?/auth/login')]")
    public List<WebElement> TestrailUrl;

    @FindBy(how = How.XPATH, using = ".//section[@id='content']/p")
    public List<WebElement> testCases_status;

    @FindBy(how = How.XPATH, using = ".//section[@id='content']/p")
    public List<WebElement> testRuns_status;

    @FindBy(how = How.XPATH, using = ".//section[@id='content']//p[@class='top']/em")
    public List<WebElement> loginToTestRail;

    public String advanceSprintXpath = ".//div[contains(concat(' ',@class,' '), ' ghx-sprint-planned ')][1]";
    public String activeSprintXpath = "//div[@class='ghx-backlog-container ghx-sprint-active js-sprint-container ghx-open ui-droppable'][1]";
    public String perCardXpath = advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]";
    public String issueCountXpath = advanceSprintXpath + "//div[@class='ghx-issue-count']";
    public String sprintNumberXpath = advanceSprintXpath +"//div[@class='ghx-name']";
    public String perCardTitleXpath = "//span[@class='ghx-inner']";
    public String perCardNumberXpath = "//span[@class='ghx-end ghx-items-container']/a";
    public String perCardAssigneeXpath = "//span[@class='ghx-end ghx-items-container']/img"; // <-- get Alt
    public String perCardTesterXpath = "//div[contains(concat(' ',@class,' '), ' ghx-plan-extra-fields ')]//span[3]/span";
    public String perCardStatusXpath = "//div[contains(concat(' ',@class,' '), ' ghx-plan-extra-fields ')]//span[1]/span";

//    public List<WebElement> testCases_status(){
//        return driver.findElements(By.xpath("//p[contains(text(),'No test cases in TestRail are linked to this issue.')]"));
//
//    }
//
//    public List<WebElement> testRuns_status(){
//        return driver.findElements(By.xpath("//p[contains(text(),'No test runs in TestRail are linked to this issue.')]"));
//
//    }

    //.//div[@class='ghx-backlog-container ghx-sprint-planned js-sprint-container ghx-open ui-droppable'][1]
}
