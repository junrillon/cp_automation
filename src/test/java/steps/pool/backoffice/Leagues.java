package steps.pool.backoffice;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

import static steps.Screenshot.screenshots;


public class Leagues {

    private WebDriver driver;

    public Leagues(Driver driver) {
        this.driver = driver.get();


    }

    @And("I click the create league button")
    public void iClickTheCreateLeagueButton() throws IOException {

        //Click pool header button
        pages.pool.backoffice.Leagues page = new pages.pool.backoffice.Leagues(driver);
        page.clickCreateLeague();


        // wait for the element Sport modal to be visible and verify its text
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement modalDropdownSportName =  wait.until(ExpectedConditions.visibilityOf(page.sportDropdown));

            Assert.assertEquals(true, modalDropdownSportName.isDisplayed());
        } catch (Exception e) {

            String scenarioTitle;

            System.out.println("scenario fail ");

            //take a screenshot here
            screenshots(driver);

            Assert.fail("Assertion failed or element not found!");

        }

    }

    public static String leagueName;
    @And("I input league details")
    public void iInputLeagueDetails(DataTable leagueDetails) {

        //Click pool header button
        pages.pool.backoffice.Leagues page = new pages.pool.backoffice.Leagues(driver);
        page.selectSports(Sports.sportName);

        //get the value list from feature file
        List<List<String>> data = leagueDetails.asLists(String.class);
        leagueName = data.get(1).get(0);
        page.InputLeagueName(leagueName);
        page.clickSubmitBtn();


    }

    @Then("league is successfully created")
    public void leagueIsSuccessfullyCreated() throws IOException {

        // wait for the element Sport modal to be visible and verify its text
        try {
            pages.pool.backoffice.Leagues page = new pages.pool.backoffice.Leagues(driver);
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement sucessModal = wait.until(ExpectedConditions.visibilityOf(page.successMsg));

            Assert.assertEquals("Success", sucessModal.getText());
        } catch (Exception e) {

//            String scenarioTitle = Hooks.scenarioName;
//
//            System.out.println("scenario fail " + scenarioTitle);

            //take a screenshot here
            screenshots(driver);

            Assert.fail("Assertion failed or element not found!");

        }

        pages.pool.backoffice.Leagues page = new pages.pool.backoffice.Leagues(driver);
        page.clickSuccessBtn();


        page.sportDatatableDisplay(Sports.sportName);
        page.leagueDatatableDisplay(leagueName);

    }

    }

