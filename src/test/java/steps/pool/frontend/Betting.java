package steps.pool.frontend;

import engine.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.frontend.ggplay.HomePage;

public class Betting {

    private WebDriver driver;

    public Betting(Driver driver) {
        this.driver = driver.get();

    }

    @Given("I click the pool header button")
    public void iClickThePoolHeaderButton() {

        //Click pool header button
        HomePage page = new HomePage(driver);
        page.clickPoolBtn();
    }

    @When("I click the available sports")
    public void iClickTheAvailableSports() {
    }

    @And("I select team and input bet amount")
    public void iSelectTeamAndInputBetAmount() {
    }

    @And("I click place bet button")
    public void iClickPlaceBetButton() {
    }

    @And("I confirm my place bet")
    public void iConfirmMyPlaceBet() {
    }

    @Then("place bet success")
    public void placeBetSuccess() {
    }
}
