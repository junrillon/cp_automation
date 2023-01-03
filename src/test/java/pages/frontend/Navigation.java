package pages.frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;


public class Navigation {


   public Navigation(WebDriver driver) {
    
       PageFactory.initElements(driver, this);

    }
    
    /**
     * Object repository
     */
    //User account name
    @FindBy(how = How.XPATH, using =  ".//span[@class='wallet-balance']")
    public WebElement balance;

    //Navigation - Fund history button
    @FindBy(how = How.XPATH, using = ".//a[@href='/account/fund-history']")
    public WebElement FundHistory;

    //Deposit Button
    @FindBy(how = How.XPATH, using = ".//a[@href='/account/deposit']")
    public WebElement depositButton;

}

