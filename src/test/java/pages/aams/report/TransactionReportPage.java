package pages.aams.report;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TransactionReportPage {
    private final WebDriver driver;

    @FindBy(how = How.ID, using = "export_excel_btn")
    public WebElement exportToExcelButton;

    @FindBy(how = How.ID, using = "filter_created_at")
    public WebElement dateFilter;

    @FindBy(how = How.ID, using = "filter_courier")
    public WebElement courierDropdown;

    @FindBy(how = How.ID, using = "filter_status")
    public WebElement statusDropdown;

    @FindBy(how = How.ID, using = "filter_button")
    public WebElement filterButton;

    @FindBy(how = How.ID, using = "modal_body")
    public List<WebElement> modalBody;

    @FindBy(how = How.XPATH, using = ".//table[@class='table']//th[normalize-space()='Date']")
    public WebElement dateColumn;

    @FindBy(how = How.ID, using = "modal_message")
    public List<WebElement> modalMessage;

    @FindBy(how = How.XPATH, using = ".//div[@id='modalFooter']//button[contains(text(), 'Close')]")
    public WebElement modalCloseButton;

    @FindBy(how = How.XPATH, using = ".//table[@class='table']")
    public WebElement transactionTable;

    public TransactionReportPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

}
