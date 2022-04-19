package pages.pool.frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;


public class MatchDetails {
    public MatchDetails(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */
    //TEAM A
    @FindBy(how = How.ID, using =  "submit-bet-placeBetTeamA")
    public WebElement selectionA;

    //TEAM B
    @FindBy(how = How.ID, using =  "submit-bet-placeBetTeamB")
    public WebElement selectionB;

    //DRAW
    @FindBy(how = How.ID, using =  "submit-bet-placeBetDraw")
    public WebElement selectionDraw;

    //input amount //*[@id="placeBetTeamA"]/input
    @FindBy(how = How.XPATH, using =  ".//input[@placeholder='Enter Amount']")
    public WebElement amountInput;

    //plce bet button
    @FindBy(how = How.XPATH, using =  ".//button[@class='btn btn-primary btn-block' and contains(text(),' Place Bet')]")
    public WebElement clickSubmitBtn;

    //confirm button //*[@id="confirmBetTeamA"]/div/div/div[2]/div[3]/button
    @FindBy(how = How.XPATH, using =  "//*[@id=\"confirmBetTeamA\"]/div/div/div[2]/div[3]/button")
    public WebElement confirmPlacebet;

    // odds
    @FindBy(how = How.XPATH, using = ".//div[@id='placeBetTeamA']/preceding-sibling::div[@class='bet-details small']/div/div[2]")
    public WebElement teamAOdds;

    @FindBy(how = How.XPATH, using = ".//div[@id='placeBetTeamB']/preceding-sibling::div[@class='bet-details small']/div/div[2]")
    public WebElement teamBOdds;

    @FindBy(how = How.XPATH, using = ".//div[@id='placeBetDraw']/preceding-sibling::div[@class='bet-details small']/child::*/div[2][@class]")
    public WebElement drawOdds;

    @FindBy(how = How.XPATH, using = ".//span[@class='badge badge-primary rounded-pill pending-bets-total']")
    public WebElement betSlipCount;

    @FindBy(how = How.XPATH, using = ".//div[@class='small font-weight-bold' and contains(text()[3],' Cancelled')]")
    public WebElement cancelledBroadcast;




    /**
     * Object action
     */
    //Click TEAM A
    public void ClickTeamA() {selectionA.click();}

    //click TEAM B
    public void ClickTeamB() {selectionB.click();}

    //click pool header button
    public void ClickDraw() {selectionDraw.click();}

    //click pool header button
    public void inputAmount(String matchCountInput) { amountInput.sendKeys(matchCountInput);}

    //click pool header button
    public void clickPlaceBetBtn() {clickSubmitBtn.click();}

    //click pool header button
    public void clickConfirmPlaceBetBtn() {confirmPlacebet.click();}

    //get odds team a
    public String getTeamAOdds() {String teamA = teamAOdds.getText();return teamA;}

    //get odds team b
    public String getTeamBOdds() {String teamB = teamBOdds.getText();return teamB;}

    //get odds draw
    public String getDrawOdds() {String draw = drawOdds.getText();return draw;}


}
