package pages.frontend.brasilbet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class WithdrawPix {
    public WithdrawPix(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //User Balance
    @FindBy(how = How.XPATH, using =  ".//a[@class='nav-link animate-pulse account-balance']")
    public WebElement walletBalance;

    //Withdraw Button
    @FindBy(how = How.XPATH, using = ".//a[@href='/account/withdraw']")
    public WebElement withdrawButton;

    //CPF Bank
    @FindBy(how = How.XPATH, using = ".//span[@class='small']")
    public WebElement cpfBankButton;

    //Text input for withdraw amount
    @FindBy(how = How.XPATH, using = ".//input[@class='form-control form-group text-center']")
    public WebElement inputWithdrawAmount;

    //Confirm Button
    @FindBy(how = How.XPATH, using = ".//button[@class='btn btn-block btn-success mt-4']")
    public WebElement confirmButton;


    public void clickWalletBalance() {
        walletBalance.click();
    }

    public void clickWithdrawButton() {
        withdrawButton.click();
    }

    public void clickCpfBank() {
        cpfBankButton.click();
    }

    public void setInputWithdrawAmount(){
        inputWithdrawAmount.sendKeys("5");
    }

    public void clickConfirmWithdraw() {
        confirmButton.click();
    }
}


