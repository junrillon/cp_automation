package Pages.Backoffice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Backoffice {
    public Backoffice(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */
    @FindBy(how = How.XPATH, using = "//input[@id='username']")
    public WebElement username;

    @FindBy(how = How.XPATH, using = "//input[@id='password']")
    public WebElement password;

    @FindBy(how = How.XPATH, using = "//button[@class='btn red btn-block uppercase']")
    public WebElement loginButton;


    public void inputCredentials(String usernameAdmin,String passwordAdmin){
        username.isDisplayed();
        password.isDisplayed();
        loginButton.isDisplayed();

        username.sendKeys(usernameAdmin);
        password.sendKeys(passwordAdmin);
        loginButton.click();
    }

    @FindBy(how = How.XPATH, using = "//span[@class='username btn btn-default']")
    public WebElement adminUsername;

    @FindBy(how = How.XPATH, using = "//a[@id='nav_Casino']")
    public WebElement navCasino;

    @FindBy(how = How.XPATH, using = "//a[@id='nav_Game List Management']")
    public WebElement navCasino_GameListManagement;


}
