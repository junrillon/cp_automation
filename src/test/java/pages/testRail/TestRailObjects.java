package pages.testRail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class TestRailObjects {

    public TestRailObjects(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH, using = "//input[@id='name']")
    public WebElement username;

    @FindBy(how = How.XPATH, using = "//input[@id='password']")
    public WebElement password;

    @FindBy(how = How.XPATH, using = "//button[@id='button_primary']")
    public WebElement loginButton;

    @FindBy(how = How.XPATH, using = "//div[@id='header']")
    public WebElement testrailHeader;

}
