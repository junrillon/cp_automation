package pages.aams;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class Users {

    private WebDriver driver;


    /**
     * Object repository
     */

    @FindBy(how = How.XPATH, using = "//a[@href='/admin/cms/users/create']")
    @CacheLookup
    public WebElement createUserButton;

    @FindBy(how = How.XPATH, using = "//a[@href='/admin/cms/users']")
    @CacheLookup
    public WebElement cancelCreateUserButton;

    @FindBy(how = How.ID, using = "email")
    @CacheLookup
    public WebElement emailField;

    @FindBy(how = How.ID, using = "role")
    @CacheLookup
    public WebElement roleOption;

    @FindBy(how = How.ID, using = "submit_button")
    @CacheLookup
    public WebElement submitButton;



    public Users(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickCreateUserButton(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(createUserButton));
            wait.until(ExpectedConditions.elementToBeClickable(createUserButton)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            clickCreateUserButton();
        }
    }

    public void clickCancelCreateUserButton(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(cancelCreateUserButton));
            wait.until(ExpectedConditions.elementToBeClickable(cancelCreateUserButton)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            clickCancelCreateUserButton();
        }
    }

    public void enterEmailValue(WebElement element, String value) {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(value);

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            enterEmailValue(element, value);
        }
    }


        public void selectRole(String role){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(roleOption));
            wait.until(ExpectedConditions.visibilityOf(roleOption)).click();

            Select select = new Select(roleOption);

            // Iterate over the options and select the matching option (case-insensitive)
            List<WebElement> options = select.getOptions();
            for (WebElement option : options) {
                if (option.getText().toLowerCase().contains(role.toLowerCase())) {
                    option.click();
                    wait.until(ExpectedConditions.visibilityOf(roleOption)).click();
                    break;
                }
            }

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            selectRole(role);
        }
    }

}
