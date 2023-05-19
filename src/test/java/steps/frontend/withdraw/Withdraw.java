package steps.frontend.withdraw;

import engine.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.frontend.DepositPage;
import pages.frontend.FundHistoryPage;
import pages.frontend.NavigationPage;
import pages.frontend.WithdrawPage;

public class Withdraw {
    private final WebDriver driver;
    private final FundHistoryPage fundHistoryPage;
    private final NavigationPage navigationPage;
    private final DepositPage depositPage;
    private final WithdrawPage withdrawPage;
    private final PageModelBase pageModelBase;

    public Withdraw(Driver driver) {
        this.driver = driver.get();
        this.fundHistoryPage = new FundHistoryPage(this.driver);
        this.navigationPage = new NavigationPage(this.driver);
        this.depositPage = new DepositPage(this.driver);
        this.withdrawPage = new WithdrawPage(this.driver);
        this.pageModelBase = new PageModelBase(this.driver);
    }

    static String partnerBank;
    static String nickname;
    static String accountNumber;

    @When("I add withdraw account")
    public void addWithdrawAccount() throws InterruptedException {
        Thread.sleep(2000);

        partnerBank = "Gcash";
        nickname = "Sample Gcash";
        accountNumber = "65497423412";

        //will check if already has a bank account
        boolean withExistingAccount = withdrawPage.hasSavedBankAccounts();
        if(withExistingAccount){
            System.out.println("Already have existing bank account.");

        } else {
            //Will add a bank account if it has no existing bank account
            System.out.println("Creating a bank account.");

            //Click add bank button
            withdrawPage.clickAddBankAccount();

            //Fill up the add bank modal
            withdrawPage.fillUpAddAccountModal(partnerBank, nickname, accountNumber);

            //Assert if adding bank account is success
            Assert.assertTrue(withdrawPage.isAddBankAccountSuccess());
        }

    }

    @Then("I click the saved account")
    public void clickSavedAccount() throws InterruptedException {
        //wait for the add account modal to close
        pageModelBase.waitForModalToClose(withdrawPage.withdrawModal, 10);

        //Click the saved account
        withdrawPage.clickSavedBankAccount(nickname);

        //Assert if withdraw form modal is open.
        boolean isOpen = pageModelBase.waitForModalToOpen(withdrawPage.withdrawModal, 10);
        Assert.assertTrue(isOpen);

    }

    @Then("I input amount to withdraw {int}")
    public void inputAmountToWithdraw(int amount) throws InterruptedException {
        //Click the saved account
        withdrawPage.inputAmount(amount);

        //Assert if amount is equal
        String expectedAmount = withdrawPage.getInputtedAmount();
        Assert.assertEquals(expectedAmount, String.valueOf(amount));

    }


}
