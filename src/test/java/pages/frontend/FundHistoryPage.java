package pages.frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FundHistoryPage {
    private WebDriver driver;

    //NavigationPage - Fund history button
    @FindBy(how = How.XPATH, using = ".//a[@href='/account/fund-history']")
    public WebElement fundHistoryButton;

    @FindBy(how = How.XPATH, using = ".//section[@class='container total-balance']//div[2]//div[1]//h4")
    public WebElement balanceDisplay;

    @FindBy(how = How.XPATH, using = ".//section[@class='container total-balance']//div[2]//div[2]//h4")
    public WebElement bonusDisplay;

    @FindBy(how = How.XPATH, using = ".//section[@class='container total-balance']//div[2]//div[3]//h4")
    public WebElement rolloverDisplay;

    @FindBy(how = How.XPATH, using = ".//div[@class='table-responsive']")
    public WebElement fundHistoryTable;

    @FindBy(how = How.XPATH, using =  ".//div[@class='table-responsive']//tr[1]//td[@class='hidden-mobile']")
    public WebElement firstRowTransId;

    public FundHistoryPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    public String getBalance() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(balanceDisplay));
        return balanceDisplay.getText();
    }

    public String getBonus() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(bonusDisplay));
        return bonusDisplay.getText();
    }

    public String getRollover() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(rolloverDisplay));
        return rolloverDisplay.getText();
    }

    public void clickFundHistoryButton() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(fundHistoryButton));
        fundHistoryButton.click();
    }

    public String getFirstRowTransId() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(fundHistoryTable));
        return firstRowTransId.getText();
    }

}
