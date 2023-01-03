package steps.frontend;

import engine.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.frontend.Navigation;
import pages.frontend.brasilbet.Deposit;
import pages.frontend.brasilbet.FundHistory;

public class Actions {
    private static WebDriver driver;

    public Actions(Driver driver) {
        Actions.driver = driver.get();

    }

    public void goToFundHistory(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        Navigation page = new Navigation(driver);

        wait.until(ExpectedConditions.visibilityOf(page.FundHistory));
        wait.until(ExpectedConditions.elementToBeClickable(page.FundHistory))
                .click();
    }

    public static String getBalance(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        FundHistory page = new FundHistory(driver);

        //wait for the balance to be visible and then get text
        return wait.until(ExpectedConditions.visibilityOf(page.balanceDisplay)).getText();
    }

    public static String getBonus(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        FundHistory page = new FundHistory(driver);

        //wait for the balance to be visible and then get text
        return wait.until(ExpectedConditions.visibilityOf(page.bonusDisplay)).getText();

    }

    public static String getRollover(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        FundHistory page = new FundHistory(driver);

        //wait for the balance to be visible and then get text
        return wait.until(ExpectedConditions.visibilityOf(page.rolloverDisplay)).getText();

    }
}
