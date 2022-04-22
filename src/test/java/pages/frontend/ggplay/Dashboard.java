package pages.frontend.ggplay;

import engine.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    public WebElement walletBalance;

    //Active session continue button
    @FindBy(how = How.XPATH, using =  "//*/text()[normalize-space(.)='Continue']/parent::*")
    public WebElement alertContinueBtn;

    //pool header button
    @FindBy(how = How.XPATH, using =  ".//a[@class='nav-link' and contains(text(),\"Esports\")]")
    public WebElement EsportsHeaderBtn;

    //Select game text
    @FindBy(how = How.ID, using =  "moreGames")
    public WebElement selectGameTxt;


    //test
    @FindBy(how = How.XPATH, using =  ".//div[@class='small' and contains(text(),'Automation Sports')]")
    public WebElement TestSport;

    //Cancel more game modal
    @FindBy(how = How.ID, using =  "modalClose")
    public WebElement cancelMoreGamesModal;

 //confirm button
 @FindBy(how = How.XPATH, using =  ".//iframe[@class]")
 public WebElement iFramePool;

    //confirm button
    @FindBy(how = How.XPATH, using =  ".//button[@class='MuiButtonBase-root MuiButton-root MuiButton-text MuiButton-textPrimary']")
    public WebElement noLivegames;


}
