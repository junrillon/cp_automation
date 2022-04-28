package steps.backoffice;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.backoffice.Dashboard;

import java.util.List;

public class B2CLogin {

    private final WebDriver driver;
    public B2CLogin(Driver driver){
        this.driver = driver.get();
    }

    @Given("^I access b2c backoffice ([^\"]*)$")
    public void IAccessB2cBackoffice(String BOUrl){
        //navigate to backoffice
        driver.navigate().to(BOUrl);
    }

    @When("^I login on b2c backoffice")
    public void inputTheUsernameAndPassword(DataTable credentials) throws InterruptedException{
        Dashboard page = new Dashboard(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);

        List<List<String>> data = credentials.asLists(String.class);
        String username = data.get(1).get(0);
        String password = data.get(1).get(1);

        //Wait for username field to be visible
        wait.until(ExpectedConditions.visibilityOf(page.usernameField));
        page.usernameField.sendKeys(username);
        page.passwordField.sendKeys(password);

        //Wait for the login button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(page.loginButton));
        page.loginButton.click();


    }

    @Then("I should access the dashboard")
    public void iShouldAccessTheDashboard() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //Get current website title
        String currentPage = driver.getTitle();
        if(currentPage.contains("Dashboard")){
            System.out.println("You're in Dashboard.");

        } else if(currentPage.contains("steps.frontend.Login")){
            System.out.println("You're still not logged in.");

        } else {
            System.out.println("You're not in Dashboard.");
        }
    }
}
