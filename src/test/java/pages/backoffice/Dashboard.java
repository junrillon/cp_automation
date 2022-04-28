package pages.backoffice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Dashboard {
    private WebDriver driver;

    public Dashboard(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    /**
     * Object repository
     */
    @FindBy(how = How.ID, using = "username")
    @CacheLookup
    public WebElement usernameField;

    @FindBy(how = How.ID, using = "password")
    @CacheLookup
    public WebElement passwordField;

    @FindBy(how = How.XPATH, using = ".//button[@type='submit' and contains(text(), 'Login')]")
    @CacheLookup
    public WebElement loginButton;

    @FindBy(how = How.XPATH, using = ".//span[@class='username btn btn-default']")
    @CacheLookup
    public WebElement adminUsername;

    //a[@id='nav_Casino']
    @FindBy(how = How.ID, using = "nav_Casino")
    @CacheLookup
    public WebElement navCasino;

    @FindBy(how = How.ID, using = "nav_Game List Management")
    @CacheLookup
    public WebElement navCasino_GameListManagement;
}
