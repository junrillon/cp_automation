package pages.jira;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pages.PageModelBase;

import java.util.List;

public class JiraObjects{

    private WebDriver driver;
    
    public JiraObjects(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */
    @FindBy(how = How.ID, using = "username")
    @CacheLookup
    public WebElement usernameField;

    @FindBy(how = How.ID, using = "password")
    @CacheLookup
    public WebElement passwordField;

    @FindBy(how = How.ID, using = "login-submit")
    @CacheLookup
    public WebElement loginButton;

    @FindBy(how = How.XPATH, using = ".//header[@role='banner']")
    @CacheLookup
    public WebElement homeBanner;

    @FindBy(how = How.XPATH, using = ".//div[@data-testid='home-page-content']")
    @CacheLookup
    public WebElement homePage;

    @FindBy(how = How.XPATH, using = ".//span[contains(text(),'Backlog')]")
    @CacheLookup
    public WebElement backlog;

    @FindBy(how = How.ID, using = "ghx-detail-view")
    public WebElement cardDetailedView;

    @FindBy(how = How.XPATH, using = ".//div[@class='mffpf0-2 bWvVtC']") //<-- details
    public WebElement cardDetails;

    @FindBy(how = How.XPATH, using = ".//h2[contains(text(), 'Story Points')]") //<-- details
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

    @FindBy(how = How.ID, using = "tr-frame-panel-references")
    public WebElement tcIframe2;

    @FindBy(how = How.XPATH, using = ".//iframe[contains(@id,'com.testrail.jira.testrail-plugin__panel-runsreferences')]")
    public WebElement trIframe1;

    @FindBy(how = How.ID, using = "tr-frame-panel-runsreferences")
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

    @FindBy(how = How.XPATH, using = ".//div[contains(concat(' ',@class,' '), ' ghx-sprint-planned ')][1]")
    public WebElement advanceSprintXpathWE;

    public String advanceSprintXpath = ".//div[contains(concat(' ',@class,' '), ' ghx-sprint-planned ')][1]";
    public String activeSprintXpath = "//div[@class='ghx-backlog-container ghx-sprint-active js-sprint-container ghx-open ui-droppable'][1]";
    public String perCardXpath = advanceSprintXpath + "//div[contains(concat(' ',@class,' '), ' ghx-backlog-card ')]";
    public String issueCountXpath = advanceSprintXpath + "//div[@class='ghx-issue-count']";
    public String issueContentXpath = perCardXpath + "//div[@class='ghx-issue-content']";
    public String sprintNumberXpath = advanceSprintXpath +"//div[@class='ghx-name']";
    public String perCardTitleXpath = "//span[@class='ghx-inner']";
    public String perCardNumberXpath = "//span[@class='ghx-end ghx-items-container']/a";
    public String perCardAssigneeXpath = "//span[@class='ghx-end ghx-items-container']/img"; // <-- get Alt
    public String perCardTesterXpath = "//div[contains(concat(' ',@class,' '), ' ghx-plan-extra-fields ')]//span[3]/span";
    public String perCardStatusXpath = "//div[contains(concat(' ',@class,' '), ' ghx-plan-extra-fields ')]//span[1]/span";

}
