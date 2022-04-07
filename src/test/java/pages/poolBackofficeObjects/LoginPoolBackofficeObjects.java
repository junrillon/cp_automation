package pages.poolBackofficeObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;


public class LoginPoolBackofficeObjects {
    public LoginPoolBackofficeObjects(WebDriver driver) {
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



    /**
     * Object action
     */

    // input username and password
    public void LoginAdmin(String usernameAdmin,String passwordAdmin)
    {
        txtUserNameAdmin.sendKeys(usernameAdmin);
        txtPasswordAdmin.sendKeys(passwordAdmin);
        txtCaptcha.click();
    }



}


