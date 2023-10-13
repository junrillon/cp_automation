package pages.aams.settings.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pages.PageModelBase;

import java.util.List;

public class CreateEditPagesPage {
    private final WebDriver driver;
    private final PageModelBase baseAction;

    @FindBy(how = How.ID, using = "create_form")
    public WebElement createForm;

    @FindBy(how = How.ID, using = "name")
    public WebElement pageNameInput;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'pages/create')]")
    public WebElement createPageButton;

    @FindBy(how = How.ID, using = "page_path")
    public WebElement pagePathInput;

    // Status dropdown in the Create Page
    @FindBy(how = How.ID, using = "status")
    public WebElement statusDropdown;

    // Status dropdown in the Edit Page
    @FindBy(how = How.XPATH, using = ".//select[@id='role']")
    public WebElement editStatusDropdown;

    @FindBy(how = How.ID, using = "submit_button_create")
    public WebElement submitButtonCreate;

    @FindBy(how = How.ID, using = "submit_button_edit")
    public WebElement submitButtonEdit;

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

    public CreateEditPagesPage(WebDriver driver) {
        this.driver = driver;
        this.baseAction = new PageModelBase(this.driver);
        PageFactory.initElements(driver, this);
    }

}
