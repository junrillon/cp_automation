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

    //Basketball Button
    @FindBy(how = How.XPATH, using = ".//li/child::a[@title=\"Basketball\"]")
    @CacheLookup
    public WebElement BasketballButton;

    //Early Matches Button
    @FindBy(how = How.XPATH, using = ".//li[@data-name=\"Basketball\"]/descendant::span[text()=\"Early\"]/parent::a")
    @CacheLookup
    public WebElement EarlyMatchButton;

    //Team 1 Odds of any ML market
    @FindBy(how = How.XPATH, using = ".//span[@class=\"bg-sport \"]/ancestor::div[@class=\"odds-container odds-container-nolive container-normal\"]/descendant::td[@class=\"col-1x2 col-1x2-0\"]/child::a[@data-team-type=\"1\"]")
    @CacheLookup
    public WebElement TeamAOddsML;

    //Team 2 Odds of any ML market
    @FindBy(how = How.XPATH, using = ".//span[@class=\"bg-sport \"]/ancestor::div[@class=\"odds-container odds-container-nolive container-normal\"]/descendant::td[@class=\"col-1x2 col-1x2-0\"]/child::a[@data-team-type=\"0\"]")
    @CacheLookup
    public WebElement TeamBOddsML;

    //Bet Amount Text Field
    @FindBy(how = How.XPATH, using = ".//div[@id=\"betslip-content\"]/descendant::div[@class=\"stake-stretch\"]/child::input")
    @CacheLookup
    public WebElement PinnacleBetAmount;

    //Accept Better Odds Checkbox
    @FindBy(how = How.XPATH, using = ".//label[@class=\"acceptBetterOddsLabel\"]/child::input")
    @CacheLookup
    public WebElement AcceptBetterOdds;

    //Place Bet Button
    @FindBy(how = How.XPATH, using = ".//div[@class=\"betslip-buttons\"]/child::input[@title=\"Place Bet\"]")
    @CacheLookup
    public WebElement PinnaclePlaceBetButton;

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






}



