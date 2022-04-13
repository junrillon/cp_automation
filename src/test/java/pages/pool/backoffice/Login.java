package pages.pool.backoffice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;


public class Login {
    public Login(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */

    // Username field
    @FindBy(how = How.ID, using =  "inputUsername")
    public WebElement txtUserNameAdmin;

    // password field
    @FindBy(how = How.ID, using =  "inputPassword")
    public WebElement txtPasswordAdmin;

    // captcha txt
    @FindBy(how = How.ID, using =  "captcha")
    public WebElement txtCaptcha;


    // captcha txt
    @FindBy(how = How.ID, using =  "loginBtn")
    public WebElement signIn;



    /**
     * Object action
     */

    // input username and password
    public void LoginAdmin(String usernameAdmin,String passwordAdmin)
    {
        txtUserNameAdmin.sendKeys(usernameAdmin);
        txtPasswordAdmin.sendKeys(passwordAdmin);
        signIn.click();
    }



}


