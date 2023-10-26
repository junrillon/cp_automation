package pages.aams;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AuditLogin {
    private final WebDriver driver;

    @FindBy(how = How.ID, using = "email")
    public WebElement emailInput;

    @FindBy(how = How.ID, using = "password")
    public WebElement passwordInput;

    @FindBy(how = How.ID, using = "otp")
    public WebElement otpInput;

    @FindBy(how = How.ID, using = "otpButton")
    public WebElement otpButton;

    @FindBy(how = How.ID, using = "showModalBtn")
    public WebElement loginButton;

    @FindBy(how = How.XPATH, using = ".//div[@id='sucessModal']//p")
    public List<WebElement> successMessageModal;

    @FindBy(how = How.XPATH, using = ".//div[@class='container']//div[contains(@class, ' bg-danger ')]//p")
    public List<WebElement> failedMessageModal;

    @FindBy(how = How.XPATH, using = ".//div[@class='container']//div[@id='modalFooter']//button[contains(text(), 'Close')]")
    public WebElement modalCloseButton;

    @FindBy(how = How.XPATH, using = ".//div[@id='modalFooter']//button[contains(text(), 'Proceed')]")
    public WebElement modalProceedButton;

    @FindBy(how = How.XPATH, using = ".//div[contains(@class, 'phpdebugbar ')]")
    public WebElement debugger;

    @FindBy(how = How.XPATH, using = ".//a[@class='phpdebugbar-close-btn']")
    public WebElement debuggerCloseButton;


    public AuditLogin(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void inputCredentials(String email, String password){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Close the debugger
        closeDebugger();

        // Wait for the email and password input field and then send keys.
        wait.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(password);

    }

    public void closeDebugger(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Wait for the debugger to be visible
        wait.until(ExpectedConditions.visibilityOf(debugger));

        // Wait for the debugger button to be visible and clickable then click
        wait.until(ExpectedConditions.visibilityOf(debuggerCloseButton));
        wait.until(ExpectedConditions.elementToBeClickable(debuggerCloseButton)).click();
    }

    public void requestOtp(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Wait for the button to be visible and clickable then click.
        wait.until(ExpectedConditions.visibilityOf(otpButton));
        wait.until(ExpectedConditions.elementToBeClickable(otpButton)).click();

        //Check if requesting otp is success
        checkSuccessMessage();
    }

    public void checkSuccessMessage(){
        int retry = 0;
        int maxRetry = 3;
        String successMessage;

        while (retry <= maxRetry) {
            // Refresh the errorMessage count to get the updated size
            int successMessageCount = successMessageModal.size();

            if (successMessageCount == 1) {
                successMessage = successMessageModal.get(0).getText();
                System.out.println(successMessage);
                break;
            }

            // Optionally, add a delay between each iteration to avoid excessive polling
            try {
                Thread.sleep(1000); // Delay 1 second (adjust as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //increment the retry value
            retry++;
        }

        // Close the modal
        closeModal();
    }

    public void checkErrorMessage() {
        int retry = 0;
        int maxRetry = 3;
        String errorMessage;

        while (retry <= maxRetry) {
            // Refresh the errorMessage count to get the updated size
            int errorMessageCount = failedMessageModal.size();

            if (errorMessageCount == 1) {
                errorMessage = failedMessageModal.get(0).getText();
                System.out.println(errorMessage);

                //Check the error message
                if (errorMessage.equals("There is an active session, Do you want to proceed?")) {
                    // Click the proceed button
                    clickProceedButton();
                } else {
                    // Close the modal
                    closeModal();
                }

                break;
            }

            // Optionally, add a delay between each iteration to avoid excessive polling
            try {
                Thread.sleep(1000); // Delay 1 second (adjust as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //increment the retry value
            retry++;
        }
    }

    public void checkOtpInputValue(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        String currentOtpValue = wait.until(ExpectedConditions.visibilityOf(otpInput))
                                    .getAttribute("value");

        while (currentOtpValue.isEmpty()) {
            // Refresh the errorMessageCount to get the updated size
            currentOtpValue = otpInput.getAttribute("value");

            // Optionally, you can add a delay between each iteration to avoid excessive polling
            try {
                Thread.sleep(1000); // Adjust the delay time as needed (in milliseconds)
            } catch (InterruptedException e) {
                // Handle the InterruptedException if needed
                e.printStackTrace();
            }

            System.out.println("Otp: " + currentOtpValue);
        }
    }
    public void clickLogin(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Check if otp input field is blank
        checkOtpInputValue();

        // Wait for the button to be visible and clickable then click.
        wait.until(ExpectedConditions.visibilityOf(loginButton));
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

        //Check if there's an error
        checkErrorMessage();

    }
    public void closeModal(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Wait for the button to be visible and clickable then click.
        wait.until(ExpectedConditions.visibilityOf(modalCloseButton));
        wait.until(ExpectedConditions.elementToBeClickable(modalCloseButton)).click();
    }

    public void clickProceedButton(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Wait for the button to be visible and clickable then click.
        wait.until(ExpectedConditions.visibilityOf(modalProceedButton));
        wait.until(ExpectedConditions.elementToBeClickable(modalProceedButton)).click();
    }

}
