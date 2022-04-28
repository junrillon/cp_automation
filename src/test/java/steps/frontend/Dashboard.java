package steps.frontend;

import engine.Driver;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
}
