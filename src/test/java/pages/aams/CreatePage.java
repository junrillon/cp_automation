package pages.aams;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CreatePage {
    private final WebDriver driver;

    @FindBy(how = How.ID, using = "create_form")
    public WebElement createForm;

    @FindBy(how = How.ID, using = "name")
    public WebElement pageNameInput;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'pages/create')]")
    public WebElement createPageButton;

    @FindBy(how = How.ID, using = "page_path")
    public WebElement pagePathInput;

    @FindBy(how = How.ID, using = "status")
    public WebElement statusDropdown;

    @FindBy(how = How.ID, using = "submit_button_create")
    public WebElement submitButton;

    @FindBy(how = How.XPATH, using = ".//a[contains(text(), 'Cancel')]")
    public WebElement cancelButton;

    @FindBy(how = How.XPATH, using = ".//a[normalize-space()='View Pages']")
    public WebElement viewPagesButton;

    @FindBy(how = How.ID, using = "modal_body")
    public List<WebElement> modalBody;

    @FindBy(how = How.ID, using = "modal_message")
    public List<WebElement> modalMessage;

    @FindBy(how = How.XPATH, using = ".//div[@id='modalFooter']//button[contains(text(), 'Close')]")
    public WebElement modalCloseButton;

    public CreatePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterValue(WebElement element, String value){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(value);

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            enterValue(element, value);
        }

    }

    public void clickButton(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.visibilityOf(element)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            clickButton(element);
        }
    }

    public void selectStatus(String status){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(statusDropdown));
            wait.until(ExpectedConditions.visibilityOf(statusDropdown)).click();

            Select select = new Select(statusDropdown);

            // Iterate over the options and select the matching option (case-insensitive)
            List<WebElement> options = select.getOptions();
            for (WebElement option : options) {
                if (option.getText().toLowerCase().contains(status.toLowerCase())) {
                    option.click();
                    break;
                }
            }

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            selectStatus(status);
        }
    }

    public void closeModal(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Wait for the button to be visible and clickable then click.
        wait.until(ExpectedConditions.visibilityOf(modalCloseButton));
        wait.until(ExpectedConditions.elementToBeClickable(modalCloseButton)).click();
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
                    closeModal();

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
}
