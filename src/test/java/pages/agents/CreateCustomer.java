package pages.agents;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.PageModelBase;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CreateCustomer {
    private final WebDriver driver;
    private final PageModelBase pageModelBase;

    @FindBy(how = How.CSS, using = "#statusMessageDiv p")
    public WebElement statusMessageContainer;

    @FindBy(how = How.XPATH, using = "createUserForm")
    public WebElement createUserForm;

    @FindBy(how = How.XPATH, using = ".//select[@id='masterAgentDropDown']")
    public WebElement masterAgentDropDown;

    @FindBy(how = How.XPATH, using = ".//select[@id='agentDropDown']/option")
    public List<WebElement> masterAgentDropDownOptions;

    @FindBy(how = How.XPATH, using = ".//select[@id='agentDropDown']")
    public WebElement agentDropDown;

    @FindBy(how = How.XPATH, using = ".//select[@id='agentDropDown']/option")
    public List<WebElement> agentDropDownOptions;

    @FindBy(how = How.XPATH, using = ".//select[@id='subAgentDropDown']")
    public WebElement subAgentDropDown;

    @FindBy(how = How.XPATH, using = ".//select[@id='agentDropDown']/option")
    public List<WebElement> subAgentDropDownOptions;

    @FindBy(how = How.ID, using = "agentUsernameInput")
    public WebElement usernameInputField;

    @FindBy(how = How.XPATH, using = ".//div[contains(@class, 'copyToClipboard')]")
    public WebElement copyUsernameButton;

    @FindBy(how = How.XPATH, using = ".//span[@id='tooltip-clipboard']")
    public WebElement copiedUsernameContainer;

    @FindBy(how = How.ID, using = "passwordInput")
    public WebElement passwordInputField;

    @FindBy(how = How.ID, using = "confirmPasswordInput")
    public WebElement confirmPwInputField;

    @FindBy(how = How.ID, using = "amountToTransferInput")
    public WebElement amoutToTransferInputField;

    @FindBy(how = How.ID, using = "submitButton")
    public WebElement submitButton;

    @FindBy(how = How.ID, using = "addtlNameInput")
    public WebElement nameInputField;

    //username input error message
    @FindBy(how = How.ID, using = "agentUsernameInput-error")
    public List<WebElement> usernameErrorMessage;

    @FindBy(how = How.ID, using = "addtlNameInput-error")
    public List<WebElement> nameErrorMessage;

    public CreateCustomer(WebDriver driver) {
        this.driver = driver;
        this.pageModelBase = new PageModelBase(this.driver);
        PageFactory.initElements(driver, this);
    }

    public void checkOptionCount(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Create a Select object with the dropdown element
            Select select = new Select(element);

            // Wait for the options to be present and visible
            wait.until(ExpectedConditions.visibilityOfAllElements(select.getOptions()));

            // Get the initial count of options
            int optionCount = select.getOptions().size();

            // Wait for the count to become more than 2
            while (optionCount < 2) {
                // Refresh the Select object to get the updated options count
                select = new Select(element);
                optionCount = select.getOptions().size();
            }

            Assert.assertTrue(optionCount > 2, "optionCount: " + optionCount);
        } catch (Exception e) {
            // Handle the exception here
            // You can log the exception or perform any other error handling logic
            e.printStackTrace();

            // Recall the method
            checkOptionCount(element);
        }
    }
    public void selectAgent(WebElement element, String agentUsername){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Check if the agentUsername is not null
        if(!agentUsername.equals("null")) {

            //wait for the element to visible before assigning it to a Select element
            wait.until(ExpectedConditions.visibilityOf(element));

            // Create a Select object with the dropdown element
            Select selectAgent = new Select(element);

            //wait for the element to be clickable
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();

            //Check and wait for options to be 2 or more
            checkOptionCount(element);

            // Iterate through the options and select the desired option
            for (WebElement option : selectAgent.getOptions()) {
                if (option.getText().contains(agentUsername)) {
                    selectAgent.selectByVisibleText(option.getText());
                    break;
                }
            }

            WebElement selectedOption = selectAgent.getFirstSelectedOption();
            String actualSelectedOption = selectedOption.getText();

            // Assert if the actual option contains the expected option
            Assert.assertTrue(actualSelectedOption.contains(agentUsername),
                    "Selected option does not contain: " + agentUsername);
        }
    }

    private void generateAndSetNewUsername() {
        String newUsername = generateRandomUsername();

        // Set the new username and a default password
        setUsernamePassword(newUsername, "");
    }

    private String generateRandomUsername() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // Generate a random length between 2 and 3
        int length = random.nextInt(2) + 2;

        for (int i = 0; i < length; i++) {
            // Generate a random lowercase letter
            char randomChar = (char) (random.nextInt(26) + 'a');
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public void checkUsernameErrorMessage() {
        try {
            int errorMessageCount = usernameErrorMessage.size();
            if (errorMessageCount > 0) {
                String errorMessage = usernameErrorMessage.get(0).getText();
                System.out.println("Displayed Error: " + errorMessage);

                // Clear the input field
                pageModelBase.clearInputField(usernameInputField);

                // Call the method to generate and set a new username
                generateAndSetNewUsername();
            }
        } catch (Exception e) {
            // Handle the exception here
            // You can log the exception or perform any other error handling logic
            e.printStackTrace();

            // Recall the method
            checkUsernameErrorMessage();
        }
    }
    public void setUsernamePassword(String username, String password){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //wait for the element to visible and then send key or input value
        wait.until(ExpectedConditions.visibilityOf(usernameInputField)).sendKeys(username);

        //wait for the element to visible and then send key or input value
        wait.until(ExpectedConditions.visibilityOf(passwordInputField)).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOf(confirmPwInputField)).sendKeys(password);
    }

    public String clickCopyUsername(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //wait for the element to visible
        wait.until(ExpectedConditions.visibilityOf(copyUsernameButton));

        //Click the copy username button
        copyUsernameButton.click();

        //return the extracted text from the clipboard
        return element.getText().replace("Copied: ", "");

    }

    public void setAmountToTransfer(String amount){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //wait for the element to visible and then send key or input value
        wait.until(ExpectedConditions.visibilityOf(amoutToTransferInputField)).sendKeys(amount);
    }

    public void setAdditionalInfoName(String name){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //wait for the element to visible and then send key or input value
        wait.until(ExpectedConditions.visibilityOf(nameInputField)).click();
        nameInputField.sendKeys(name);

        // Check if there's an error in input name field.
        checkNameErrorMessage(name);
    }

    public void checkNameErrorMessage(String name) {
        try {
            int errorMessageCount = nameErrorMessage.size();
            if (errorMessageCount > 0) {
                String errorMessage = nameErrorMessage.get(0).getText();
                System.out.println("Displayed Error: " + errorMessage);

                // Clear the input field
                pageModelBase.clearInputField(usernameInputField);

                // Call the method to generate and set a new username
                generateAndSetNewUsername();
            }
        } catch (Exception e) {
            // Handle the exception here
            System.out.println("Exception occurred: " + e.getMessage());

            // Call the setAdditionalInfoName() method
            setAdditionalInfoName(name);
        }
    }
        public void clickSubmitButton(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Will scroll to the element, to make sure it's visible
        pageModelBase.scrollIntoView(submitButton);

        //wait for the element to visible
        wait.until(ExpectedConditions.visibilityOf(submitButton));

        //Click the submit button
        submitButton.click();
    }

    public void getStatusMessage(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //wait for 2 seconds and wait for the element to be visible
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            wait.until(ExpectedConditions.visibilityOf(element));

            //Call the method getStatusMessage() and assign the text to variable statusMessage.
            String statusMessage = element.getText();

            //Assert if user creation is successful
            Assert.assertEquals("Successfully saved the data.", statusMessage);
            System.out.println("Message:" + statusMessage);

        } catch (StaleElementReferenceException e) {
            // Handle StaleElementReferenceException
            System.out.println("StaleElementReferenceException occurred: " + e.getMessage());

        } catch (NoSuchElementException e) {
            // Handle NoSuchElementException
            System.out.println("NoSuchElementException occurred: " + e.getMessage());
        }
    }
}
