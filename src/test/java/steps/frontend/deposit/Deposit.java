package steps.frontend.deposit;

import engine.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.frontend.DepositPage;
import pages.frontend.FundHistoryPage;
import pages.frontend.NavigationPage;

public class Deposit {
    private final WebDriver driver;
    private final FundHistoryPage fundHistoryPage;
    private final NavigationPage navigationPage;
    private final DepositPage depositPage;

    public Deposit(Driver driver) {
        this.driver = driver.get();
        this.fundHistoryPage = new FundHistoryPage(this.driver);
        this.navigationPage = new NavigationPage(this.driver);
        this.depositPage = new DepositPage(this.driver);
    }

     static String transId;
     static String depositAmount;
     static String balanceBefore;
     static String rolloverBefore;
     static String bonus;

    @When("I get the current balance")
    public void getCurrentBalance(){
        //Go to fundHistoryPage
        navigationPage.goToFundHistory();

        //Get the balance, bonus and rollover
        balanceBefore = fundHistoryPage.getBalance();
        bonus = fundHistoryPage.getBonus();
        rolloverBefore = fundHistoryPage.getRollover();

        //Assert that the value of each variable are not null
        Assert.assertNotEquals(null, balanceBefore);
        Assert.assertNotEquals(null, bonus);
        Assert.assertNotEquals(null, rolloverBefore);

    }

    @When("I select payment gateway {string}")
    public void selectPaymentGateway(String paymentGateway) throws InterruptedException {
        //select payment gateway
        depositPage.clickAndSelectPaymentGateway(paymentGateway);

        //Assert if the selected payment gateway is same with the desired payment gateway
        String actual = depositPage.getSelectedPaymentGateway();
        Assert.assertEquals(actual, paymentGateway);

        //Check selected payment gateway and then click next button
        depositPage.proceedWithDeposit(paymentGateway);

    }

    @When("I input an amount of {string}")
    public void inputAmount(String amount){
        //Input value in amount field
        depositPage.inputAmount(amount);

        String value = depositPage.getAmountFieldValue();
        Assert.assertNotNull(value);
    }

    @Then("I attach file")
    public void attachFile(){
        //attach file
        depositPage.attachFile();

        //To check if the file is successfully attached
        String uploadedFileName = depositPage.getUploadedFilePath();
        Assert.assertTrue(uploadedFileName.contains("sample attachment.png"));

    }

    @Then("I submit the deposit")
    public void submitDeposit() throws InterruptedException {
        //Click submit button
        depositPage.submitDeposit();

        //Check if success display is displayed
        Assert.assertTrue(depositPage.checkIfSuccessDeposit());

    }

    @Then("I get the transaction Id")
    public void getTransId() throws InterruptedException {
        //Go to fund history
        navigationPage.goToFundHistory();

        //Get the first transId in the fund history page
        transId = fundHistoryPage.getFirstRowTransId();
    }

}
