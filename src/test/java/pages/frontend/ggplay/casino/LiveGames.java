package pages.frontend.ggplay.casino;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LiveGames {
    public LiveGames(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // gameCard
    @FindBy(how = How.XPATH, using =  ".//div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3'][8]//div[@class='game-card']")
    @CacheLookup
    public WebElement gameCard;

    // gameCard image
    @FindBy(how = How.XPATH, using =  ".//div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3'][8]//div[@class='game-card']//img")
    @CacheLookup
    public WebElement gameCardImage;

    // Lobby Iframe
    @FindBy(how = How.XPATH, using =  ".//iframe[@title=\"game\"]")
    @CacheLookup
    public WebElement evoLobbyIframe;


    // gameCard play button
    @FindBy(how = How.XPATH, using =  ".//div[@class='col-xl-2 col-lg-3 col-sm-4 col-4 mb-3'][8]//a[@class='btn btn-primary']")
    @CacheLookup
    public WebElement gameCardPlayButton;

    @FindBy(how = How.XPATH, using =  ".//section[@class='game-view']")
    @CacheLookup
    public WebElement maintenanceNotif;

//    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'loading-screen ') and contains(@style,'display: flex;')]")
//    public WebElement casinoLoadingScreen;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'loading-screen ') and contains(@style,'display: flex;')]")
    public List<WebElement> casinoLoadingScreen;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), ' finish-progress')]")
    public List<WebElement> casinoFinishProgress;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'wrap-inner ListLobbyTablesInner')]")
    @CacheLookup
    public WebElement tableList;

    @FindBy(how = How.XPATH, using =  ".//section[@id='category-recently_played']")
    @CacheLookup
    public WebElement recentlyPlayed;

    @FindBy(how = How.XPATH, using =  ".//div[@id='category-grid-recently_played']//li")
    @CacheLookup
    public WebElement firstRecentlyPlayedGame;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'lobby-table-block')][14]")
    @CacheLookup
    public WebElement baccaratA;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'balance')]//span[contains(concat(' ',@class,' '), 'amount')]//span[2]")
    public WebElement inGameBalance;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'totalBet')]//div[contains(concat(' ',@class,' '), 'textContainer')]//span")
    public WebElement inGameTotalBetWinLabel;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'totalBet')]//span[contains(concat(' ',@class,' '), 'amount')]//span[2]")
    public WebElement inGameTotalBet;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'timerAndResult')]//div")
    public WebElement timer;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'liveStatisticsContainer')]//div[contains(concat(' ',@class,' '), 'currency')]//span[2]")
    public WebElement totalBet;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'bettingGrid')]//div[contains(concat(' ',@class,' '), 'player')]")
    public List<WebElement> playerSelection;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'player')]//div[contains(concat(' ',@class,' '), 'title')]")
    public WebElement playerSelectionLabel;
    //div[contains(concat(' ',@class,' '), 'bettingGrid')]//div[@data-betspot-destination="Player"]

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'bettingGrid')]//div[contains(concat(' ',@class,' '), 'banker')]")
    public WebElement bankerSelection;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'bettingGrid')]//div[contains(concat(' ',@class,' '), 'tie')]")
    public WebElement tieSelection;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'bettingGrid')]//div[contains(concat(' ',@class,' '), 'dragon')][1]")
    public List<WebElement>  dragonSelection;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'dragon')]//div[contains(concat(' ',@class,' '), 'title')]")
    public WebElement dragonSelectionLabel;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'chipStack')]")
    public WebElement betAmountContainer;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'expandedChipStack')]")
    public WebElement betAmountSelection;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'chipItem')][1]//div[contains(concat(' ',@class,' '), 'chip')]")
    public WebElement minBetAmount;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'gameResult')]//div[contains(concat(' ',@class,' '), 'gameResultElements')]")
    public List<WebElement> gameResultElements;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'winner')]//span")
    public List<WebElement> gameWinner;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'gameResult')]//div[contains(concat(' ',@class,' '), 'message')]")
    public List<WebElement> gameResultMessageContainer;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'gameResult')]//div[contains(concat(' ',@class,' '), 'message')]//div[1]")
    public List<WebElement> gameResultMessage;

    @FindBy(how = How.XPATH, using =  ".//div[contains(concat(' ',@class,' '), 'message')]//div[contains(concat(' ',@class,' '), 'amount')]")
    public WebElement winnings;


}
