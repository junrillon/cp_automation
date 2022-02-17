package Pages.AgentSite;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPageAgents {
    public LoginPageAgents(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */

    // Agent Username field
    @FindBy(how = How.ID, using =  "agent_username")
    public WebElement txtAgentUserName;

    // password field
    @FindBy(how = How.ID, using =  "password")
    public WebElement txtAgentPassword;

    // captcha txt
    @FindBy(how = How.ID, using =  "captcha")
    public WebElement txtCaptcha;

    /**
     * Object action
     */

    // input username and password
    public void LoginAgents(String agentUsername,String agentPassword)
    {
        txtAgentUserName.sendKeys(agentUsername);
        txtAgentPassword.sendKeys(agentPassword);
        txtCaptcha.click();
    }
}
