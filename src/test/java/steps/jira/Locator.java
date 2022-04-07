package steps.jira;

import engine.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Locator {
    private WebDriver driver;
    public Locator(WebDriver driver) {PageFactory.initElements(driver, this);}
    
    public WebElement TestrailFrame(){
        //return mobileDriver.getDriver().findElements(By.xpath("//android.widget.ListView/android.view.View[4]/android.view.View/android.widget.Button"));
        return driver.findElement(By.xpath("//div[@data-test-id='issue.views.issue-details.issue-layout.issue-layout']//iframe"));

    }

    public List<WebElement> TestrailUrl(){
        return driver.findElements(By.xpath("//a[contains(@href, 'index.php?/auth/login')]"));

    }

    public List<WebElement> testCases_status(){
        return driver.findElements(By.xpath("//p[contains(text(),'No test cases in TestRail are linked to this issue.')]"));

    }

    public List<WebElement> testRuns_status(){
        return driver.findElements(By.xpath("//p[contains(text(),'No test runs in TestRail are linked to this issue.')]"));

    }

    public List<WebElement> loginToTestRail(){
        return driver.findElements(By.xpath("//p[@class='top']/em"));

    }

    public WebElement loginToTestRailButton(){
        return driver.findElement(By.xpath("//p[@class='bottom']/a[@class='button']"));

    }

}
