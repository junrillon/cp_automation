package pages.frontend.ggplay;

import engine.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Dashboard {


   public Dashboard(WebDriver driver) {
    
       PageFactory.initElements(driver, this);



    }
    
    /**
     * Object repository
     */
    //User account name
    @FindBy(how = How.XPATH, using =  ".//span[@class='wallet-balance' and contains(text(),',')]")
    @CacheLookup
    public WebElement walletBalance;

    //Active session continue button
    @FindBy(how = How.XPATH, using =  "//*/text()[normalize-space(.)='Continue']/parent::*")
    @CacheLookup
    public WebElement alertContinueBtn;

    //pool header button
    @FindBy(how = How.XPATH, using =  ".//a[@class='nav-link' and contains(text(),\"Esports\")]")
    @CacheLookup
    public WebElement EsportsHeaderBtn;

    //Select game text
    @FindBy(how = How.ID, using =  "moreGames")
    @CacheLookup
    public WebElement selectGameTxt;


    //test
    @FindBy(how = How.XPATH, using =  ".//div[@class='small' and contains(text(),'Automation Sports')]")
    @CacheLookup
    public WebElement TestSport;

    //Cancel more game modal
    @FindBy(how = How.ID, using =  "modalClose")
    @CacheLookup
    public WebElement cancelMoreGamesModal;

    //confirm button
    @FindBy(how = How.XPATH, using =  ".//iframe[@class]")
    @CacheLookup
    public WebElement iFramePool;

    //confirm button
    @FindBy(how = How.XPATH, using =  ".//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text MuiButton-textPrimary']")
    @CacheLookup
    public WebElement noLivegames;

 //confirm button
 @FindBy(how = How.ID, using =  "navbarDropdownMenuLinkUserAccount")
 public WebElement tcxtUsername;


}

