package steps;

import engine.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.frontend.NavigationPage;
import pages.frontend.sports.AltenarPage;

public class FrontendDashboard {
    private final WebDriver driver;
    private final NavigationPage navigationPage;

    public FrontendDashboard(Driver driver) {
        this.driver = driver.get();
        this.navigationPage = new NavigationPage(this.driver);
    }

    @Given("I navigate to games casino")
    public void iNavigateToGamesCasino() {
        pages.frontend.ggplay.Dashboard page = new pages.frontend.ggplay.Dashboard(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        WebElement gamesCasino = page.navGamesCasino;
        wait.until(ExpectedConditions.visibilityOf(gamesCasino));
        wait.until(ExpectedConditions.elementToBeClickable(gamesCasino));
        gamesCasino.click();

    }

    @Given("I navigate to live casino")
    public void iNavigateToLiveCasino() {
        pages.frontend.ggplay.Dashboard page = new pages.frontend.ggplay.Dashboard(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        WebElement liveCasino = page.navLiveCasino;
        wait.until(ExpectedConditions.visibilityOf(liveCasino));
        wait.until(ExpectedConditions.elementToBeClickable(liveCasino));
        liveCasino.click();

    }

    @Given("I navigate to altenar sports")
    public void iNavigateToSports() {
        pages.frontend.ggplay.Dashboard page = new pages.frontend.ggplay.Dashboard(driver);
        AltenarPage altenarPage = new AltenarPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        WebElement navSports = wait.until(ExpectedConditions.visibilityOf(page.navSports));
        wait.until(ExpectedConditions.elementToBeClickable(navSports));
        navSports.click();

        //Wait for brasil frame
        WebElement brasilFrame = wait.until(ExpectedConditions.visibilityOf(altenarPage.altenar));
        boolean isPresent = brasilFrame.isDisplayed();
        if(isPresent){
            System.out.println("Brasil frame is present.");
        }

    }

    @Given("I click the deposit button")
    public void clickDepositButton(){
        //click deposit button
        navigationPage.clickDepositButton();

        //Get the current page title
        String pageTitle = driver.getCurrentUrl();

        //Assert if the title contains "depositPage"
        Assert.assertTrue(pageTitle.contains("frontend/deposit"));
    }

    @When("I click the withdraw button")
    public void clickWithdrawButton(){
        //click withdraw button
        navigationPage.clickWithdrawButton();

        //Get the current page title
        String pageTitle = driver.getCurrentUrl();

        //Assert if the title contains "depositPage"
        Assert.assertTrue(pageTitle.contains("withdraw"));
    }

}
