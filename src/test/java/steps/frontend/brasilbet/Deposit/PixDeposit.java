package steps.frontend.brasilbet.Deposit;

import engine.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.hu.De;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageModelBase;
import pages.frontend.brasilbet.Deposit;
import pages.frontend.brasilbet.FundHistory;
import pages.frontend.brasilbet.WithdrawPix;

public class PixDeposit {
    private WebDriver driver;
    public pages.PageModelBase PageModelBase;

    public static String transId;

    public PixDeposit(pages.PageModelBase PageModelBase, Driver driver) {
        this.driver = driver.get();
        this.PageModelBase = PageModelBase;

    }

    @When("I click the deposit button")
    public void clickDepositButton() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        Deposit page = new Deposit(driver);

        wait.until(ExpectedConditions.visibilityOf(page.depositButton));
        wait.until(ExpectedConditions.elementToBeClickable(page.depositButton))
                .click();
    }

    @When("I select payment gateway")
    public void selectPaymentGateway() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        Deposit page = new Deposit(driver);

        wait.until(ExpectedConditions.visibilityOf(page.paymentGatewayDropdown));
        wait.until(ExpectedConditions.elementToBeClickable(page.paymentGatewayDropdown)).click();

        Select paymentGateway = new Select(page.paymentGatewayDropdown);
        paymentGateway.selectByVisibleText("PIX");

    }

    @When("I input an amount of ([^\"]*)$")
    public void inputAmount(String amount) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        Deposit page = new Deposit(driver);

        wait.until(ExpectedConditions.visibilityOf(page.amountField))
                .sendKeys(amount);
    }

    @Then("I submit the deposit")
    public void submitDeposit(){

        WebDriverWait wait = new WebDriverWait(driver, 20);
        Deposit page = new Deposit(driver);

        //Wait for 'submit button' to be visible then click
        wait.until(ExpectedConditions.visibilityOf(page.submitButton));
        wait.until(ExpectedConditions.elementToBeClickable(page.submitButton))
                .click();

        //Wait for 'go to fund history button' to be visible then click
        wait.until(ExpectedConditions.visibilityOf(page.fundHistory));
        wait.until(ExpectedConditions.elementToBeClickable(page.fundHistory))
                .click();

    }

    @Then("I get the transaction Id")
    public void getTransId() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        FundHistory page = new FundHistory(driver);

        //Wait for the table to be visible
        wait.until(ExpectedConditions.visibilityOf(page.fundHistoryTable));

        transId = page.firstRowTransId.getText();
        System.out.println(transId);

        Thread.sleep(3000);
    }
}
