package steps.pool.backoffice;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps.Hooks;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static steps.Screenshot.screenshots;


public class Sports {

    private WebDriver driver;

    public Sports(Driver driver) {
        this.driver = driver.get();


    }
    @And("I click the create sport button")
    public void iClickTheCreateSportButton() throws IOException {

        //Click pool header button
        pages.pool.backoffice.Sports page = new pages.pool.backoffice.Sports(driver);
        page.clickCreateSport();


        // wait for the element Sport modal to be visible and verify its text
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement modalInputSportName =  wait.until(ExpectedConditions.visibilityOf(page.inputSportName));

            Assert.assertEquals(true, modalInputSportName.isDisplayed());
        } catch (Exception e) {

            String scenarioTitle = Hooks.scenarioName;

            System.out.println("scenario fail " + scenarioTitle);

            //take a screenshot here
            screenshots(driver);

            Assert.fail("Assertion failed or element not found!");

        }

    }
    public static String sportName;
    @And("I input sports details")
    public void iInputSportsDetails(DataTable sportsDetails) {


        //get the value list from feature file
        List<List<String>> data = sportsDetails.asLists(String.class);
        sportName = data.get(1).get(0);
        String matchLabel = data.get(1).get(1);
        String image = data.get(1).get(2);
        String videoUrl = data.get(1).get(3);

        //Click pool header button
        pages.pool.backoffice.Sports page = new pages.pool.backoffice.Sports(driver);
        page.inputSportName(sportName);
        page.inputMatchLabel(matchLabel);

        String absolutePath = new File(System.getProperty("user.dir") + image).getAbsolutePath();
      //  System.out.println("Absolute path of sports test image " + absolutePath);

        page.clickChooseFileBtn(absolutePath);

        page.inputVideoUrl(videoUrl);
        
    }

    @And("I click the submit button")
    public void iClickTheSubmitButton() {

        pages.pool.backoffice.Sports page = new pages.pool.backoffice.Sports(driver);
        page.clickSubmitBtn();
    }

    @Then("sport is successfully created")
    public void sportIsSuccessfullyCreated() throws IOException{

        // wait for the element Sport modal to be visible and verify its text
        try {
            pages.pool.backoffice.Sports page = new pages.pool.backoffice.Sports(driver);
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement sucessModal = wait.until(ExpectedConditions.visibilityOf(page.successModal));

            Assert.assertEquals("Success", sucessModal.getText());
        } catch (Exception e) {

            String scenarioTitle = Hooks.scenarioName;

            System.out.println("scenario fail " + scenarioTitle);

            //take a screenshot here
            screenshots(driver);

            Assert.fail("Assertion failed or element not found!");

        }

        pages.pool.backoffice.Sports page = new pages.pool.backoffice.Sports(driver);
        page.clickOkBtn();

        driver.navigate().refresh();
    }

    @When("I search the sports")
    public void iSearchTheSports() throws IOException {

        pages.pool.backoffice.Sports page = new pages.pool.backoffice.Sports(driver);
        page.clickSportSearch(sportName);
        page.clickSubmitSearchBtn();

        try {
            // Set the implicit wait time to 10 seconds
          //  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
       //     WebDriverWait wait = new WebDriverWait(driver, 15);
      //      WebElement element = driver.findElement(By.xpath(".//tr[@class='odd']//td[contains(text(), 'Test Automation Sport')]"));
     //   wait.until(ExpectedConditions.visibilityOf(page.actionDropdown));
      //      wait.until(ExpectedConditions.visibilityOf(element));

       page.sportNameDatatableDisplay(sportName);

      //      System.out.println("11111111111111111111111111111 " +  page.sportDatatableDisplay(sportName));

     //   Assert.assertEquals(true, true);
    } catch (Exception e) {

 //       String scenarioTitle = Hooks.scenarioName;

 //       System.out.println("scenario fail " + scenarioTitle);

        //take a screenshot here
//        screenshots(driver);

        Assert.fail("Assertion failed or element not found! " + e.getMessage());

    }

    }

    @And("I click the edit sport")
    public void iClickTheEditSport() {
        pages.pool.backoffice.Sports page = new pages.pool.backoffice.Sports(driver);
     //   page.sportDatatableDisplay();

        page.clickEditSport();



    }

    @And("I change the match label to fight")
    public void iChangeTheMatchLabelToFight() {
    }

    @And("I click edit submit button")
    public void iClickEditSubmitButton() {
    }
}
