package pages.frontend.brasilbet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
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
    @CacheLookup
    public WebElement txtUserName;

    // password field
    @FindBy(how = How.ID, using =  "defaultForm-pass")
    @CacheLookup
    public WebElement txtPassword;

    // captcha txt
    @FindBy(how = How.ID, using =  "defaultForm-captcha")
    @CacheLookup
    public WebElement txtCaptcha;

    // captcha field
    @FindBy(how = How.ID, using =  "ct")
    @CacheLookup
    public WebElement getCaptcha;

    // banner exit button
    @FindBy(how = How.XPATH, using =  ".//i[@class='fa fa-times-circle fa-2x']")
    @CacheLookup
    public WebElement bannerExitBtn;

    @FindBy(how = How.XPATH, using =  ".//span[@class='fas fa-sign-in-alt']")
    @CacheLookup
    public WebElement navLogin;

    // login button
    @FindBy(how = How.XPATH, using =  "/html/body/div[2]/div/div/div[2]/div[2]/button/span")
    @CacheLookup
    public WebElement loginBtn;

    // continue button
    @FindBy(how = How.XPATH, using =  ".//i[@class='fa fa-check']")
    @CacheLookup
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
    @CacheLookup
    public WebElement activeSessionAlert;

    @FindBy(how = How.XPATH, using =  "//div[@class='react-confirm-alert-button-group']/button[1]")
    @CacheLookup
    public WebElement activeSessionContinueBtn;

    // Wallet
    @FindBy(how = How.XPATH, using =  ".//span[@class='wallet-balance']")
    @CacheLookup
    public WebElement wallet;

    // Withdraw
    @FindBy(how = How.XPATH, using =  ".//a[@href='/account/withdraw']")
    @CacheLookup
    public WebElement withdrawButton;




}
