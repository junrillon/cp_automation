package pages.frontend.brasilbet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginBrasilbet {
    public LoginBrasilbet(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */

    // Username field
    @FindBy(how = How.ID, using =  "defaultForm-email")
    public WebElement txtUserName;

    // password field
    @FindBy(how = How.ID, using =  "defaultForm-pass")
    public WebElement txtPassword;

    // captcha txt
    @FindBy(how = How.ID, using =  "defaultForm-captcha")
    public WebElement txtCaptcha;

    // captcha field
    @FindBy(how = How.ID, using =  "ct")
    public WebElement getCaptcha;

    // banner exit button
    @FindBy(how = How.XPATH, using =  ".//i[@class='fa fa-times-circle fa-2x']")
    public WebElement bannerExitBtn;

    @FindBy(how = How.XPATH, using =  ".//span[@class='fas fa-sign-in-alt']")
    public WebElement navLogin;

    // login button
    @FindBy(how = How.XPATH, using =  "/html/body/div[2]/div/div/div[2]/div[2]/button/span")
    public WebElement loginBtn;

    // continue button
    @FindBy(how = How.XPATH, using =  ".//i[@class='fa fa-check']")
    public WebElement ContinueSession;

    /**
     * Object action
     */

    // input username and password
    public void Login(String username,String password){
        txtUserName.sendKeys(username);
        txtPassword.sendKeys(password);
    }

    // get captcha and input captcha
    public void getAndInputCaptcha(){
        String cptcha = getCaptcha.getText();
        txtCaptcha.sendKeys(cptcha);
    }

    public void clickLoginBtn(){

        loginBtn.click();
    }



    //Active Session
    @FindBy(how = How.XPATH, using =  "//div[@class='react-confirm-alert-body']")
    public WebElement activeSessionAlert;

    @FindBy(how = How.XPATH, using =  "//div[@class='react-confirm-alert-button-group']/button[1]")
    public WebElement activeSessionContinueBtn;

    // Wallet
    @FindBy(how = How.XPATH, using =  ".//span[@class='wallet-balance']")
    public WebElement wallet;

    // Withdraw
    @FindBy(how = How.XPATH, using =  ".//a[@href='/account/withdraw']")
    public WebElement withdrawButton;




}
