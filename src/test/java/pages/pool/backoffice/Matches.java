package pages.pool.backoffice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class Matches {
    public Matches(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    /**
     * Object repository
     */
    //Create match button
    @FindBy(how = How.ID, using =  "btn-match-create")
    @CacheLookup
    public WebElement createMatchBtn;

    //sports dropdown from create matches
    @FindBy(how = How.CSS, using =  "#sport-list")
    @CacheLookup
    public WebElement sportsDropdown;

    //league dropdown from create matches
    @FindBy(how = How.ID, using =  "league-list")
    @CacheLookup
    public WebElement leagueDropdown;

    //input match date from create matches
    @FindBy(how = How.ID, using =  "datepicker")
    @CacheLookup
    public WebElement datePicker;

    //input match count from create matches
    @FindBy(how = How.ID, using =  "match-count-create")
    @CacheLookup
    public WebElement matchCountTxt;

    //Submit button
    @FindBy(how = How.ID, using =  "create-match-btn")
    @CacheLookup
    public WebElement submitBtn;

    //Match page empty table
    @FindBy(how = How.XPATH, using = ".//table[@id='DataTables_Table_0']//tbody")
    @CacheLookup
    public WebElement matchPageMatchesTable;

    //Action button
    @FindBy(how = How.CSS, using =  "select.custom-select.match-action")
    @CacheLookup
    public WebElement actionDrpdown;

    @FindBy(how = How.XPATH, using = ".//input[@type='search']")
    @CacheLookup
    public WebElement searchField;


    /**
     * Object action
     */

    //click create match button
    public void clickCreateMatch()
    {
        createMatchBtn.isDisplayed();
        createMatchBtn.click();
    }

    //select sport
    public void selectSports(String selectedSports)
    {
        sportsDropdown.isDisplayed();
        sportsDropdown.sendKeys(selectedSports);
    }

    //select league
    public void selectLeague(String selectedLeague)
    {
        Select drpDownLeague = new Select(leagueDropdown);
        drpDownLeague.selectByVisibleText(selectedLeague);
    }

    //select sport
    public void inputDate(String dateToday) {datePicker.sendKeys(dateToday);}

    //select league
    public void inputMatchCount(String matchCountInput) {matchCountTxt.sendKeys(matchCountInput);}


    //click submit button
    public void clickSubmitBtn() {submitBtn.click();}

    //matches is display
    public void selectFromActionDrpDown(){
        actionDrpdown.isDisplayed();
        Select actionDrpDown = new Select(actionDrpdown);
        actionDrpDown.selectByVisibleText("View Match Details");
    }

    //search field is displayed
    public void clickSearchField(){
        searchField.isDisplayed();
        searchField.click();
    }



}
