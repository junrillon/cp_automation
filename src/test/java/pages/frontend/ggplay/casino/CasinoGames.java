package pages.frontend.ggplay.casino;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CasinoGames {
    public CasinoGames(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // gameCard
    @FindBy(how = How.XPATH, using =  ".//div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3'][2]//div[@class='game-card']")
    @CacheLookup
    public WebElement gameCard;

    // gameCard image
    @FindBy(how = How.XPATH, using =  ".//div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3'][2]//div[@class='game-card']//img")
    @CacheLookup
    public WebElement gameCardImage;

    // gameCard image
    @FindBy(how = How.XPATH, using =  ".//div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3'][3]//div[@class='game-card']//img")
    @CacheLookup
    public WebElement liveCasinoBaccarat;

    // gameCard play button
    @FindBy(how = How.XPATH, using =  ".//div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3'][2]//a[@class='btn btn-primary']")
    @CacheLookup
    public WebElement gameCardPlayButton;

    @FindBy(how = How.XPATH, using =  ".//section[@class='game-view']")
    @CacheLookup
    public WebElement maintenanceNotif;

    @FindBy(how = How.XPATH, using =  ".//iframe[@class='w-100 h-100 border-none']")
    @CacheLookup
    public WebElement gameIframe1;

    @FindBy(how = How.XPATH, using =  ".//div[@id='slotContainer']/iframe")
    @CacheLookup
    public WebElement gameIframe2;

    @FindBy(how = How.XPATH, using =  ".//div[@class='loading-screen']")
    @CacheLookup
    public WebElement casinoLoadingScreen;

    @FindBy(how = How.XPATH, using =  ".//div[@class='play-button-transition']/button")
    @CacheLookup
    public WebElement playButton;

    @FindBy(how = How.ID, using =  "stage")
    @CacheLookup
    public WebElement canvasPlayButton;

    @FindBy(how = How.XPATH, using =  ".//div[@class='btn-circle-inner-play-button']")
    @CacheLookup
    public WebElement innerPlayButton;

    @FindBy(how = How.XPATH, using =  ".//div[@class='statistics-field-value']")
    @CacheLookup
    public WebElement balanceValue;

    @FindBy(how = How.XPATH, using =  ".//div[@class='stake-value']")
    @CacheLookup
    public WebElement stakeValue;

    @FindBy(how = How.XPATH, using =  ".//div[@class='win-text']")
    @CacheLookup
    public WebElement winIndicator;

    @FindBy(how = How.XPATH, using =  ".//div[@class='win-text']/canvas")
    @CacheLookup
    public WebElement winIndicator2;

    @FindBy(how = How.XPATH, using =  ".//div[@class='debug-component']//span[@class='close-btn-gui']")
    @CacheLookup
    public List<WebElement> debugComponent;

}
