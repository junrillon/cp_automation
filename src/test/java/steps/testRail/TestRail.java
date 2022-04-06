package steps.testRail;

import base.BaseUtil;
import pages.testRail.TestRailObjects;
import steps.testRail.api.APIClient;
import steps.testRail.api.APIException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        int homeheader = base.Driver.findElements(By.xpath("//div[@id='header']")).size();
        if(homeheader == 0){
            Thread.sleep(1400);
            wait.until(ExpectedConditions.visibilityOf(usernameField));
            usernameField.sendKeys(username);

            wait.until(ExpectedConditions.visibilityOf(pwField));
            pwField.sendKeys(password);

            wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
            loginBtn.click();

            System.out.println("Input testrail credentials then logged in.");
        } else {
            System.out.println("You're already logged in.");
        }
    }

    @Given("^Get tr")
    public void getTr(DataTable testrailCreds) throws APIException, IOException{
        List<List<String>> table = testrailCreds.asLists(String.class);
        String username = table.get(1).get(0);
        String password = table.get(1).get(1);
        String testrailUrl = table.get(1).get(2);

        APIClient client = new APIClient(testrailUrl);
        client.setUser(username);
        client.setPassword(password);

        Map data = new HashMap();

        JSONObject c = (JSONObject) client.sendGet("get_run/1164");
//        JSONArray d = (JSONArray) client.sendGet("get_suites/7");
        System.out.println(c);

        //status: passed = 1, blocked = 2, untested = 3, retest = 4, failed = 5 1164
        //data.put("status_id", 5);
        //data.put("comment", "This test worked fine!");
        //JSONObject nr = (JSONObject) client.sendPost("add_run/15", "suite_id=93&name='sampleTestRun'&include_all=True");

        //JSONObject r = (JSONObject) client.sendPost("add_result_for_case/1164/42470", data);
        //System.out.println(r);
    }
}
