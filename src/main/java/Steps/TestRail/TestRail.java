package Steps.TestRail;

import Base.BaseUtil;
import Pages.TestRail.TestRailObjects;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestRail extends BaseUtil {

    private final BaseUtil base;

    public TestRail(BaseUtil base) {
        this.base = base;
    }

    @Given("^I access testrail website ([^\"]*)$")
    public void accessTestrailWebsite(String feUrl){
        //Open Chrome with URL
        base.Driver.navigate().to(feUrl);
        base.Driver.manage().window().maximize();
        System.out.println("Accessed testrail website.");
    }

    @When("^I input testrail username ([^\"]*) and password ([^\"]*)$")
    public void inputTestRailCredentials(String username, String password) throws InterruptedException {
        TestRailObjects testRailObjects = new TestRailObjects(base.Driver);
        WebDriverWait wait = new WebDriverWait(base.Driver, 5);

        WebElement usernameField = testRailObjects.username;
        WebElement pwField = testRailObjects.password;
        WebElement loginBtn = testRailObjects.loginButton;

        Thread.sleep(1400);
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.sendKeys(username);

        wait.until(ExpectedConditions.visibilityOf(pwField));
        pwField.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
        loginBtn.click();

        System.out.println("Input testrail credentials then logged in.");
    }
}
