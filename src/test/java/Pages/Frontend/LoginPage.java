package Pages.Frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public LoginPage(WebDriver driver) {
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

    // login button
    @FindBy(how = How.CSS, using =  "div.login-footer.d-flex.justify-content-center > button.btn.btn-primary")
    public WebElement loginBtn;


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
    @FindBy(how = How.XPATH, using =  "//div[@class='react-confirm-alert-body']")
    public WebElement activeSessionAlert;

    @FindBy(how = How.XPATH, using =  "//div[@class='react-confirm-alert-button-group']/button[1]")
    public WebElement activeSessionContinuebtn;

    // Games Casino
    @FindBy(how = How.XPATH, using =  "//ul[@class='navbar-nav mr-auto']/li/a[@href='/casino/casino']")
    public WebElement navGamesCasino;

    // Provider filter
    @FindBy(how = How.XPATH, using =  "//div[@class='custom-control custom-checkbox']")
    public WebElement providerFilter;

    public void locateProviderFilter(){
        providerFilter.isDisplayed();
    }

    // Game card
    @FindBy(how = How.XPATH, using =  "//div[@class=\"game-card\"]")
    public WebElement gameCard;

    @FindBy(how = How.XPATH, using =  "//div[@class='game-content']/div[2]")
    public WebElement casinoGamesContainer;

    @FindBy(how = How.XPATH, using =  "//div[@class='play-button-transition']/button")
    public WebElement playButton;

    public void clickPlayButton(){
        playButton.isDisplayed();
        playButton.click();
    }

    @FindBy(how = How.XPATH, using =  "//section[@class='game-view']")
    public WebElement maintenanceNotif;

    @FindBy(how = How.XPATH, using =  "//iframe[@class='w-100 h-100 border-none']")
    public WebElement gameIframe;

    @FindBy(how = How.XPATH, using =  "//div[@id='slotContainer']/iframe")
    public WebElement gameIframe2;

    @FindBy(how = How.XPATH, using =  "//div[@class='btn-circle-inner-play-button']")
    public WebElement innerPlayButton;

    @FindBy(how = How.XPATH, using =  "//div[@class='statistics-field-value']")
    public WebElement balanceValue;

    @FindBy(how = How.XPATH, using =  "//div[@class='stake-value']")
    public WebElement stakeValue;

    @FindBy(how = How.XPATH, using =  "//div[@class='win-text']")
    public WebElement winIndicator;

    @FindBy(how = How.XPATH, using =  "//div[@class='win-text']/canvas")
    public WebElement winIndicator2;



}
