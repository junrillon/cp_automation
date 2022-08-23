package pages.frontend.brasilbet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Deposit {
    public Deposit(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //User Balance
    @FindBy(how = How.XPATH, using =  ".//ul[@class='nav-link animate-pulse account-balance']")
    public WebElement walletBalance;

    //Deposit Button
    @FindBy(how = How.XPATH, using = ".//a[@href='/account/deposit']")
    public WebElement depositButton;

    //Payment Gateway Dropdown
    @FindBy(how = How.XPATH, using = ".//div[@class='deposit-withdraw-page']//select")
    public WebElement paymentGatewayDropdown;

    //Amount Input Field
    @FindBy(how = How.XPATH, using = ".//div[@class='deposit-withdraw-page']//input")
    public WebElement amountField;

    //Submit Button
    @FindBy(how = How.XPATH, using = ".//div[@class='deposit-withdraw-page']//button[contains(concat(' ',@class,' '), 'btn-success')]")
    public WebElement submitButton;

    //Success Deposit -> Go to fund history button
    @FindBy(how = How.XPATH, using = ".//div[@class='deposit-success']//a[@href='/account/fund-history']")
    public WebElement fundHistory;


}

