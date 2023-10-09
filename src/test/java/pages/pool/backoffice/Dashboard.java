package pages.pool.backoffice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class Dashboard {
    public Dashboard(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */
    //User account name
    @FindBy(how = How.CSS, using =  "form.d-flex > ul.navbar-nav.me-auto.mb-2.mb-lg-0 > li.nav-item.dropdown > #navbarDropdown")
    @CacheLookup
    public WebElement userAccount;

    //games header dropdown
    @FindBy(how = How.XPATH, using =  "(//a[@id='navbarDropdown'])[2]")
    @CacheLookup
    public WebElement gamesDropdown;

    //games>matches
    @FindBy(how = How.XPATH, using =  "//ul[@class='dropdown-menu show']/li/a[contains(text(), 'Matches')]")
    @CacheLookup
    public WebElement matches;
    //*[@href = 'https://admin.cpp555.com/matches' and (text() = 'Matches' or . = 'Matches')] <-- old xpath



    //games>sports
    @FindBy(how = How.XPATH, using =  ".//ul[@class='dropdown-menu show']/li/a[contains(text(), 'Sports')]")
    @CacheLookup
    public WebElement sports;

    //games>leagues
    @FindBy(how = How.XPATH, using =  ".//ul[@class='dropdown-menu show']/li/a[contains(text(), 'Leagues')]")
    @CacheLookup
    public WebElement leagues;
    public void clickLeagues() {leagues.click();}



    /**
     * Object action
     */
    //User account is display
    public void userAccountDisplay() {userAccount.isDisplayed();}

    //click games header
    public void clickGamesDropdown() {gamesDropdown.click();}

    //click matches
    public void clickMatches(){
        matches.isDisplayed();
        matches.click();
    }

    //matches is display
    public void MatchesDisplay() {matches.isDisplayed();}


    //click sports
    public void clickSports() {sports.isDisplayed(); sports.click();}


}
