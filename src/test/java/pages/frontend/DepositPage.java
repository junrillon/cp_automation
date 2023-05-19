package pages.frontend;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;

public class DepositPage {
    private final WebDriver driver;

    //Payment Gateway Dropdown
    @FindBy(how = How.XPATH, using = ".//div[@class='deposit-withdraw-page']//select")
    public WebElement paymentGatewayDropdown;

    //Amount Input Field
    @FindBy(how = How.XPATH, using = ".//div[@class='deposit-withdraw-page']//input")
    public WebElement amountField;

    //Next button
    @FindBy(how = How.XPATH, using = ".//div[@class='deposit-withdraw-page']//button[contains(text(), 'Next')]")
    public WebElement nextButton;

    //Submit Button
    @FindBy(how = How.XPATH, using = ".//div[@class='deposit-withdraw-page']//button[contains(concat(' ',@class,' '), 'btn-success')]")
    public WebElement submitButton;

    //Success DepositPage -> Go to fund history button
    @FindBy(how = How.XPATH, using = ".//div[@class='deposit-success']//a[@href='/account/fund-history']")
    public WebElement depositPageFundHistory;

    //Attach deposit slip
    @FindBy(how = How.CSS, using = "div.dropzone > input[type='file'][multiple]")
    public WebElement dropFiles;

    //DepositPage success display
    @FindBy(how = How.XPATH, using = ".//div[@class='deposit-success']")
    public List<WebElement> depositSuccessDisplay;

    public DepositPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickAndSelectPaymentGateway(String selectedPaymentGateway){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //wait for the payment gateway dropdown and then click
        wait.until(ExpectedConditions.elementToBeClickable(paymentGatewayDropdown));
        paymentGatewayDropdown.click();

        //select the payment gateway
        Select paymentGatewayDrp = new Select(paymentGatewayDropdown);
        paymentGatewayDrp.selectByVisibleText(selectedPaymentGateway);
    }

    public void proceedWithDeposit(String selectedPaymentGateway){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        if(!selectedPaymentGateway.equalsIgnoreCase("PIX")){
            wait.until(ExpectedConditions.elementToBeClickable(nextButton));
            nextButton.click();
        }
    }

    public void clickNextButton(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        nextButton.click();
    }

    public String getSelectedPaymentGateway(){
        WebElement selectElement = paymentGatewayDropdown;

        // Get the current value of the select element then return
        return selectElement.getAttribute("data-bankname");
    }

    public void inputAmount(String amount){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Wait for the amount field and then input amount
        wait.until(ExpectedConditions.visibilityOf(amountField));
        amountField.sendKeys(amount);
    }

    public String getAmountFieldValue(){
        WebElement selectElement = amountField;

        // Get the current value of the select element then return
        return selectElement.getAttribute("value");
    }

    public String getFilePath(){
        File file = new File("C:\\Users\\QA02\\IdeaProjects\\cp_automation\\src\\main\\resources\\images\\sample attachment.png");
        return file.getAbsolutePath();
    }

    public void attachFile(){
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //Wait and click next button
        clickNextButton();

        //Locate the element where we attach file, then change the style into 'block' in order to interact with it.
        js.executeScript("arguments[0].style.display = 'block';", dropFiles);

        //and then we sendKeys the location of the file we want to attach.
        String filePath = getFilePath();
        dropFiles.sendKeys(filePath);

    }

    public String getUploadedFilePath(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //to get the uploaded file name
        WebElement actualFile = wait.until(ExpectedConditions.visibilityOf(dropFiles));
        return actualFile.getAttribute("value");
    }

    public void submitDeposit(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Wait for 'submit button' to be visible then click
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();

    }

    public boolean checkIfSuccessDeposit() throws InterruptedException {
        Thread.sleep(2000);
        return !depositSuccessDisplay.isEmpty();
    }

}

