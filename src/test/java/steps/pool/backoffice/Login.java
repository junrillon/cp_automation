package steps.pool.backoffice;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps.BrokenLinks;
import steps.Hooks;

import java.io.IOException;
import java.util.List;

import static steps.Screenshot.screenshots;
import static steps.BrokenLinks.ICheckTheBrokenLinks;


public class Login {

    public WebDriver driver;


    public Login(Driver driver) {
        this.driver = driver.get();

    }

    @Given("I logged in at pool backoffice ([^\"]*)$")
    public void iLoggedInAtPoolBackoffice(String url, DataTable loginDetails) throws IOException {

        //Open browser plus url
        driver.get(url);

        //Get data table from feature file
        List<List<String>> data = loginDetails.asLists(String.class);
        String user = data.get(1).get(0); String pass = data.get(1).get(1);

        //Input username and password
        pages.pool.backoffice.Login pageLogin = new pages.pool.backoffice.Login(driver);
        pageLogin.LoginAdmin(user, pass);

        // wait for the element dashboard title to be visible and verify its text
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.titleIs("Dashboard"));
            Assert.assertEquals(driver.getTitle(), "Dashboard");
        } catch (Exception e) {

            String scenarioTitle = Hooks.scenarioName;

            System.out.println("scenario fail " + scenarioTitle);

            //take a screenshot here
            screenshots(driver);

            Assert.fail("Assertion failed or element not found!");

        }

        //Verify if user account is display
/*        Dashboard pageDashboard = new Dashboard(driver);
        pageDashboard.userAccountDisplay();*/


      //  ICheckTheBrokenLinks();
        BrokenLinks.ICheckTheBrokenLinks(driver);


    }


}
