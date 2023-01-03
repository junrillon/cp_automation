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
import pages.frontend.GeneralDashboard;
import pages.frontend.Navigation;
import pages.frontend.brasilbet.Deposit;
import pages.frontend.brasilbet.FundHistory;
import pages.frontend.brasilbet.WithdrawPix;
import steps.frontend.Actions;

public class PixDeposit {
    private final WebDriver driver;
    public steps.frontend.Actions Actions;

    public PixDeposit(steps.frontend.Actions Actions, Driver driver) {
        this.driver = driver.get();
        this.Actions = Actions;

    }

     static String transId;
     static String depositAmount;
     static String balanceBefore;
     static String rolloverBefore;
     static String bonus;

    @When("I get the current balance")
    public void getCurrentBalance(){
        //Go to fundHistory
        Actions.goToFundHistory();

        //Get the balance, bonus and rollover
        balanceBefore = steps.frontend.Actions.getBalance();
        bonus = steps.frontend.Actions.getBonus();
        rolloverBefore = steps.frontend.Actions.getRollover();

    }

    @When("I click the deposit button")
    public void clickDepositButton() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        Navigation page = new Navigation(driver);

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

        depositAmount = amount;
        System.out.println("Deposit amount: " + depositAmount);
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
        wait.until(ExpectedConditions.visibilityOf(page.depositPageFundHistory));
        wait.until(ExpectedConditions.elementToBeClickable(page.depositPageFundHistory))
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
    }

}
