package pages.frontend.sports;

import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Altenar {

    public Altenar(WebDriver driver) {
        PageFactory.initElements(driver, this);}

    //Navigation Balance
    @FindBy(how = How.XPATH, using = ".//span[@class='wallet-balance']")
    public WebElement balance;

    //Brasil Frame
    @FindBy(how = How.ID, using = "brasilbet")
    public WebElement altenar;

    //Live Games Tab
    @FindBy(how = How.XPATH, using = ".//div[contains(concat(' ',@class,' '), ' _asb_top-sports-scroller-button ')][2]")
    public WebElement liveTab;

    //------------- LIVE -------------
    //Live Games
    @FindBy(how = How.NAME, using = "asb-livenow")
    public WebElement live_MatchContainer;

    //Live Games Tab
    @FindBy(how = How.NAME, using = "asb-top-sports-tabs")
    public WebElement liveSports;

    //Live Sport
    @FindBy(how = How.XPATH, using = ".//div[@name='asb-livenow']//div[contains(concat(' ',@class,' '), ' _asb_items-tabs-tab ')][1]")
    public WebElement live_Sports;

    //Live Match
    @FindBy(how = How.XPATH, using = ".//div[@name='asb-livenow']//div[contains(concat(' ',@class,' '), ' _asb_events-table-row-event-info ')][1]")
    public WebElement live_Event;

    //Live Match Event
    @FindBy(how = How.XPATH, using = ".//div[@name='asb-livenow']//div[contains(concat(' ',@class,' '), ' _asb_events-table-row-event-info ')]")
    public WebElement live_EventName;

    //------------- END OF LIVE -------------

    //------------- UPCOMING -------------
    //Upcoming Games
    @FindBy(how = How.NAME, using = "asb-upcoming")
    public WebElement upcoming_MatchContainer;

    //Selected Live Sport
    @FindBy(how = How.XPATH, using = ".//div[@name='asb-upcoming']//div[contains(concat(' ',@class,' '), ' _asb_items-tabs-tab ')][1]")
    public WebElement upcoming_Sports;

    //Selected Live match
    @FindBy(how = How.XPATH, using = ".//div[@name='asb-upcoming']//div[contains(concat(' ',@class,' '), ' _asb_events-table-row-event-info ')]")
    public WebElement upcoming_Event;

    //Upcoming Match Event
    @FindBy(how = How.XPATH, using = ".//div[@name='asb-upcoming']//div[contains(concat(' ',@class,' '), ' _asb_events-table-row-event-info ')]")
    public WebElement upcoming_EventName;

    //Upcoming Match Event
    @FindBy(how = How.XPATH, using = ".//div[@name='asb-upcoming']//div[contains(concat(' ',@class,' '), ' _asb_events-table ')]/child::div[2]/div[1]")
    public WebElement upcoming_5thEventName;



    //------------- END OF UPCOMING -------------


    //Market container
    @FindBy(how = How.XPATH, using = ".//div[@name='asb-common-event-details']//div[contains(concat(' ',@class,' '), ' _asb_event-details-markets-group ')]")
    public WebElement marketContainer;

    //Main market odds selection
    @FindBy(how = How.XPATH, using = ".//div[contains(concat(' ',@class,' '), ' _asb_event-details-market ')][1]//div[contains(concat(' ',@class,''), ' _asb_prices-column ')][1]")
    public WebElement oddsSelection;

    //Bet slip selector
    @FindBy(how = How.XPATH, using = ".//div[@title=\"Betslip\"]")
    public WebElement betSlipBtn;

    //My bets
    @FindBy(how = How.XPATH, using = ".//div[@title=\"My Bets\"]")
    public WebElement myBetsBtn;

    //Bet amount input
    @FindBy(how = How.XPATH, using = ".//div[@name='asb-betslip']//input[contains(concat(' ',@class,' '), ' _asb_text-input-input ')]")
    public WebElement betAmountInput;

    //Bet amount input
    @FindBy(how = How.XPATH, using = ".//div[@role='betslip-totals-stake']")
    public WebElement totalStake;


    //Bet slip selection details
    @FindBy(how = How.XPATH, using = ".//div[@name=\"asb-mybets-betslip-selector\"]//div[@class=\"_asb_items-tabs-content \"]")
    public WebElement betSlipSelectionDetails;

    //Bet slip selection details (Empty)
    @FindBy(how = How.XPATH, using = ".//div[@name=\"asb-betslip\"]//div[contains(concat(' ',@class,''), ' _asb_betslip-empty ')]")
    public WebElement emptyBetSlipSelectionDetails;

    //Bet slip selection details (Empty)
    @FindBy(how = How.XPATH, using = ".//div[contains(concat(' ',@class,''), ' _asb_betslip-place-btns-bet ')]")
    public WebElement placeBetBtn;

    //Bet slip selection details (Empty)
    @FindBy(how = How.XPATH, using = ".//div[contains(concat(' ',@class,''), ' _asb_betslip-odds-change-action-select--expansion-panel-header ')]")
    public WebElement oddsChangeSelectAction;

    //Accept any change odds
    @FindBy(how = How.XPATH, using = ".//div[contains(concat(' ',@class,''), ' _asb_betslip-odds-change-action-select--items-radio-item ')][1]")
    public WebElement acceptAnyChangeOdds;

    //Loading after placing bet
    @FindBy(how = How.XPATH, using = ".//div[contains(concat(' ',@class,' '), ' asb-flex-cc _asb_store-status-loader ')]")
    public List<WebElement> loading;

    //Bet slip ID
    @FindBy(how = How.XPATH, using = ".//div[contains(concat(' ',@class,' '), ' _asb_betslip-receipt-item-bet-id ')]")
    public WebElement betSlipId;


    //.//div[contains(concat(' ',@class,' '), ' asb-flex-cc _asb_store-status-loader ')]  <-- laoding icon after place bet

    //asb-flex-col _asb_betslip-receipt <-- betslip receipt
    //.//div[contains(concat(' ',@class,' '), ' _asb_betslip-receipt-item-bet-id ')] <-- betslip ID

    //.//div[contains(concat(' ',@class,' '), ' _asb_event-details-market ')][1] <-- main market
    //.//div[contains(concat(' ',@class,' '), ' _asb_event-details-market ')][1]//div[contains(concat(' ',@class,''), ' _asb_prices-column ')][1]


    //.//div[@name='asb-livenow']//div[contains(concat(' ',@class,' '), ' _asb_events-table-row-markets ')] <-- market display
}

