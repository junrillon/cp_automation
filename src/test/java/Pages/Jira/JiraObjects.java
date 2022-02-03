package Pages.Jira;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class JiraObjects {

    public JiraObjects(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */
    @FindBy(how = How.XPATH, using = "//input[@id='username']")
    public WebElement username;

    @FindBy(how = How.XPATH, using = "//input[@id='password']")
    public WebElement password;

    @FindBy(how = How.XPATH, using = "//button[@id='login-submit']")
    public WebElement loginButton;


    public void inputCredentials(String usernameAdmin,String passwordAdmin){
        username.isDisplayed();
        password.isDisplayed();
        username.sendKeys(usernameAdmin);
        password.sendKeys(passwordAdmin);
    }

    public void clickLoginButton(){
        loginButton.isDisplayed();
        loginButton.click();
    }

    @FindBy(how = How.XPATH, using = "//header[@role='banner']")
    public WebElement homeBanner;

    @FindBy(how = How.XPATH, using = "//div[@data-testid='home-page-content']")
    public WebElement homePage;

    @FindBy(how = How.XPATH, using = "//span[contains(text(),'Backlog')]")
    public WebElement backlog;

    @FindBy(how = How.XPATH, using = "//div[@class='ghx-issue-count']")
    public WebElement issueCount;

    @FindBy(how = How.XPATH, using = "//div[@id='ghx-detail-view']")
    public WebElement cardDetailedView;

    @FindBy(how = How.XPATH, using = "//div[@class='mffpf0-2 bWvVtC']") //<-- details
    public WebElement cardDetails;

    @FindBy(how = How.XPATH, using = "//div[contains(concat(' ',@class,' '), ' sc-1ddwfnu-3 ') and contains(text(), 'TestRail: Cases')]")
    public WebElement testCases;

    @FindBy(how = How.XPATH, using = "//div[@class='anythr-2 gphqXW' and contains(text(), 'TestRail: Cases')]")
    public WebElement testCasesBack;

    @FindBy(how = How.XPATH, using = "//div[contains(concat(' ',@class,' '), ' sc-1ddwfnu-3 fXigRG ') and contains(text(), 'TestRail: Runs')]")
    public WebElement testRuns;

    @FindBy(how = How.XPATH, using = "//div[@class='anythr-2 gphqXW' and contains(text(), 'TestRail: Runs')]")
    public WebElement testRunsBack;

}
