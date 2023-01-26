package pages.backoffice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class GameList {
    private WebDriver driver;

    public GameList(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // ----- Start of filters
    @FindBy(how = How.XPATH, using = ".//div[@class='filter']//input[@title='Game Name']") //<-- Game name filter
    public WebElement gameNameFilter;

    @FindBy(how = How.ID, using = "casinoRoom") //<-- Casino room filter
    public WebElement casinoRoomFilter;

    @FindBy(how = How.XPATH, using = ".//button[@id='filterButton']") //<-- Apply filter button
    public WebElement applyFilterButton;
    // ----- END of filters

    @FindBy(how = How.XPATH, using = ".//div[@class='page-title']/h1")
    public WebElement pageTitle;

    @FindBy(how = How.XPATH, using = ".//ul[@class='casino-room-accordion']")
    public WebElement providerSelection;

    @FindBy(how = How.XPATH, using = ".//ul[@class='inner show']/li/a/span") //ul[@class='inner show']/li/a/span[@class='game-provider-accordion']
    public WebElement providerInnerSelection;

    @FindBy(how = How.XPATH, using = ".//div[@class='modal-dialog modal-sm']//button[@class='close']")
    public WebElement modalCloseButton;

    @FindBy(how = How.XPATH, using = ".//div[@id='modal-loading' and contains(@style,'display: block;')]//div[@class=\"modal-content\"]")
    public List<WebElement> gDetailsModal;

    @FindBy(how = How.XPATH, using = ".//div[@id='modal-loading' and contains(@style,'display: block;')]//div[@class=\"modal-content\"]")
    public WebElement WE_gDetailsModal;
    //.//div[@id='modal-loading' and contains(@style,'display: block;')]

    @FindBy(how = How.ID, using = "gameListTable") //<-- Game list table
    public WebElement gameListTable;

    @FindBy(how = How.XPATH, using = ".//div[@class='game-image']") //<-- Game Image
    public WebElement gameImage;

    @FindBy(how = How.XPATH, using = ".//button[@class='btn btn-primary btn-xs upload-btn']") //<-- Upload Image Button
    public WebElement uploadImageButton;

    @FindBy(how = How.ID, using = "form-upload") //<-- Upload Image Modal
    public WebElement uploadImageForm;

    @FindBy(how = How.XPATH, using = ".//form[@id='form-upload']//button[@class='close']") //<-- Upload Image modal close button
    public WebElement uploadImageFormCloseButton;

    @FindBy(how = How.ID, using = "input-game_image") //<-- Choose file Button | /input[@type='file' and @id='input-game_image']
    public WebElement chooseFileButton;

    @FindBy(how = How.ID, using = "action-save-banner") //<-- Upload Image Save button
    public WebElement saveButton;

    @FindBy(how = How.XPATH, using = ".//div[@class='note note-success']") //<-- Success note (div) after uploading image
    public WebElement noteSuccess;

    @FindBy(how = How.XPATH, using = ".//table[@id='gameListTable']//td[@class='dataTables_empty']")
    public WebElement dataTableEmpty; //empty table

}
