package steps.pool.backoffice;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.pool.backoffice.Dashboard;
import io.cucumber.java.en.When;
import steps.Hooks;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static steps.Screenshot.screenshots;


public class Navigation {

    private WebDriver driver;

    public Navigation(Driver driver) {
        this.driver = driver.get();

    }

    @When("I click the games header dropdown")
    public void iClickTheGamesHeaderDropdown() {

        //Click pool header button
        Dashboard page = new Dashboard(driver);
        page.clickGamesDropdown();


    }

    @And("I click sports")
    public void iClickSports() throws IOException {

        //Click pool header button
        Dashboard page = new Dashboard(driver);
        page.clickSports();

        // wait for the element Sport title to be visible and verify its text
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.titleIs("Sports"));
            Assert.assertEquals(driver.getTitle(), "Sports");
        } catch (Exception e) {

            String scenarioTitle = Hooks.scenarioName;

            System.out.println("scenario fail " + scenarioTitle);

            //take a screenshot here
            screenshots(driver);

            Assert.fail("Assertion failed or element not found!");

        }

    }

    @And("I click the leagues")
    public void iClickTheLeagues() throws IOException{

        //Click pool header button
        Dashboard page = new Dashboard(driver);
        page.clickLeagues();

        // wait for the element Sport title to be visible and verify its text
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.titleIs("Leagues"));
            Assert.assertEquals(driver.getTitle(), "Leagues");
        } catch (Exception e) {

            String scenarioTitle = Hooks.scenarioName;

            System.out.println("scenario fail " + scenarioTitle);

            //take a screenshot here
            screenshots(driver);

            Assert.fail("Assertion failed or element not found!");

        }
    }
}
