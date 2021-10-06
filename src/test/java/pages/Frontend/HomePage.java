package pages.Frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage {
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Object repository
     */
    //User account name
    @FindBy(how = How.XPATH, using =  "//div[@id='navbarSupportedContent']/ul[2]/li/a/span")
    public WebElement walletBalance;

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
    @FindBy(how = How.XPATH, using =  "//*[@src = 'https://cdn01.cpp555.com/prod/sports-uploads/uploaded__1631775777.jpeg']")
    public WebElement TestSport;

    //Cancel more game modal
    @FindBy(how = How.ID, using =  "modalClose")
    public WebElement cancelMoreGamesModal;

  //


    /**
     * Object action
     */


    //User account is display
    public String walletBalance() {
         String wallet = walletBalance.getText();return wallet;
    }

    //click continue button
    public void clickContinueBtn() {alertContinueBtn.click();}

    //click pool header button
    public void clickPoolBtn() {poolHeaderBtn.click();}

    //click pool header button
    public void selectGameTxtDisplay() {selectGameTxt.isDisplayed();selectGameTxt.click();}

    //click pool header button
    public void clickTestSport() {TestSport.click();}

}
