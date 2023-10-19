package pages.aams.management.users;

import org.openqa.selenium.By;
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
    public WebElement createUserButton;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, '/admin/cms/users/edit')]")
    public WebElement editUserButton;

    @FindBy(how = How.XPATH, using = ".//a[contains(text(), 'Cancel')]")
    public WebElement cancelCreateUserButton;

    @FindBy(how = How.ID, using = "email")
    public WebElement emailField;

    @FindBy(how = How.ID, using = "role")
    public WebElement roleOption;

    @FindBy(how = How.ID, using = "status")
    public WebElement statusOption;

    @FindBy(how = How.ID, using = "modal_close_btn")
    public WebElement modalCloseButtonSubmit;

    @FindBy(how = How.ID, using = "submit_button")
    public WebElement submitCreateUserButton;

    @FindBy(how = How.ID, using = "submit_edit_button")
    public WebElement submitEditUserButton;

    @FindBy(how = How.XPATH, using = "//input[@placeholder='Enter Email']")
    public WebElement emailFilter;

    @FindBy(how = How.ID, using = ".//form[@id='filter_form']//select[@name='filter_role']")
    public WebElement roleFilter;

    @FindBy(how = How.ID, using = "filter_button")
    public WebElement filterButton;

    @FindBy(how = How.XPATH, using = ".//table[@class='table']")
    public WebElement table;




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

    public void clickSubmitCreateUserButton(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(submitCreateUserButton));
            wait.until(ExpectedConditions.elementToBeClickable(submitCreateUserButton)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            clickSubmitCreateUserButton();
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

    public int getCellCount(WebElement tableElement) {
        int cellCount = 0;

        // Get all rows from the table
        List<WebElement> rows = tableElement.findElements(By.tagName("tr"));

        // Iterate through the rows (excluding the header row)
        for (int i = 1; i < rows.size(); i++) {
            WebElement row = rows.get(i);

            // Get all cells in the row
            List<WebElement> cells = row.findElements(By.tagName("td"));

            // Get the value of the first cell and assign it to variable cellValue
            cellCount = cells.size();
        }

        return cellCount;
    }

    public String getCellValue(WebElement tableElement, int index) {
        String cellValue = null;

        // Get all rows from the table
        List<WebElement> rows = tableElement.findElements(By.tagName("tr"));

        // Iterate through the rows (excluding the header row)
        for (int i = 1; i < rows.size(); i++) {
            WebElement row = rows.get(i);

            // Get all cells in the row
            List<WebElement> cells = row.findElements(By.tagName("td"));

            // Get the value of the first cell and assign it to variable cellValue
            cellValue = cells.get(index).getText();
        }

        return cellValue;
    }
}
