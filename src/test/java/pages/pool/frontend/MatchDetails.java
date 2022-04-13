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


}
