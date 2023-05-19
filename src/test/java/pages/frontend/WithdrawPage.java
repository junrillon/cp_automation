package pages.frontend;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WithdrawPage {
    private final WebDriver driver;

    @FindBy(how = How.XPATH, using = ".//div[contains(@class, 'withdraw-modal')]//div[@class='modal-content']")
    public WebElement dimmedBody;

    //Add account button
    @FindBy(how = How.XPATH, using = ".//div[@id='add-account']")
    public WebElement addAccountButton;

    @FindBy(how = How.XPATH, using = ".//div[contains(@class, 'withdraw-modal')]")
    public WebElement withdrawModal;
    //div[contains(@class, 'add-account') and contains(@class, 'withdraw-modal')]

    @FindBy(how = How.XPATH, using = ".//div[@class='payment-option']//select")
    public WebElement partnerBankDropdown;

    @FindBy(how = How.XPATH, using = ".//input[@name='accountNickname']")
    public WebElement nicknameInputField;

    @FindBy(how = How.XPATH, using = ".//input[@name='accountNumber']")
    public WebElement accountNumberInputField;

    @FindBy(how = How.XPATH, using = ".//div[@class='payment-details']//button")
    public WebElement addAccountSubmitButton;

    @FindBy(how = How.XPATH, using = ".//div[contains(@class, 'alert-success')]/p")
    public List<WebElement> addAccountSuccessAlert;

    @FindBy(how = How.XPATH, using = ".//div[contains(@class, 'saved-account')]//div[@class='col-2']")
    public List<WebElement> savedAccount;

    @FindBy(xpath = ".//div[@class='col-2']//span[contains(text(), '%s')]")
    public WebElement dynamicSavedAccount;

    @FindBy(how = How.XPATH, using = ".//div[contains(@class, 'withdraw-modal')]//input[@inputmode='decimal']")
    public WebElement withdrawAmountField;

    @FindBy(how = How.XPATH, using = ".//div[contains(@class, 'withdraw-modal')]//button[contains(@class, 'btn-success')]")
    public WebElement withdrawSubmitButton;


    public WithdrawPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean hasSavedBankAccounts(){
        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.visibilityOfAllElements(savedAccount));
        return savedAccount.size() > 1;
    }

    public void clickAddBankAccount(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //wait for the saved account to be clickable then click
        wait.until(ExpectedConditions.elementToBeClickable(addAccountButton));
        addAccountButton.click();
    }

    public boolean isAddBankAccountSuccess(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //wait for size of the element to be 1
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(".//div[contains(@class, 'alert-success')]/p"), 1));
        return addAccountSuccessAlert.size() > 0;
    }

    public void fillUpAddAccountModal(String partnerBank, String nickname, String accountNumber){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Will wait for the add account modal to be visible
        wait.until(ExpectedConditions.visibilityOf(withdrawModal));

        //select the preferred partner bank
        Select paymentGatewayDrp = new Select(partnerBankDropdown);
        paymentGatewayDrp.selectByVisibleText(partnerBank);

        //Input value in nickname field
        nicknameInputField.sendKeys(nickname);

        //Input value in account number field
        accountNumberInputField.sendKeys(accountNumber);

        //Will wait for the submit button to be clickable and click
        wait.until(ExpectedConditions.visibilityOf(addAccountSubmitButton));
        addAccountSubmitButton.click();

    }

    public void clickSavedBankAccount(String searchTerm) {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //wait for the saved account to become visible
        dynamicSavedAccount = driver.findElement(By.xpath(String.format(".//div[@class='col-2']//span[contains(text(), '%s')]", searchTerm)));
        wait.until(ExpectedConditions.visibilityOf(dynamicSavedAccount));

        //wait for the saved account to be clickable then click
        wait.until(ExpectedConditions.elementToBeClickable(dynamicSavedAccount));
        dynamicSavedAccount.click();

    }

    public void inputAmount(int amount) {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Will wait for the add withdraw modal to be visible
        wait.until(ExpectedConditions.visibilityOf(withdrawModal));

        //Wait for the element to be visible then input amount
        wait.until(ExpectedConditions.visibilityOf(withdrawAmountField));
        withdrawAmountField.sendKeys(String.valueOf(amount));

    }

    public String getInputtedAmount() {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Will wait for the add withdraw modal to be visible
        wait.until(ExpectedConditions.visibilityOf(withdrawModal));

        //Wait for the element to be visible then input amount
        wait.until(ExpectedConditions.visibilityOf(withdrawAmountField));
        String amount = withdrawAmountField.getAttribute("value");

        return amount;
    }

}
