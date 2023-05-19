package pages.agents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AgentDashboard {
    private final WebDriver driver;

    @FindBy(how = How.XPATH, using = ".//a[@href='#create-user']")
    public WebElement createAccountButton;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, '/create-customer')]")
    public WebElement createCustomerButton;

    //Processing deposits tile
    @FindBy(how = How.XPATH, using = ".//div[@id='processingDepositBoard']")
    public WebElement processingDepositTile;

    //Pending deposits tile
    @FindBy(how = How.XPATH, using = ".//div[@id='pendingDepositBoard']")
    public WebElement pendingDepositTile;

    //Pending withdraw
    @FindBy(how = How.XPATH, using = "pendingWithdrawBoard")
    public WebElement pendingWithdrawTile;

    //Processing withdraw
    @FindBy(how = How.XPATH, using = ".//div[@id='processingWithdrawBoard']")
    public WebElement processingWithdrawTile;

    public AgentDashboard(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickCreateAccount(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        wait.until(ExpectedConditions.visibilityOf(createAccountButton));
        createAccountButton.click();
    }

    public void clickCreateCustomer(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        wait.until(ExpectedConditions.visibilityOf(createCustomerButton));
        createCustomerButton.click();
    }

    public void clickProcessingDepositTile(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //wait for agent username field to be visible then enter value
        wait.until(ExpectedConditions.visibilityOf(processingDepositTile));
        processingDepositTile.click();

    }

}
