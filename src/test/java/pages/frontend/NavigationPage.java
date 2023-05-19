package pages.frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class NavigationPage {
    private final WebDriver driver;

    /**
    * Object repository
    */

    //User account name
    @FindBy(how = How.XPATH, using =  ".//span[@class='wallet-balance']")
    public WebElement balanceDisplay;

    //Deposit Button
    @FindBy(how = How.XPATH, using = ".//a[@href='/account/deposit']")
    public WebElement depositButton;

    //Withdraw Button
    @FindBy(how = How.XPATH, using = ".//a[@href='/account/withdraw']")
    public WebElement withdrawButton;

    //Navigation - Fund history button
    @FindBy(how = How.XPATH, using = ".//a[@href='/account/fund-history']")
    public WebElement FundHistoryButton;

    //ul[contains(concat(' ',@class,' '), ' account-balance ')]

    public NavigationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickDepositButton(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Wait for the deposit button and then click
        wait.until(ExpectedConditions.elementToBeClickable(depositButton));
        depositButton.click();
    }

    public void clickWithdrawButton(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Wait for the deposit button and then click
        wait.until(ExpectedConditions.elementToBeClickable(withdrawButton));
        withdrawButton.click();
    }

    public void goToFundHistory(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Wait for the balance and then click
        wait.until(ExpectedConditions.elementToBeClickable(balanceDisplay));
        balanceDisplay.click();

        //Wait for the fund history button and then click
        wait.until(ExpectedConditions.elementToBeClickable(FundHistoryButton));
        FundHistoryButton.click();
    }

}

