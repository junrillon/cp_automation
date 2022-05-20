package pages.frontend.sports;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Pinnacle {
    public Pinnacle(WebDriver driver) {PageFactory.initElements(driver, this);}

    /**
     * Object repository
     */

    //Sports Header
    @FindBy(how = How.XPATH, using = ".//a[@class='nav-link' and contains(text(),\"Sports\")]")
    @CacheLookup
    public WebElement SportsHeaderBtn;

    //Iframe Pinnacle
    @FindBy(how = How.XPATH, using = ".//iframe[@class]")
    @CacheLookup
    public WebElement iFramePinnacle;

    /**
     * Single Bet Object repository
     */

    //Basketball Button
    @FindBy(how = How.XPATH, using = ".//li//a[@title=\"Basketball\"]")
    @CacheLookup
    public WebElement BasketballButton;

    //Early Matches Button
    @FindBy(how = How.XPATH, using = ".//li[@data-name=\"Basketball\"]//span[text()=\"Early\"]/parent::a")
    @CacheLookup
    public WebElement EarlyMatchButton;

    //Team 1 Odds of any ML market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"early-mk\"]//td[@class=\"col-1x2 col-1x2-0\"]//a[@data-team-type=\"0\"]")
    public WebElement TeamAOddsML;

    //Team 2 Odds of any ML market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"early-mk\"]//td[@class=\"col-1x2 col-1x2-0\"]//a[@data-team-type=\"1\"]")
    public WebElement TeamBOddsML;

    //Single Bet Amount Text Field
    @FindBy(how = How.XPATH, using = ".//div[@id=\"betslip-content\"]//div[@class=\"stake-stretch\"]//input")
    @CacheLookup
    public WebElement SinglePinnacleBetAmount;

    //Accept Better Odds Checkbox
    @FindBy(how = How.XPATH, using = ".//label[@class=\"acceptBetterOddsLabel\"]//input")
    @CacheLookup
    public WebElement AcceptBetterOdds;

    //Single Place Bet Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"betslip-buttons\"]//input[@title=\"Place Bet\"]")
    @CacheLookup
    public WebElement SinglePlaceBetButton;

    //Confirm Place Bet Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"ui-dialog-buttonset\"]//span[text()=\"OK\"]")
    public WebElement PinnacleSuccessBetOK;

    //Pinnacle Bet Success Message
    @FindBy(how = How.XPATH, using = ".//div[@role=\"dialog\"]//div[@id=\"alert\"]")
    @CacheLookup
    public WebElement PinnacleSuccessBet;

    //My Bets Button
    @FindBy(how = How.XPATH, using = ".//div[@id=\"menu-auth\"]//a[@onclick=\"openAccMyBetFull(); return false;\"]")
    @CacheLookup
    public WebElement PinnacleMyBets;

    //Get Wager ID
    @FindBy(how = How.XPATH, using = ".//div[@class=\"bet even  open \"]//span[@class=\"wager-id\" and contains(text(),'')]")
    @CacheLookup
    public WebElement BetSlipWagerId;

    //My Bets Wager ID
    @FindBy(how = How.XPATH, using = ".//span[@class=\"wager_id\" and contains(text(),'')]")
    public WebElement MyBetsWagerID;

    //My Bets Wager ID Location
    @FindBy(how = How.XPATH, using = ".//span[@class=\"wager_id\" and contains(text(),'')]")
    public List<WebElement> WagerIDLocation;

    /**
     * Parlay Bet Object repository
     */

    //Loading Element
    @FindBy(how = How.XPATH, using = ".//div[@class=\"full-loading\"]")
    @CacheLookup
    public WebElement Loading;

    //Parlay Tab Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"bs-tab parlay\"]")
    public WebElement ParlayTabButton;

    //In Play Collapsible Tab
    @FindBy(how = How.XPATH, using = ".//div[@class=\"s-tab\"]/h3[text()=\"In-Play Now\"]")
    @CacheLookup
    public WebElement LiveCollapseButton;

    //Sports Collapsible Tab
    @FindBy(how = How.XPATH, using = ".//div[@class=\"s-tab\"]/h3[text()=\"Sports\"]")
    @CacheLookup
    public WebElement SportsCollapseButton;

    //Sports Location Tab
    @FindBy(how = How.XPATH, using = ".//div[@class=\"s-tab\"]/h3[text()=\"Sports\"]")
    public List<WebElement> SportsTab;

    //Esports Collapsible Tab
    @FindBy(how = How.XPATH, using = ".//div[@class=\"s-tab\"]/h3[text()=\"Esports\"]")
    @CacheLookup
    public WebElement EsportsCollapseButton;

    //Basketball Sports Parlay Tab
    @FindBy(how = How.XPATH, using = ".//div[@class=\"filter-parlay\"]//span[text()=\"Basketball\"]")
    @CacheLookup
    public WebElement ParlayBasketballButton;

    //Parlay Team 1 Odds of any ML market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"double-line  \"]//td[@class=\"col-1x2 col-1x2-0\"]//a[@data-team-type=\"1\"]")
    public WebElement ParlayTeamAOddsML;

    //Parlay Team 2 Odds of any ML market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"double-line  \"]//td[@class=\"col-1x2 col-1x2-0\"]//a[@data-team-type=\"0\"]")
    public WebElement ParlayTeamBOddsML;

    //Parlay Team 1 Odds of any Over/Under market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"double-line  \"]//td[@class=\"col-ou ou-0\"]//a[@data-team-type=\"1\"]")
    @CacheLookup
    public WebElement ParlayTeamAOddsOU;

    //Parlay Team 2 Odds of any Over/Under market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"double-line  \"]//td[@class=\"col-ou ou-0\"]//a[@data-team-type=\"0\"]")
    @CacheLookup
    public WebElement ParlayTeamBOddsOU;

    //Parlay Bet Amount Text Field
    @FindBy(how = How.XPATH, using = ".//div[@class=\"stretch\"]/input")
    @CacheLookup
    public WebElement ParlayPinnacleBetAmount;

    //Parlay Place Bet Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"parlay-bet-title\"]/following-sibling::div[@class=\"betslip-buttons\"]//input[@title=\"Place Bet\"]")
    @CacheLookup
    public WebElement ParlayPlaceBetButton;

    /**
     * Teaser Bet Object repository
     */

    //Teaser Tab Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"bs-tab teaser\"]")
    @CacheLookup
    public WebElement TeaserTabButton;

    //Teaser Team 1 Odds of any HDP market
    @FindBy(how = How.XPATH, using = ".//table[@class=\"events no-select\"]//td[@class=\"col-hdp teaser-body\"]//a[@data-team-type=\"1\"]")
    public WebElement TeaserTeamAOddsHDP;

    //Teaser Team 2 Odds of any HDP market
    @FindBy(how = How.XPATH, using = ".//table[@class=\"events no-select\"]//td[@class=\"col-hdp teaser-body\"]//a[@data-team-type=\"0\"]")
    public WebElement TeaserTeamBOddsHDP;

    //Teaser Team 1 Odds of any Over/Under market
    @FindBy(how = How.XPATH, using = ".//table[@class=\"events no-select\"]//td[@class=\"col-ou teaser-body\"]//a[@data-team-type=\"1\"]")
    public WebElement TeaserTeamAOddsOU;

    //Teaser Team 2 Odds of any Over/Under market
    @FindBy(how = How.XPATH, using = ".//table[@class=\"events no-select\"]//td[@class=\"col-ou teaser-body\"]//a[@data-team-type=\"0\"]")
    public WebElement TeaserTeamBOddsOU;

    //Teaser Place Bet Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"teasers-bet-title\"]/following-sibling::div[@class=\"betslip-buttons\"]//input[@title=\"Place Bet\"]")
    @CacheLookup
    public WebElement TeaserPlaceBetButton;


}



