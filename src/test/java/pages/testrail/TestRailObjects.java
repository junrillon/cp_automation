package pages.testrail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class TestRailObjects {

    public TestRailObjects(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH, using = "//input[@id='name']")
    @CacheLookup
    public WebElement username;

    @FindBy(how = How.XPATH, using = "//input[@id='password']")
    @CacheLookup
    public WebElement password;

    @FindBy(how = How.XPATH, using = "//button[@id='button_primary']")
    @CacheLookup
    public WebElement loginButton;

    @FindBy(how = How.XPATH, using = "//div[@id='header']")
    @CacheLookup
    public WebElement testrailHeader;

}
