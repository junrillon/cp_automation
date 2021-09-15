package Pages.AdminBO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePageAdmin {
    public HomePageAdmin(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */
    //User account name
    @FindBy(how = How.CSS, using =  "form.d-flex > ul.navbar-nav.me-auto.mb-2.mb-lg-0 > li.nav-item.dropdown > #navbarDropdown")
    public WebElement userAccount;

    //games header dropdown
    @FindBy(how = How.XPATH, using =  "(//a[@id='navbarDropdown'])[2]")
    public WebElement gamesDropdown;

    //games>matches
    @FindBy(how = How.XPATH, using =  "//div[@id='navbarSupportedContent']/ul/li[3]/ul/li[3]/a")
    public WebElement matches;


    /**
     * Object action
     */
    //User account is display
    public void userAccountDisplay() {userAccount.isDisplayed();}

    //click games header
    public void clickGamesDropdown() {gamesDropdown.click();}

    //click matches
    public void clickMatches() {matches.click();}

    //matches is display
    public void MatchesDisplay() {matches.isDisplayed();}


}
