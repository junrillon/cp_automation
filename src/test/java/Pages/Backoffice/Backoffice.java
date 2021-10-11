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

    @FindBy(how = How.XPATH, using = "//button[@type='submit']")
    public WebElement loginButton;


    public void inputCredentials(String usernameAdmin,String passwordAdmin){
        username.isDisplayed();
        password.isDisplayed();
        username.sendKeys(usernameAdmin);
        password.sendKeys(passwordAdmin);
    }

    public void clickLoginButton(){
        loginButton.isDisplayed();
        loginButton.click();
    }

    @FindBy(how = How.XPATH, using = "//span[@class='username btn btn-default']")
    public WebElement adminUsername;

    public void adminUsernameDisplay(){
        adminUsername.isDisplayed();
    }
    //a[@id='nav_Casino']
    @FindBy(how = How.XPATH, using = "//li[@class='menu-dropdown classic-menu-dropdown '][5]")
    public WebElement navCasino;

    @FindBy(how = How.XPATH, using = "//a[@id='nav_Game List Management']")
    public WebElement navCasino_GameListManagement;

    public void navigateToGamelist(){
        navCasino.isDisplayed();
        navCasino.click();
        navCasino_GameListManagement.isDisplayed();
        navCasino_GameListManagement.click();
    }

    // ----- Start of filters
    @FindBy(how = How.XPATH, using = "//div[@class='filter']//input[@title='Game Name']") //<-- Game name filter
    public WebElement gameNameFilter;

    @FindBy(how = How.XPATH, using = "//select[@id='casinoRoom']") //<-- Casino room filter
    public WebElement casinoRoomFilter;

    @FindBy(how = How.XPATH, using = "//div[@class='actions']//button[@id='filterButton']") //<-- Apply filter button
    public WebElement applyFilterButton;
    // ----- END of filters


    @FindBy(how = How.XPATH, using = "//div[@class='page-title']/h1")
    public WebElement pageTitle;

    @FindBy(how = How.XPATH, using = "//ul[@class='casino-room-accordion']")
    public WebElement providerSelection;

    @FindBy(how = How.XPATH, using = "//ul[@class='inner show']/li/a/span") //ul[@class='inner show']/li/a/span[@class='game-provider-accordion']
    public WebElement providerInnerSelection;

    @FindBy(how = How.XPATH, using = "//div[@class='modal-dialog modal-sm']//button[@class='close']")
    public WebElement modalCloseButton;

    @FindBy(how = How.XPATH, using = "//table[@id='gameListTable']") //<-- Game list table
    public WebElement gameListTable;

    @FindBy(how = How.XPATH, using = "//div[@class='game-image']") //<-- Game Image
    public WebElement gameImage;

    @FindBy(how = How.XPATH, using = "//button[@class='btn btn-primary btn-xs upload-btn']") //<-- Upload Image Button
    public WebElement uploadImageButton;

    @FindBy(how = How.XPATH, using = "//form[@id='form-upload']") //<-- Upload Image Modal
    public WebElement uploadImageForm;

    @FindBy(how = How.XPATH, using = "//form[@id='form-upload']//button[@class='close']") //<-- Upload Image modal close button
    public WebElement uploadImageFormCloseButton;

    @FindBy(how = How.XPATH, using = "//input[@id='input-game_image']") //<-- Choose file Button | /input[@type='file' and @id='input-game_image']
    public WebElement chooseFileButton;

    @FindBy(how = How.XPATH, using = "//button[@id='action-save-banner']") //<-- Upload Image Save button
    public WebElement saveButton;

    @FindBy(how = How.XPATH, using = "//div[@class='note note-success']") //<-- Success note (div) after uploading image
    public WebElement noteSuccess;
}
