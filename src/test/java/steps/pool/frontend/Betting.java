package steps.pool.frontend;

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
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.frontend.ggplay.Dashboard;
import pages.pool.frontend.MatchDetails;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Betting {

    private WebDriver driver;

    public Betting(Driver driver) {
        this.driver = driver.get();


    }

    @Given("I click the pool header button")
    public void iClickThePoolHeaderButton() {

        //Click pool header button
        Dashboard page = new Dashboard(driver);
        page.clickEsportsBtn();
    }

    String balanceBeforeBet;
    @When("I click the test sports")
    public void iClickTheAvailableSports() throws InterruptedException {

        Dashboard page = new Dashboard(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //get wallet balance display
       wait.until(ExpectedConditions.visibilityOfAllElements(page.walletBalance));
        balanceBeforeBet = page.walletBalance();
        System.out.println(" balance before betting: " + balanceBeforeBet);

        // verify if More games is display
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page.iFramePool));


       // driver.switchTo().frame(page.iFramePool);
        //page.selectGameTxtDisplay();

        //click sports
       // WebDriver.Timeouts waitTIme = driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); presenceOfAllElementsLocatedBy


        wait.until(ExpectedConditions.elementToBeClickable(page.TestSport));
        page.clickTestSport();
    }

    String BetAmount;
    @And("I select team A and input bet amount")
    public void iSelectTeamAndInputBetAmount(DataTable matchDetails) throws InterruptedException {

        MatchDetails page = new MatchDetails(driver);

        //get the value list from feature file
        List<List<String>> data = matchDetails.asLists(String.class);
        int BetSelection = Integer.parseInt(data.get(1).get(0));
        BetAmount = data.get(1).get(1);

        Thread.onSpinWait();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(page.selectionA));



        switch(BetSelection) {
            case 1:
                page.ClickTeamA();
                break;
            case 2:
                page.ClickTeamB();
                break;
            case 3:
                page.ClickDraw();
                break;
            default:
                System.out.println("WRONG SELECTION FORMAT!");
                break;
    }


       // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOf(page.amountInput));
        Thread.onSpinWait();
        page.inputAmount(BetAmount);


    }

    @And("I click place bet button")
    public void iClickPlaceBetButton() {

        MatchDetails page = new MatchDetails(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(page.clickSubmitBtn));
        page.clickPlaceBetBtn();
    }

    @And("I confirm my place bet")
    public void iConfirmMyPlaceBet() {

        MatchDetails page = new MatchDetails(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(page.confirmPlacebet));
        page.clickConfirmPlaceBetBtn();
    }

    @Then("place bet success")
    public void placeBetSuccess() throws InterruptedException {


        driver.switchTo().defaultContent();


        // compute new balance after bet
        var trimbalB4Bet = balanceBeforeBet.replace(",","");

        System.out.println("Expected New trimbalB4Bet: " + trimbalB4Bet );
        var BB = Double.valueOf(trimbalB4Bet);
        var BA = Double.valueOf(BetAmount);
        var balanceAfterbet = (BB - BA);
        System.out.println("Expected New BB: " + BB );
        System.out.println("Expected New BA: " + BA );
        var balanceAfterbetFinal = new BigDecimal(balanceAfterbet).setScale(2);
        System.out.println("Expected New balance: " + balanceAfterbetFinal);

        //get wallet balance display after bet
        Thread.sleep(50000);
        //driver.switchTo().defaultContent();
        Dashboard page = new Dashboard(driver);

        var actualBalanceAfterBetTrim = page.walletBalance().replace(",","");;
        //var actualBalanceAfterBetTrim = actualBalanceAfterBet1.replace(",","");
        var actualBalanceAfterBet = Double.valueOf(actualBalanceAfterBetTrim);
        var actualBalanceAfterBetFinal = new BigDecimal(balanceAfterbet).setScale(2);
        System.out.println("Actual balance After Bet: " + actualBalanceAfterBet);


        Assert.assertEquals(balanceAfterbetFinal, actualBalanceAfterBetFinal);
    }

}
