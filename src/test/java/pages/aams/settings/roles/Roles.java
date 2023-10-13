package pages.aams.settings.roles;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Roles {
    private final WebDriver driver;

    @FindBy(how = How.ID, using = "content")
    public WebElement content;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'roles/create')]")
    public WebElement createRoleButton;

    @FindBy(how = How.ID, using = "filter_role")
    public WebElement roleDropdown;

    @FindBy(how = How.ID, using = "filter_status")
    public WebElement statusDropdown;

    @FindBy(how = How.ID, using = "filter_button")
    public WebElement filterButton;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'roles/edit')]")
    public WebElement editRoleButton;

    @FindBy(how = How.XPATH, using = ".//table[@class='table']")
    public WebElement table;

    public Roles(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
