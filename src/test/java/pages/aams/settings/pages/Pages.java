package pages.aams.settings.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Pages {
    private final WebDriver driver;

    @FindBy(how = How.ID, using = "content")
    public WebElement content;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'pages/create')]")
    public WebElement createPageButton;

    @FindBy(how = How.ID, using = "keyword")
    public WebElement searchInput;

    @FindBy(how = How.ID, using = "filter_status")
    public WebElement statusDropdown;

    @FindBy(how = How.ID, using = "filter_button")
    public WebElement filterButton;

    @FindBy(how = How.XPATH, using = ".//a[contains(@href, 'pages/edit')]")
    public WebElement editPageButton;

    @FindBy(how = How.XPATH, using = ".//table[@class='table']")
    public WebElement table;

    public Pages(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

}
