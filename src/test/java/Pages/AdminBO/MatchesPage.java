package Pages.AdminBO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MatchesPage {
    public MatchesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */
    //Create match button
    @FindBy(how = How.ID, using =  "btn-match-create")
    public WebElement createMatchBtn;

    //sports dropdown from create matches
    @FindBy(how = How.CSS, using =  "#sport-list")
    public WebElement sportsDropdown;

    //league dropdown from create matches
    @FindBy(how = How.CSS, using =  "#league-list")
    public WebElement leagueDropdown;

    //input match count from create matches
    @FindBy(how = How.CSS, using =  "#match-count-create")
    public WebElement matchCountTxt;


    /**
     * Object action
     */
    //create match button is display
    public void createMatchDisplay() {createMatchBtn.isDisplayed();}

    //click create match button
    public void clickCreateMatch() {createMatchBtn.click();}

    //matches is display
    public void sportDropDownDisplay() {sportsDropdown.isDisplayed();}

    //select sport
    public void selectSports(String selectedSports) {sportsDropdown.sendKeys(selectedSports);}

    //select league
    public void selectLeague(String selectedLeague) {leagueDropdown.sendKeys(selectedLeague);}

    //select league
    public void inputMatchCount(String matchCountInput) {matchCountTxt.sendKeys(matchCountInput);}


}
