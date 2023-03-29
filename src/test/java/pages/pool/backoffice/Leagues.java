package pages.pool.backoffice;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import steps.pool.backoffice.Sports;



public class Leagues {


    private WebDriver driver;

    public Leagues(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */

    // click sports btn
    @FindBy(how = How.ID, using =  "btn-league-create")
    @CacheLookup
    public WebElement createLeagueBtn;
    public void clickCreateLeague() {
        createLeagueBtn.click();
    }

    // sports dropdown
    @FindBy(how = How.ID, using =  "sport")
    @CacheLookup
    public WebElement sportDropdown;
    public void selectSports(String selectedSports) {
        Select select = new Select(sportDropdown);
        select.selectByVisibleText(selectedSports);
    }

    // League name
    @FindBy(how = How.ID, using =  "leagueName")
    @CacheLookup
    public WebElement leagueNameText;
    public void InputLeagueName(String leagueName) {
        leagueNameText.sendKeys(leagueName);
    }

    // submit button
    @FindBy(how = How.ID, using =  "submit")
    @CacheLookup
    public WebElement submitBtn;
    public void clickSubmitBtn() {
        submitBtn.click();
    }

    // success button
    @FindBy(how = How.XPATH, using =  ".//button[@class='swal-button swal-button--confirm']")
    @CacheLookup
    public WebElement successBtn;
    public void clickSuccessBtn() {
        successBtn.click();
    }

    // success button
    @FindBy(how = How.XPATH, using =  ".//div[@class='swal-title']")
    @CacheLookup
    public WebElement successMsg;


    // searchSports
    @FindBy(how = How.ID, using =  "select2-sportList-container") //
    @CacheLookup
    public WebElement searchSports;
    public void setSearchSports() {
        searchSports.sendKeys("test");
    }

    //  display dynamic sports in data table
    public void sportDatatableDisplay(String dynamicSportXpath) {
        String sportXpath = ".//tr[@class='odd']//td[contains(text(), '" + dynamicSportXpath + "')]";
        WebElement dynamicSportElement = driver.findElement(By.xpath(sportXpath));
        dynamicSportElement.isDisplayed();
            }

    //  display dynamic league in data table
    public void leagueDatatableDisplay(String dynamicLeagueXpath) {
        String leagueXpath = ".//tr[@class='odd']//td[contains(text(), '" + dynamicLeagueXpath + "')]";
        WebElement dynamicLeagueElement = driver.findElement(By.xpath(leagueXpath));
        dynamicLeagueElement.isDisplayed();
    }


}


