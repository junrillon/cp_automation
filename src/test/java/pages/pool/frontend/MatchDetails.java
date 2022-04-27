package pages.pool.frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
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
    @CacheLookup
    public WebElement selectionA;

    //TEAM B
    @FindBy(how = How.ID, using =  "submit-bet-placeBetTeamB")
    @CacheLookup
    public WebElement selectionB;

    //DRAW
    @FindBy(how = How.ID, using =  "submit-bet-placeBetDraw")
    @CacheLookup
    public WebElement selectionDraw;

    //input amount //*[@id="placeBetTeamA"]/input
    @FindBy(how = How.XPATH, using =  ".//input[@placeholder='Enter Amount']")
    @CacheLookup
    public WebElement amountInputTeamA;

    //input amount //*[@id="placeBetTeamb"]/input
    @FindBy(how = How.XPATH, using =  ".//div[@id='placeBetTeamB']//input[@placeholder='Enter Amount']")
    @CacheLookup
    public WebElement amountInputTeamB;

    //input amount //*[@id="placeBetTeamdraw"]/input
    @FindBy(how = How.XPATH, using =  ".//div[@id='placeBetDraw']//input[@placeholder='Enter Amount']")
    @CacheLookup
    public WebElement amountInputDraw;

    //plce bet button //*[@id="placeBetDraw"]/div/div/div[3]/button
    @FindBy(how = How.XPATH, using =  ".//button[@class='btn btn-primary btn-block' and contains(text(),' Place Bet')]")
    @CacheLookup
    public WebElement clickSubmitBtnTeamA;

    //plce bet button //*[@id="placeBetDraw"]/div/div/div[3]/button
    @FindBy(how = How.XPATH, using =  ".//*[@id='placeBetTeamB']//button[@class='btn btn-primary btn-block' and contains(text(),' Place Bet')]")
    @CacheLookup
    public WebElement clickSubmitBtnTeamB;

    //plce bet button //*[@id="placeBetDraw"]/div/div/div[3]/button
    @FindBy(how = How.XPATH, using =  ".//*[@id='placeBetDraw']//button[@class='btn btn-primary btn-block' and contains(text(),' Place Bet')]")
    @CacheLookup
    public WebElement clickSubmitBtnDraw;

    //confirm button //*[@id="confirmBetTeamA"]/div/div/div[2]/div[3]/button //*[@id="confirmBetTeamA"]/div/div/div[2]/div[3]/button/text()
    @FindBy(how = How.XPATH, using =  ".//*[@id='confirmBetTeamA']//button[@class='btn btn-primary btn-block']")
    @CacheLookup
    public WebElement confirmPlacebetTeamA;

    //confirm button //*[@id="confirmBetTeamA"]/div/div/div[2]/div[3]/button //*[@id="confirmBetTeamA"]/div/div/div[2]/div[3]/button/text()
    @FindBy(how = How.XPATH, using =  ".//*[@id='confirmBetTeamB']//button[@class='btn btn-primary btn-block']")
    @CacheLookup
    public WebElement confirmPlacebetTeamB;

    //confirm button //*[@id="confirmBetTeamA"]/div/div/div[2]/div[3]/button //*[@id="confirmBetTeamA"]/div/div/div[2]/div[3]/button/text()
    @FindBy(how = How.XPATH, using =  ".//*[@id='confirmBetDraw']//button[@class='btn btn-primary btn-block']")
    @CacheLookup
    public WebElement confirmPlacebetDraw;

    // odds
    @FindBy(how = How.XPATH, using = ".//div[@id='placeBetTeamA']/preceding-sibling::div[@class='bet-details small']/div/div[2]")
    @CacheLookup
    public WebElement teamAOdds;

    @FindBy(how = How.XPATH, using = ".//div[@id='placeBetTeamB']/preceding-sibling::div[@class='bet-details small']/div/div[2]")
    @CacheLookup
    public WebElement teamBOdds;

    @FindBy(how = How.XPATH, using = ".//div[@id='placeBetDraw']/preceding-sibling::div[@class='bet-details small']/child::*/div[2][@class]")
    @CacheLookup
    public WebElement drawOdds;

    @FindBy(how = How.XPATH, using = ".//span[@class='badge badge-primary rounded-pill pending-bets-total']")
    @CacheLookup
    public WebElement betSlipCount;

    @FindBy(how = How.XPATH, using = ".//div[@class='small font-weight-bold' and contains(text()[3],' Cancelled')]")
    @CacheLookup
    public WebElement cancelledBroadcast;

    @FindBy(how = How.XPATH, using = ".//div[@class='small font-weight-bold' and contains(text()[3],' Winner:')]")
    @CacheLookup
    public WebElement winBroadcast;


}
