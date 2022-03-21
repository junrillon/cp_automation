package Pages.Frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPage {
    public RegistrationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */
    @FindBy(how = How.XPATH, using = "//input[@id='userName']")
    public WebElement username;

    @FindBy(how = How.XPATH, using = "//input[@id='password']")
    public WebElement password;

    @FindBy(how = How.XPATH, using = "//input[@id='confirmPassword']")
    public WebElement confirmPassword;

    @FindBy(how = How.XPATH, using = "//input[@id='fullName']")
    public WebElement fullName;

    @FindBy(how = How.XPATH, using = "//input[@id='cpf']")
    public WebElement cpf;

    @FindBy(how = How.XPATH, using = "//input[@id='birthday']")
    public WebElement birthday;

    @FindBy(how = How.XPATH, using = "//input[@id='email']")
    public WebElement email;

    @FindBy(how = How.XPATH, using = "//input[@id='contact']")
    public WebElement contact;

    @FindBy(how = How.XPATH, using = "//input[@id='agentCode']")
    public WebElement agentCode;

    @FindBy(how = How.XPATH, using = "//input[@id='validation']")
    public WebElement captcha;

    @FindBy(how = How.XPATH, using = "//*[@id='ct']")
    public WebElement captchaValue;

    @FindBy(how = How.XPATH, using = "//input[@id='pptc']")
    public WebElement pptc;

    @FindBy(how = How.XPATH, using = "//button[@class='btn btn-primary' and contains(text(),'Submit')]")
    public WebElement submitButton;


    //Alerts
    @FindBy(how = How.XPATH, using = "//div[@class='registration-success login-page']")
    public WebElement registrationSuccessModal;

    @FindBy(how = How.XPATH, using = "//div[@class='registration-success login-page']//a[@class='btn btn-primary']")
    public WebElement loginBtnAfterRegistration;

    @FindBy(how = How.XPATH, using = "//div[@class='registration-success login-page']//p")
    public WebElement successMsg;

    @FindBy(how = How.XPATH, using = "//div[@class=\"alert alert-danger\"]")
    public WebElement error;
}
