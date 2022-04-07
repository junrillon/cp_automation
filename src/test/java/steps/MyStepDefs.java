package steps;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MyStepDefs {

    private WebDriver driver;
    public MyStepDefs (Driver driver){
        this.driver = driver.get();
    }

    @Given("I redirect to facebook page")
    public void iRedirect(){
        driver.get("https://www.facebook.com/");
        String PageTitle = driver.getTitle();
        Assert.assertEquals("Facebook - Log In or Sign Up", PageTitle);
    }

    @When("I click create new account")
    public void iClickCreateNewAccount() {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        WebElement createNewAcccount = driver.findElement(By.xpath(".//a[@data-testid='open-registration-form-button']"));
        wait.until(ExpectedConditions.elementToBeClickable(createNewAcccount));
        createNewAcccount.click();

    }

    @When("I enter needed details")
    public void iEnterNeededDetails(DataTable personalDetails) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        List<List<String>> data = personalDetails.asLists(String.class);
        String fname = data.get(1).get(0);
        String lname = data.get(1).get(1);
        String email = data.get(1).get(2);
        String pw = data.get(1).get(3);
        String birthday = data.get(1).get(4);
        String gender = data.get(1).get(5);

        WebElement signUpModal = driver.findElement(By.id("reg_box"));
        wait.until(ExpectedConditions.elementToBeClickable(signUpModal));

        driver.findElement(By.xpath(".//input[@name='firstname']")).sendKeys(fname);
        driver.findElement(By.xpath(".//input[@name='lastname']")).sendKeys(lname);
        driver.findElement(By.xpath("//input[@name='reg_email__']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@name='reg_email_confirmation__']")).sendKeys(email);
        driver.findElement(By.id("password_step_input")).sendKeys(pw);

        Select drpMonth = new Select(driver.findElement(By.id("month")));
        Select drpDay = new Select(driver.findElement(By.id("day")));
        Select drpYear = new Select(driver.findElement(By.id("year")));

        drpMonth.selectByVisibleText("Dec");
        drpDay.selectByVisibleText("24");
        drpYear.selectByVisibleText("1995");

        driver.findElement(By.xpath(".//input[@name='sex']/preceding-sibling::label[text()='Male']")).click();

//        .//button[@name='websubmit']

        System.out.println("");
    }

    @Then("the google search should be displayed")
    public void theGoogleSearchShouldBeDisplayed() {
        String verifyFacebookText = driver.findElement(By.
                xpath(".//h3[text()='MST Connect - Home | Facebook']")).getText();
        Assert.assertEquals("MST Connect - Home", verifyFacebookText);
    }
}