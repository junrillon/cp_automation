package pages.frontend.sports;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

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
    @FindBy(how = How.XPATH, using = ".//li/child::a[@title=\"Basketball\"]")
    @CacheLookup
    public WebElement BasketballButton;

    //Early Matches Button
    @FindBy(how = How.XPATH, using = ".//li[@data-name=\"Basketball\"]/descendant::span[text()=\"Early\"]/parent::a")
    @CacheLookup
    public WebElement EarlyMatchButton;

    //Team 1 Odds of any ML market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"early-mk\"]/descendant::td[@class=\"col-1x2 col-1x2-0\"]/child::a[@data-team-type=\"1\"]")
    @CacheLookup
    public WebElement TeamAOddsML;

    //Team 2 Odds of any ML market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"early-mk\"]/descendant::td[@class=\"col-1x2 col-1x2-0\"]/child::a[@data-team-type=\"0\"]")
    @CacheLookup
    public WebElement TeamBOddsML;

    //Single Bet Amount Text Field
    @FindBy(how = How.XPATH, using = ".//div[@id=\"betslip-content\"]/descendant::div[@class=\"stake-stretch\"]/child::input")
    @CacheLookup
    public WebElement SinglePinnacleBetAmount;

    //Accept Better Odds Checkbox
    @FindBy(how = How.XPATH, using = ".//label[@class=\"acceptBetterOddsLabel\"]/child::input")
    @CacheLookup
    public WebElement AcceptBetterOdds;

    //Single Place Bet Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"betslip-buttons\"]/child::input[@title=\"Place Bet\"]")
    @CacheLookup
    public WebElement SinglePlaceBetButton;

    //Confirm Place Bet Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"ui-dialog-buttonset\"]/descendant::span[text()=\"OK\"]")
    public WebElement PinnacleSuccessBetOK;

    //Pinnacle Bet Success Message
    @FindBy(how = How.XPATH, using = ".//div[@role=\"dialog\"]/child::div[@id=\"alert\"]")
    @CacheLookup
    public WebElement PinnacleSuccessBet;

    //My Bets Button
    @FindBy(how = How.XPATH, using = ".//div[@id=\"menu-auth\"]/descendant::a[@onclick=\"openAccMyBetFull(); return false;\"]")
    @CacheLookup
    public WebElement PinnacleMyBets;

    //Get Wager ID
    @FindBy(how = How.XPATH, using = ".//div[@class=\"bet even  open \"]/descendant::span[@class=\"wager-id\" and contains(text(),'')]")
    @CacheLookup
    public WebElement BetSlipWagerId;

    //My Bets Wager ID
    @FindBy(how = How.XPATH, using = ".//span[@class=\"wager_id\" and contains(text(),'')]")
    @CacheLookup
    public WebElement MyBetsWagerID;

    /**
     * Parlay Bet Object repository
     */

    //Parlay Tab Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"bs-tab parlay\"]")
    @CacheLookup
    public WebElement ParlayTabButton;

    //In Play Collapsible Tab
    @FindBy(how = How.XPATH, using = ".//div[@class=\"s-tab\"]/h3[text()=\"In-Play Now\"]")
    @CacheLookup
    public WebElement LiveCollapseButton;

    //Sports Collapsible Tab
    @FindBy(how = How.XPATH, using = ".//div[@class=\"s-tab\"]/h3[text()=\"Sports\"]")
    @CacheLookup
    public WebElement SportsCollapseButton;

    //Esports Collapsible Tab
    @FindBy(how = How.XPATH, using = ".//div[@class=\"s-tab\"]/h3[text()=\"Esports\"]")
    @CacheLookup
    public WebElement EsportsCollapseButton;

    //Basketball Sports Parlay Tab
    @FindBy(how = How.XPATH, using = ".//div[@class=\"filter-parlay\"]/descendant::span[text()=\"Basketball\"]")
    @CacheLookup
    public WebElement ParlayBasketballButton;

    //Parlay Team 1 Odds of any ML market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"double-line  \"]/descendant::td[@class=\"col-1x2 col-1x2-0\"]/child::a[@data-team-type=\"0\"]")
    @CacheLookup
    public WebElement ParlayTeamAOddsML;

    //Parlay Team 2 Odds of any ML market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"double-line  \"]/descendant::td[@class=\"col-1x2 col-1x2-0\"]/child::a[@data-team-type=\"1\"]")
    @CacheLookup
    public WebElement ParlayTeamBOddsML;

    //Parlay Team 1 Odds of any Over/Under market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"double-line  \"]/descendant::td[@class=\"col-ou ou-0\"]/descendant::a[@data-team-type=\"0\"]")
    @CacheLookup
    public WebElement ParlayTeamAOddsOU;

    //Parlay Team 2 Odds of any Over/Under market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"double-line  \"]/descendant::td[@class=\"col-ou ou-0\"]/descendant::a[@data-team-type=\"1\"]")
    @CacheLookup
    public WebElement ParlayTeamBOddsOU;

    //Parlay Bet Amount Text Field
    @FindBy(how = How.XPATH, using = ".//div[@class=\"stretch\"]/input")
    @CacheLookup
    public WebElement ParlayPinnacleBetAmount;

    //Parlay Place Bet Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"parlay-bet-title\"]/following-sibling::div[@class=\"betslip-buttons\"]/child::input[@title=\"Place Bet\"]")
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
    @FindBy(how = How.XPATH, using = ".//table[@class=\"events no-select\"]/descendant::td[@class=\"col-hdp teaser-body\"]/descendant::a[@data-team-type=\"1\"]")
    @CacheLookup
    public WebElement TeaserTeamAOddsHDP;

    //Teaser Team 2 Odds of any HDP market
    @FindBy(how = How.XPATH, using = ".//table[@class=\"events no-select\"]/descendant::td[@class=\"col-hdp teaser-body\"]/descendant::a[@data-team-type=\"0\"]")
    @CacheLookup
    public WebElement TeaserTeamBOddsHDP;

    //Teaser Team 1 Odds of any Over/Under market
    @FindBy(how = How.XPATH, using = ".//table[@class=\"events no-select\"]/descendant::td[@class=\"col-ou teaser-body\"]/descendant::a[@data-team-type=\"1\"]")
    @CacheLookup
    public WebElement TeaserTeamAOddsOU;

    //Teaser Team 2 Odds of any Over/Under market
    @FindBy(how = How.XPATH, using = ".//table[@class=\"events no-select\"]/descendant::td[@class=\"col-ou teaser-body\"]/descendant::a[@data-team-type=\"0\"]")
    @CacheLookup
    public WebElement TeaserTeamBOddsOU;

    //Teaser Place Bet Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"teasers-bet-title\"]/following-sibling::div[@class=\"betslip-buttons\"]/child::input[@title=\"Place Bet\"]")
    @CacheLookup
    public WebElement TeaserPlaceBetButton;


}



