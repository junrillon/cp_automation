package pages.Frontend;

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
    @FindBy(how = How.ID, using =  "submit-bet-placeBetTeam1")
    public WebElement selectionA;

    //TEAM B
    @FindBy(how = How.ID, using =  "submit-bet-placeBetTeam2")
    public WebElement selectionB;

    //DRAW
    @FindBy(how = How.ID, using =  "submit-bet-placeBetDraw")
    public WebElement selectionDraw;

    //input amount
    @FindBy(how = How.CSS, using =  "input.form-control")
    public WebElement amountInput;

    //plce bet button
    @FindBy(how = How.XPATH, using =  "(//button[@type='button'])[6]")
    public WebElement clickSubmitBtn;

    //confirm button
    @FindBy(how = How.XPATH, using =  "(//button[@type='button'])[8]")
    public WebElement confirmPlacebet;




    /**
     * Object action
     */
    //Click TEAM A
    public void ClickTeamA() {selectionA.isDisplayed(); selectionA.click();}

    //click TEAM B
    public void ClickTeamB() {selectionB.click();}

    //click pool header button
    public void ClickDraw() {selectionDraw.click();}

    //click pool header button
    public void inputAmount(String matchCountInput) {amountInput.isDisplayed(); amountInput.sendKeys(matchCountInput);}

    //click pool header button
    public void clickPlaceBetBtn() {clickSubmitBtn.click();}

    //click pool header button
    public void clickConfirmPlaceBetBtn() {confirmPlacebet.isDisplayed(); confirmPlacebet.click();}


}
