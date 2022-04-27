package pages.b2cBackoffice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pages.PageModelBase;

import java.util.List;

public class B2CBackofficeObjects {
    private WebDriver driver;

    public B2CBackofficeObjects(WebDriver driver) {
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

    // ----- Start of filters
    @FindBy(how = How.XPATH, using = ".//div[@class='filter']//input[@title='Game Name']") //<-- Game name filter
    @CacheLookup
    public WebElement gameNameFilter;

    @FindBy(how = How.ID, using = "casinoRoom") //<-- Casino room filter
    @CacheLookup
    public WebElement casinoRoomFilter;

    @FindBy(how = How.XPATH, using = ".//div[@class='actions']//button[@id='filterButton']") //<-- Apply filter button
    @CacheLookup
    public WebElement applyFilterButton;
    // ----- END of filters


    @FindBy(how = How.XPATH, using = ".//div[@class='page-title']/h1")
    @CacheLookup
    public WebElement pageTitle;

    @FindBy(how = How.XPATH, using = ".//ul[@class='casino-room-accordion']")
    @CacheLookup
    public WebElement providerSelection;

    @FindBy(how = How.XPATH, using = ".//ul[@class='inner show']/li/a/span") //ul[@class='inner show']/li/a/span[@class='game-provider-accordion']
    @CacheLookup
    public WebElement providerInnerSelection;

    @FindBy(how = How.XPATH, using = ".//div[@class='modal-dialog modal-sm']//button[@class='close']")
    @CacheLookup
    public WebElement modalCloseButton;

    @FindBy(how = How.XPATH, using = ".//div[@id='modal-loading' and contains(@style,'display: block;')]//div[@class=\"modal-content\"]")
    @CacheLookup
    public List<WebElement> gDetailsModal;

    @FindBy(how = How.XPATH, using = ".//div[@id='modal-loading' and contains(@style,'display: block;')]//div[@class=\"modal-content\"]")
    @CacheLookup
    public WebElement WE_gDetailsModal;

    //.//div[@id='modal-loading' and contains(@style,'display: block;')]

    @FindBy(how = How.ID, using = "gameListTable") //<-- Game list table
    @CacheLookup
    public WebElement gameListTable;

    @FindBy(how = How.XPATH, using = ".//div[@class='game-image']") //<-- Game Image
    @CacheLookup
    public WebElement gameImage;

    @FindBy(how = How.XPATH, using = ".//button[@class='btn btn-primary btn-xs upload-btn']") //<-- Upload Image Button
    @CacheLookup
    public WebElement uploadImageButton;

    @FindBy(how = How.ID, using = "form-upload") //<-- Upload Image Modal
    @CacheLookup
    public WebElement uploadImageForm;

    @FindBy(how = How.XPATH, using = ".//form[@id='form-upload']//button[@class='close']") //<-- Upload Image modal close button
    @CacheLookup
    public WebElement uploadImageFormCloseButton;

    @FindBy(how = How.ID, using = "input-game_image") //<-- Choose file Button | /input[@type='file' and @id='input-game_image']
    @CacheLookup
    public WebElement chooseFileButton;

    @FindBy(how = How.ID, using = "action-save-banner") //<-- Upload Image Save button
    @CacheLookup
    public WebElement saveButton;

    @FindBy(how = How.XPATH, using = ".//div[@class='note note-success']") //<-- Success note (div) after uploading image
    @CacheLookup
    public WebElement noteSuccess;

}
