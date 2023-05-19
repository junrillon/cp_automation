package pages.agents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AgentLoginPage {
    private final WebDriver driver;

    //Login Form
    @FindBy(how = How.XPATH, using = ".//form[@id='loginForm']")
    public WebElement loginForm;

    //Username Input Field
    @FindBy(how = How.XPATH, using = ".//input[@id='agent_username']")
    public WebElement agentUsernameField;

    //Password Input Field
    @FindBy(how = How.XPATH, using = ".//input[@id='password']")
    public WebElement agentPasswordField;

    //Login button
    @FindBy(how = How.XPATH, using = ".//button[@id='loginBtn']")
    public WebElement loginButton;

    public AgentLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void loginInAgentSite(String agentUsername, String password){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //wait for agent username field to be visible then enter value
        wait.until(ExpectedConditions.visibilityOf(agentUsernameField));
        agentUsernameField.sendKeys(agentUsername);

        //wait for agent password field to be visible then enter value
        wait.until(ExpectedConditions.visibilityOf(agentPasswordField));
        agentPasswordField.sendKeys(password);

        //wait for the login button to be clickable then click
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

}
