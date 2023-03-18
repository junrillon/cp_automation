package steps.frontend.brasilbet;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.frontend.brasilbet.WithdrawPix;


public class withdrawPixCpf {

    private WebDriver driver;

    public withdrawPixCpf(Driver driver) {
        this.driver = driver.get();

    }

    @Given("I click the balance dropdown")
        public void clickBalanceDropdown() throws InterruptedException {

            WebDriverWait wait = new WebDriverWait(driver, 20);
            WithdrawPix page = new WithdrawPix(driver);
            wait.until(ExpectedConditions.visibilityOf(page.walletBalance));
            wait.until(ExpectedConditions.elementToBeClickable(page.walletBalance));
            page.clickWalletBalance();

    }

    @When("I click the withdraw button")
       public void clickWithdrawButton() throws InterruptedException {

            WebDriverWait wait = new WebDriverWait(driver, 20);
            WithdrawPix page = new WithdrawPix(driver);
            wait.until(ExpectedConditions.visibilityOf(page.withdrawButton));
            wait.until(ExpectedConditions.elementToBeClickable(page.withdrawButton));
            page.clickWithdrawButton();
    }

    @When("I click the saved bank account")
    public void clickBankAccount() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WithdrawPix page = new WithdrawPix(driver);
        wait.until(ExpectedConditions.visibilityOf(page.cpfBankButton));
        wait.until(ExpectedConditions.elementToBeClickable(page.cpfBankButton));
        page.clickCpfBank();
    }

    @When("I input the withdraw amount")
    public void inputWithdrawAmount() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WithdrawPix page = new WithdrawPix(driver);
        wait.until(ExpectedConditions.visibilityOf(page.inputWithdrawAmount));
        wait.until(ExpectedConditions.elementToBeClickable(page.inputWithdrawAmount));
        page.setInputWithdrawAmount();
    }

    @When("I confirm my withdraw")
    public void clickConfirmWithdraw() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WithdrawPix page = new WithdrawPix(driver);
        wait.until(ExpectedConditions.visibilityOf(page.confirmButton));
        wait.until(ExpectedConditions.elementToBeClickable(page.confirmButton));
        page.clickConfirmWithdraw();
    }

    @Then("withdrawal success")

    public void withdrawSuccess(){

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WithdrawPix page = new WithdrawPix(driver);
        ///page.clickWithdrawButton();
    }
}

