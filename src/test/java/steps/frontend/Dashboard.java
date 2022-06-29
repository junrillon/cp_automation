package steps.frontend;

import engine.Driver;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.frontend.sports.Altenar;

public class Dashboard {
    private final WebDriver driver;

    public Dashboard(Driver driver) {
        this.driver = driver.get();
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
        Altenar altenar = new Altenar(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        WebElement navSports = wait.until(ExpectedConditions.visibilityOf(page.navSports));
        wait.until(ExpectedConditions.elementToBeClickable(navSports));
        navSports.click();

        //Wait for brasil frame
        WebElement brasilFrame = wait.until(ExpectedConditions.visibilityOf(altenar.altenar));
        boolean isPresent = brasilFrame.isDisplayed();
        if(isPresent){
            System.out.println("Brasil frame is present.");
        }

    }

}
