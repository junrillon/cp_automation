package Steps.Jira;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Locator {

    private final WebDriver base;

    public Locator(WebDriver base) {
        this.base = base;
    }

    public WebElement TestrailFrame(){
        //return mobileDriver.getDriver().findElements(By.xpath("//android.widget.ListView/android.view.View[4]/android.view.View/android.widget.Button"));
        return base.findElement(By.xpath("//div[@data-test-id='issue.views.issue-details.issue-layout.issue-layout']//iframe"));

    }

    public List<WebElement> TestrailUrl(){
        return base.findElements(By.xpath("//a[contains(@href, 'index.php?/auth/login')]"));

    }

    public List<WebElement> testCases_status(){
        return base.findElements(By.xpath("//p[contains(text(),'No test cases in TestRail are linked to this issue.')]"));

    }

    public List<WebElement> testRuns_status(){
        return base.findElements(By.xpath("//p[contains(text(),'No test runs in TestRail are linked to this issue.')]"));

    }

    public List<WebElement> loginToTestRail(){
        return base.findElements(By.xpath("//p[@class='top']/em"));

    }

    public WebElement loginToTestRailButton(){
        return base.findElement(By.xpath("//p[@class='bottom']/a[@class='button']"));

    }

}

//iframe[@id='tr-frame-panel-references']
//div[contains(@id,'com.testrail.jira.testrail-plugin__panel')]
//div[@id='ghx-detail-view']//iframe[contains(@id,'com.codebarrel.addons.automation__cb-automation-issue-glance')]
