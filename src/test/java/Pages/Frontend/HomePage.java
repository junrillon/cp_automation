package Pages.Frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */
    //User account name
    @FindBy(how = How.ID, using =  "navbarDropdownMenuLinkUserAccount")
    public WebElement userAccount;

    //Active session continue button
    @FindBy(how = How.XPATH, using =  "//*/text()[normalize-space(.)='Continue']/parent::*")
    public WebElement alertContinueBtn;

    //pool header button
    @FindBy(how = How.CSS, using =  "#navbarSupportedContent > ul.navbar-nav.mr-auto > li:nth-child(1) > a")
    public WebElement poolHeaderBtn;

    //Select game text
    @FindBy(how = How.ID, using =  "moreGames")
    public WebElement selectGameTxt;


    //test
    @FindBy(how = How.XPATH, using =  "//*[@src = 'https://stage-cdn01.cpp555.com/stage/sports-uploads/uploaded_lol_1623115949.png']")
    public WebElement lolGame;

  //


    /**
     * Object action
     */
    //User account is display
    public void userAccountDisplay() {userAccount.isDisplayed();}

    //click continue button
    public void clickContinueBtn() {alertContinueBtn.click();}

    //click pool header button
    public void clickPoolBtn() {poolHeaderBtn.click();}

    //click pool header button
    public void selectGameTxtDisplay() {selectGameTxt.isDisplayed();}


    //click pool header button
    public void clickLolGame() {lolGame.click();}

}
