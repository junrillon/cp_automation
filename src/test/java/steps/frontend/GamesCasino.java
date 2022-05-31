package steps.frontend;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.frontend.ggplay.Dashboard;
import pages.frontend.ggplay.casino.CasinoGames;
import pages.frontend.ggplay.casino.LiveGames;

import java.util.List;

public class GamesCasino {
    private final WebDriver driver;

    public GamesCasino(Driver driver) {
        this.driver = driver.get();
    }

    public static String provider;
    @When("I select provider")
    public void iSelectProvider(DataTable providerDetails) {
        Dashboard page = new Dashboard(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        List<List<String>> sportsDetails = providerDetails.asLists(String.class);
        provider = sportsDetails.get(1).get(0);

        //wait for the provider filter check box
        wait.until(ExpectedConditions.visibilityOf(page.providerFilter));

        //wait for the casino games display (game card)
        wait.until(ExpectedConditions.visibilityOf(page.gameCard));

        //declare web element for casino provider check box and then click
        WebElement providerFilter = driver.findElement(By.xpath(".//label[contains(text(), '"+provider+"')]"));
        wait.until(ExpectedConditions.visibilityOf(providerFilter));
        providerFilter.click();

    }

    @When("I wait for casino games to load")
    public void iWaitCasinoGamesToLoad(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        CasinoGames CasinoGames = new CasinoGames(driver);

        wait.until(ExpectedConditions.visibilityOf(CasinoGames.gameCard));
        wait.until(ExpectedConditions.visibilityOf(CasinoGames.gameCardImage));

        int h = CasinoGames.gameCardImage.getSize().height;
        while(h < 100) {
            h = CasinoGames.gameCardImage.getSize().height;

            if (h > 100) {
                System.out.println("Game image loaded.");
                break;
            }
        }
    }

    @When("I wait for live games to load")
    public void iWaitLiveGamesToLoad(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        LiveGames LiveGames = new LiveGames(driver);

        wait.until(ExpectedConditions.visibilityOf(LiveGames.gameCard));
        wait.until(ExpectedConditions.visibilityOf(LiveGames.gameCardImage));

        int h = LiveGames.gameCardImage.getSize().height;
        while(h < 100) {
            h = LiveGames.gameCardImage.getSize().height;

            if (h > 100) {
                System.out.println("Game image loaded.");
                break;
            }
        }
    }
}
