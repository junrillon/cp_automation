package pages.frontend.sports;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PinnaclePage {
    public PinnaclePage(WebDriver driver) {PageFactory.initElements(driver, this);}

    /**
     * Object repository
     */

    //Sports Header
    @FindBy(how = How.XPATH, using = ".//a[@class='nav-link' and contains(text(),\"Sports\")]")
    public WebElement SportsHeaderBtn;

    //Iframe PinnaclePage
    @FindBy(how = How.XPATH, using = ".//iframe[@title='sports']")
    public WebElement iFramePinnacle;

    //Sports menu
    @FindBy(how = How.ID, using = "oddspage")
    public WebElement oddspage1;

    /**
     * Single Bet Object repository
     */
    //Early Matches Button
    @FindBy(how = How.ID, using = "oddspage")
    public List<WebElement> oddspage;

    //Basketball Button
    @FindBy(how = How.XPATH, using = ".//div[@id='sports-menu']//li//a[@title=\"Basketball\"]")
    public WebElement BasketballButton;

    //Early Matches Button
    @FindBy(how = How.XPATH, using = ".//li[@data-name=\"Basketball\"]//li[@title='Early']//span[text()=\"Early\"]")
    public WebElement EarlyMatchButton;

    //Early Matches Button
    @FindBy(how = How.XPATH, using = ".//div[@class='league']")
    public List<WebElement> sportLeague;

    //Team 1 Odds of any ML market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"early-mk\"]//td[@class=\"col-1x2 col-1x2-0\"]//a[@data-team-type=\"0\"]")
    public WebElement TeamAOddsML;

    //Team 2 Odds of any ML market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"early-mk\"]//td[@class=\"col-1x2 col-1x2-0\"]//a[@data-team-type=\"1\"]")
    public WebElement TeamBOddsML;

    //Single Bet Amount Text Field
    @FindBy(how = How.XPATH, using = ".//div[@id=\"betslip-content\"]//input[@name=\"stake\"]")

    public WebElement SinglePinnacleBetAmount;

    //Accept Better Odds Checkbox
    @FindBy(how = How.XPATH, using = ".//label[@class=\"acceptBetterOddsLabel\"]//input")
    public WebElement AcceptBetterOdds;

    //Single Place Bet Button
    @FindBy(how = How.XPATH, using = ".//div[@id=\"betslip-content\"]//input[@title=\"Place Bet\"]")
    public WebElement SinglePlaceBetButton;

    //Confirm Place Bet Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"ui-dialog-buttonset\"]//span[text()=\"OK\"]")
    public WebElement alertOkButton;

    //PinnaclePage Bet Success Message
    @FindBy(how = How.ID, using = "alert")
    public List<WebElement> alert;

    //My Bets Button
    @FindBy(how = How.XPATH, using = ".//div[@id=\"menu-auth\"]//a[@onclick=\"openAccMyBetFull(); return false;\"]")
    public WebElement PinnacleMyBets;

    //Get Wager ID
    @FindBy(how = How.XPATH, using = ".//span[@class=\"wager-id\"]")
    public WebElement BetSlipWagerId;

    //New Tab - My bets table
    @FindBy(how = How.ID, using = "wagers-container")
    public List<WebElement> myBetsTable;

    //My Bets Wager ID Location
    @FindBy(how = How.XPATH, using = ".//span[@class=\"wager_id\" and contains(text(),'')]")
    public List<WebElement> betDetail_WagerId;

    //My Bets Submit Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"form-group\"]/button[@type=\"submit\"]")
    public WebElement MyBetsSubmit;

    /**
     * Parlay Bet Object repository
     */

    //Loading Element
    @FindBy(how = How.XPATH, using = ".//div[@class=\"full-loading\"]")
    public WebElement Loading;

    //Parlay Tab Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"bs-tab parlay\"]")
    public WebElement ParlayTabButton;

    //In Play Collapsible Tab
    @FindBy(how = How.XPATH, using = ".//div[@class=\"s-tab\"]/h3[text()=\"In-Play Now\"]")
    public WebElement LiveCollapseButton;

    //Sports Collapsible Tab
    @FindBy(how = How.XPATH, using = ".//div[@id='sports-menu']//h3[text()=\"Sports\"]")
    public WebElement SportsCollapseButton;

    //Sports Location Tab
    @FindBy(how = How.XPATH, using = ".//div[@id='sports-menu']//div[contains(concat(' ',@class,' '), ' tab-content ')]")
    public List<WebElement> SportsTab;

    //Esports Collapsible Tab
    @FindBy(how = How.XPATH, using = ".//div[@class=\"s-tab\"]/h3[text()=\"Esports\"]")
    public WebElement EsportsCollapseButton;

    //Basketball Sports Parlay Tab
    @FindBy(how = How.XPATH, using = ".//div[@class=\"filter-parlay\"]//span[text()=\"Basketball\"]")
    public WebElement ParlayBasketballButton;

    //Parlay Team 1 Odds of any ML market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"double-line  \"]//td[@class=\"col-1x2 col-1x2-0\"]//a[@data-team-type=\"1\"]")
    public WebElement ParlayTeamAOddsML;

    //Parlay Team 2 Odds of any ML market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"double-line  \"]//td[@class=\"col-1x2 col-1x2-0\"]//a[@data-team-type=\"0\"]")
    public WebElement ParlayTeamBOddsML;

    //Parlay Team 1 Odds of any Over/Under market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"double-line  \"]//td[@class=\"col-ou ou-0\"]//a[@data-team-type=\"1\"]")
    public WebElement ParlayTeamAOddsOU;

    //Parlay Team 2 Odds of any Over/Under market
    @FindBy(how = How.XPATH, using = ".//div[@class=\"double-line  \"]//td[@class=\"col-ou ou-0\"]//a[@data-team-type=\"0\"]")
    public WebElement ParlayTeamBOddsOU;

    //Parlay Bet Amount Text Field
    @FindBy(how = How.XPATH, using = ".//div[@class=\"stretch\"]/input")
    public WebElement ParlayPinnacleBetAmount;

    //Parlay Place Bet Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"parlay-bet-title\"]/following-sibling::div[@class=\"betslip-buttons\"]//input[@title=\"Place Bet\"]")
    public WebElement ParlayPlaceBetButton;

    /**
     * Teaser Bet Object repository
     */

    //Teaser Tab Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"bs-tab teaser\"]")
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
    public WebElement TeaserPlaceBetButton;


}



