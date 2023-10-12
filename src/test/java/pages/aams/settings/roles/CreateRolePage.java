package pages.aams.settings.roles;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageModelBase;

import java.util.List;

public class CreateRolePage {
    private final WebDriver driver;
    private final PageModelBase baseAction;

    @FindBy(how = How.ID, using = "create_form")
    public WebElement createForm;

    @FindBy(how = How.ID, using = "name")
    public WebElement roleNameInput;

    @FindBy(how = How.ID, using = "description")
    public WebElement descriptionInput;

    @FindBy(how = How.ID, using = "status")
    public WebElement statusDropdown;

    @FindBy(how = How.ID, using = "submit_button_create")
    public WebElement submitButton;

    @FindBy(how = How.XPATH, using = ".//a[contains(text(), 'Cancel')]")
    public WebElement cancelButton;

    @FindBy(how = How.XPATH, using = ".//a[normalize-space()='View Roles']")
    public WebElement viewPagesButton;

    @FindBy(how = How.ID, using = "modal_body")
    public List<WebElement> modalBody;

    @FindBy(how = How.ID, using = "modal_message")
    public List<WebElement> modalMessage;

    @FindBy(how = How.XPATH, using = ".//div[@id='modalFooter']//button[contains(text(), 'Close')]")
    public WebElement modalCloseButton;

    public CreateRolePage(WebDriver driver) {
        this.driver = driver;
        this.baseAction = new PageModelBase(this.driver);
        PageFactory.initElements(driver, this);
    }

    public void checkModalMessage(){
        int retry = 0;
        int maxRetry = 3;
        String message = "";

        while (retry <= maxRetry) {
            // Refresh the errorMessage count to get the updated size
            int modalCount = modalBody.size();

            if (modalCount == 1) {
                // Verify the visibility of the modal message element
                WebDriverWait wait = new WebDriverWait(driver, 20);
                WebElement visibleModalMessage = wait.until(ExpectedConditions.visibilityOf(modalMessage.get(0)));

                // Get the text of the modal message
                message = visibleModalMessage.getText();
                System.out.println(message);

                //Check the error message
                if (message.contains("already been taken") || message.contains("is required")) {

                    // Click the proceed button
                    baseAction.clickButton(modalCloseButton);

                } else {
                    // Close the modal
                    baseAction.clickButton(modalCloseButton);
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

    public void enableViewAccess(String pageName) {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Generate dynamic XPath expression using the 'name' parameter
        String xpathExpression = ".//td[text()='"+pageName+"']/following-sibling::td//input[@class='form-check-input' and @name='view_access[]']";

        try {
            WebElement viewAccessRadioButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));

            // Scroll to the element
            baseAction.scrollIntoView(viewAccessRadioButton);

            // Wait for a small delay (e.g., 300 milliseconds)
            Thread.sleep(300);

            // Click on the element
            viewAccessRadioButton.click();

        } catch (NoSuchElementException | InterruptedException e) {
            // Handle exception if the element is not found
            System.out.println("Failed to locate view access radio button: " + e.getMessage());
        }
    }

    public void disableEditAccess(String pageName) {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Generate dynamic XPath expression using the 'name' parameter
        String xpathExpression = ".//td[text()='"+pageName+"']/following-sibling::td//input[@class='form-check-input' and @name='edit_access[]']";

        try {
            WebElement editAccessRadioButton =  wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));

            // Scroll to the element
            baseAction.scrollIntoView(editAccessRadioButton);

            // Click on the element
            editAccessRadioButton.click();

        } catch (NoSuchElementException e) {
            // Handle exception if the element is not found
            System.out.println("Failed to locate view access radio button: " + e.getMessage());
        }
    }
}
