package pages.pool.backoffice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class Sports {

    private WebDriver driver;
    public Sports(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */

    // click sports btn
    @FindBy(how = How.ID, using =  "btn-sport-create")
    @CacheLookup
    public WebElement createSportBtn;
    public void clickCreateSport() {
        createSportBtn.click();
    }

    // sports name input
    @FindBy(how = How.ID, using =  "sport-name-create")
    @CacheLookup
    public WebElement sportNameInputText;
    public void inputSportName(String sportsName) {
        sportNameInputText.sendKeys(sportsName);
    }

    // match label input
    @FindBy(how = How.ID, using =  "match_label")
    @CacheLookup
    public WebElement matchLabelInputText;
    public void inputMatchLabel(String matchLabel) {
        matchLabelInputText.sendKeys(matchLabel);
    }

    // video url input
    @FindBy(how = How.ID, using =  "videourl")
    @CacheLookup
    public WebElement videoUrlInputText;
    public void inputVideoUrl(String videoUrl) {
        videoUrlInputText.sendKeys(videoUrl);
    }

    // image
    @FindBy(how = How.ID, using =  "sportimage")
    @CacheLookup
    public WebElement chooseFileBtn;
    public void clickChooseFileBtn(String image) {
        chooseFileBtn.sendKeys(image);
    }

    // submit btn
    @FindBy(how = How.ID, using =  "submitSportBtn")
    @CacheLookup
    public WebElement submitBtn;
    public void clickSubmitBtn() {
        submitBtn.click();
    }

    // success msg
    @FindBy(how = How.ID, using =  "swal2-title")
    @CacheLookup
    public WebElement successModal;

    // success msg
    @FindBy(how = How.ID, using =  "sport-name-create")
    @CacheLookup
    public WebElement inputSportName;


    // success ok btn
    @FindBy(how = How.XPATH, using =  ".//button[@class='swal2-confirm swal2-styled']")
    @CacheLookup
    public WebElement successOkBtn;
    public void clickOkBtn() {
        successOkBtn.click();
    }


   // submit search btn
    @FindBy(how = How.XPATH, using =  ".//button[@class='btn btn-primary btn-sm']")
    @CacheLookup
    public WebElement getSubmitBtn;
    public void clickSubmitSearchBtn() {
        getSubmitBtn.click();
    }

    // success ok btn
    @FindBy(how = How.ID, using =  "sportlist")
    @CacheLookup
    public WebElement selectSportSearch;
    public void clickSportSearch(String sportName) {
        Select select = new Select(selectSportSearch);
        select.selectByVisibleText(sportName);
    }

    //  display dynamic sports in data table
    //public static WebElement dynamicSportElement;
    public void sportNameDatatableDisplay(String dynamicSportXpath) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String sportXpath = ".//tr[@class='odd']//td[contains(text(), '" + dynamicSportXpath + "')]";
        WebElement dynamicSportElement = driver.findElement(By.xpath(sportXpath));
        wait.until(ExpectedConditions.visibilityOf(dynamicSportElement));
       // dynamicSportElement.isDisplayed();

    }

        // ACtion dropdown
    @FindBy(how = How.ID, using =  "sport-action")
    @CacheLookup
    public WebElement actionDropdown;
    public void clickEditSport() {
        Select select = new Select(actionDropdown);
        select.selectByVisibleText("Edit Sport");
    }

}


