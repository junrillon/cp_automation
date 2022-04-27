package steps.frontend.fanjuan;
import engine.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.frontend.brasilbet.WithdrawPix;
import pages.frontend.fanjuan.WithdrawXendit;

public class withdrawXenditGcash {

    private WebDriver driver;

    public withdrawXenditGcash(Driver driver) {
        this.driver = driver.get();

    }

    @Given("I click the nav balance dropdown")
    public void clickBalanceDropdown() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WithdrawXendit page = new WithdrawXendit(driver);
        wait.until(ExpectedConditions.visibilityOf(page.walletBalance));
        wait.until(ExpectedConditions.elementToBeClickable(page.walletBalance));
        page.clickWalletBalance();
    }

    @When("I select the withdraw button")
    public void clickWithdrawButton() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WithdrawXendit page = new WithdrawXendit(driver);
        wait.until(ExpectedConditions.visibilityOf(page.withdrawButton));
        wait.until(ExpectedConditions.elementToBeClickable(page.withdrawButton));
        page.clickWithdrawButton();
    }

    @When("I select the saved bank account")
    public void clickBankAccount() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WithdrawXendit page = new WithdrawXendit(driver);
        wait.until(ExpectedConditions.visibilityOf(page.cpfBankButton));
        wait.until(ExpectedConditions.elementToBeClickable(page.cpfBankButton));
        page.clickCpfBank();
    }

    @When("I place the withdraw amount")
    public void inputWithdrawAmount() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WithdrawXendit page = new WithdrawXendit(driver);
        wait.until(ExpectedConditions.visibilityOf(page.inputWithdrawAmount));
        wait.until(ExpectedConditions.elementToBeClickable(page.inputWithdrawAmount));
        page.setInputWithdrawAmount();
    }

    @When("I confirm withdraw")
    public void clickConfirmWithdraw() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WithdrawXendit page = new WithdrawXendit(driver);
        wait.until(ExpectedConditions.visibilityOf(page.confirmButton));
        wait.until(ExpectedConditions.elementToBeClickable(page.confirmButton));
        page.clickConfirmWithdraw();
    }

    @Then("withdrawal is successful")

    public void withdrawSuccess(){

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WithdrawXendit page = new WithdrawXendit(driver);
        wait.until(ExpectedConditions.visibilityOf(page.confirmModal));
        wait.until(ExpectedConditions.elementToBeClickable(page.confirmModal));

        String verifyWithdraw = driver.findElement(By.
                xpath(".//p[text()='Your withdraw request has been submitted successfully. You can check the processing status in the Fund History page.']")).getText();
        Assert.assertEquals("Your withdraw request has been submitted successfully. You can check the processing status in the Fund History page.", verifyWithdraw);
        ///page.clickWithdrawButton();
    }
}