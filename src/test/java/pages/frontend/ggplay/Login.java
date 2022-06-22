package pages.frontend.ggplay;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class Login {
    public Login(WebDriver driver) {
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

    // login button .//button[@type='submit' and contains(text()
    @FindBy(how = How.XPATH, using =  ".//div[@class='login-page']//button[@class=\"btn btn-primary\"]")
    @CacheLookup
    public WebElement loginBtn;

    //.//div[@class='login-page']//button[@class="btn btn-primary" and contains(text(),"Login")]


    // banner exit button
    @FindBy(how = How.XPATH, using =  ".//i[@class='fa fa-times-circle fa-2x']")
    @CacheLookup
    public List<WebElement> bannerExitBtn;

    // continue button
    @FindBy(how = How.ID, using = "react-confirm-alert")
    @CacheLookup
    public List<WebElement> AlertModal;

    // continue button
    @FindBy(how = How.XPATH, using = ".//div[@class='react-confirm-alert-button-group']//i[@class=\"fa fa-check\"]")
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



    //CASINO ELEMENTS
    @FindBy(how = How.XPATH, using =  ".//div[@class='react-confirm-alert-body']")
    @CacheLookup
    public WebElement activeSessionAlert;

    @FindBy(how = How.XPATH, using =  ".//div[@class='react-confirm-alert-button-group']/button[1]")
    @CacheLookup
    public WebElement activeSessionContinuebtn;


}
