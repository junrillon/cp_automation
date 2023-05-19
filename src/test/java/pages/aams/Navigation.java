package pages.aams;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Navigation {
    private final WebDriver driver;

    @FindBy(how = How.ID, using = "navbarSupportedContent")
    public WebElement navigation;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'dashboard')]")
    public WebElement dashboard;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'tracking')]")
    public WebElement tracking;

    @FindBy(how = How.XPATH, using = ".//a[contains(text(), 'Report')]")
    public WebElement report;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'summary-report')]")
    public WebElement summaryReport;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'transaction-report')]")
    public WebElement transactionReport;

    @FindBy(how = How.XPATH, using = ".//a[contains(text(), 'Management')]")
    public WebElement management;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'users')]")
    public WebElement users;
    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'couriers')]")
    public WebElement couriers;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'activity-log')]")
    public WebElement activityLogs;

    @FindBy(how = How.XPATH, using = ".//a[contains(text(), 'Settings')]")
    public WebElement settings;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'roles')]")
    public WebElement roles;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'pages')]")
    public WebElement pages;


    public Navigation(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateToDashboard(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(dashboard));
            wait.until(ExpectedConditions.elementToBeClickable(dashboard)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            navigateToDashboard();
        }
    }

    public void navigateToTracking(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(tracking));
            wait.until(ExpectedConditions.elementToBeClickable(tracking)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            navigateToTracking();
        }
    }

    public void navigateToSummaryReport(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(report));
            wait.until(ExpectedConditions.elementToBeClickable(report)).click();

            wait.until(ExpectedConditions.visibilityOf(summaryReport));
            wait.until(ExpectedConditions.elementToBeClickable(summaryReport)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            navigateToSummaryReport();
        }
    }

    public void navigateToTransactionReport(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(report));
            wait.until(ExpectedConditions.elementToBeClickable(report)).click();

            wait.until(ExpectedConditions.visibilityOf(transactionReport));
            wait.until(ExpectedConditions.elementToBeClickable(transactionReport)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());


            //Recall the method
            navigateToTransactionReport();
        }
    }

    public void navigateToUsers(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(management));
            wait.until(ExpectedConditions.elementToBeClickable(management)).click();

            wait.until(ExpectedConditions.visibilityOf(users));
            wait.until(ExpectedConditions.elementToBeClickable(users)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            navigateToUsers();
        }
    }

    public void navigateToCouriers(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(management));
            wait.until(ExpectedConditions.elementToBeClickable(management)).click();

            wait.until(ExpectedConditions.visibilityOf(couriers));
            wait.until(ExpectedConditions.elementToBeClickable(couriers)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            navigateToCouriers();
        }
    }

    public void navigateToActivityLogs(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(management));
            wait.until(ExpectedConditions.elementToBeClickable(management)).click();

            wait.until(ExpectedConditions.visibilityOf(activityLogs));
            wait.until(ExpectedConditions.elementToBeClickable(activityLogs)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            navigateToActivityLogs();
        }
    }

    public void navigateToRoles(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(settings));
            wait.until(ExpectedConditions.elementToBeClickable(settings)).click();

            wait.until(ExpectedConditions.visibilityOf(roles));
            wait.until(ExpectedConditions.elementToBeClickable(roles)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            navigateToRoles();
        }
    }

    public void navigateToPages(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        try {
            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(settings));
            wait.until(ExpectedConditions.elementToBeClickable(settings)).click();

            wait.until(ExpectedConditions.visibilityOf(pages));
            wait.until(ExpectedConditions.elementToBeClickable(pages)).click();

        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException
            System.out.println("The element was not found: " + e.getMessage());

            //Recall the method
            navigateToPages();
        }
    }


}
