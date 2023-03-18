package steps.pool.backoffice;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.pool.backoffice.Dashboard;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public void iClickSports() {

        //Click pool header button
        Dashboard page = new Dashboard(driver);
        page.clickSports();

    }
}
