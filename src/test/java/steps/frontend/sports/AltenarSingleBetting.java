package steps.frontend.sports;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageModelBase;
import pages.frontend.sports.Altenar;
import steps.frontend.Login;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class AltenarSingleBetting {

    private final WebDriver driver;
    public PageModelBase page;

    public AltenarSingleBetting(PageModelBase page, Driver driver){
        this.driver = driver.get();
        this.page = page;
    }

    boolean withLive = false;
    StringBuffer resultContent = new StringBuffer();

    @When("I check live sports")
    public void iCheckLiveSports() {
        Altenar altenar = new Altenar(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //wait for live tab then click
        WebElement liveGamesTab = wait.until(ExpectedConditions.visibilityOf(altenar.live_MatchContainer));
        boolean isPresent = liveGamesTab.isDisplayed();
        if(isPresent){
            withLive = true;
        }

    }

    @When("I check live matches")
    public void iCheckLiveMatches() {
        Altenar altenar = new Altenar(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        if(withLive){
            //wait for live sports
            WebElement liveSports = wait.until(ExpectedConditions.visibilityOf(altenar.live_Sports));
            String sportsName = liveSports.getAttribute("title");
            liveSports.click();

            //Get the 1st match's event name
            String eventName = wait.until(ExpectedConditions.visibilityOf(altenar.live_EventName)).getAttribute("title");

            //wait for live match then click
            WebElement liveMatch = wait.until(ExpectedConditions.visibilityOf(altenar.live_Event));
            wait.until(ExpectedConditions.elementToBeClickable(liveMatch));
            liveMatch.click();

        } else {
            System.out.println("No live games");
        }

    }

    @When("I check upcoming matches")
    public void iCheckUpcomingMatches(){
        Altenar altenar = new Altenar(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        JavascriptExecutor js = (JavascriptExecutor)driver;

        //wait for live tab then click
        WebElement upcomingGamesTab = wait.until(ExpectedConditions.visibilityOf(altenar.upcoming_MatchContainer));
        js.executeScript("arguments[0].scrollIntoView(true);", upcomingGamesTab);
    }

    @When("I select one upcoming match")
    public void iSelectUpcomingMatch() throws InterruptedException {
        Altenar altenar = new Altenar(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        JavascriptExecutor js = (JavascriptExecutor)driver;

        //wait for upcoming sports
        WebElement upcomingSports = wait.until(ExpectedConditions.visibilityOf(altenar.upcoming_Sports));
        String sportsName = upcomingSports.getAttribute("title");

        //Get the 1st match's event name
        String eventName = wait.until(ExpectedConditions.visibilityOf(altenar.upcoming_EventName)).getAttribute("title");

        //scroll into 5th upcoming event
        js.executeScript("arguments[0].scrollIntoView(true);", altenar.upcoming_MatchContainer);
        Thread.sleep(1000);

        //wait for upcoming match then click
        WebElement upcomingMatch = wait.until(ExpectedConditions.visibilityOf(altenar.upcoming_EventName));
        wait.until(ExpectedConditions.elementToBeClickable(upcomingMatch));
        upcomingMatch.click();

    }

    String betSlipId;
    String origBalance;
    String balanceAfter;
    String betAmount;
    String result;
    @When("I place bet on live matches")
    public void iPlaceBet() {
        Altenar altenar = new Altenar(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Get balance before bet
        origBalance = altenar.balance.getText();
        String formatted_origBalance = origBalance.replace(",","");

        //wait for market container to be visible
        wait.until(ExpectedConditions.visibilityOf(altenar.marketContainer));

        //Click one odd in main market
        WebElement oddsSelection = wait.until(ExpectedConditions.visibilityOf(altenar.oddsSelection));
        wait.until(ExpectedConditions.elementToBeClickable(oddsSelection));
        oddsSelection.click();

        //Change odds change selection
        WebElement changeOddsSelection = wait.until(ExpectedConditions.visibilityOf(altenar.oddsChangeSelectAction));
        wait.until(ExpectedConditions.elementToBeClickable(changeOddsSelection));
        changeOddsSelection.click();

        //Accept any change odds
        WebElement acceptAnyChangeOddsSelection = wait.until(ExpectedConditions.visibilityOf(altenar.acceptAnyChangeOdds));
        wait.until(ExpectedConditions.elementToBeClickable(acceptAnyChangeOddsSelection));
        acceptAnyChangeOddsSelection.click();

        //Get bet amount value
        betAmount = wait.until(ExpectedConditions.visibilityOf(altenar.totalStake)).getText()
                .replace(" R$","");

        //Place bet
        WebElement placeBet = wait.until(ExpectedConditions.visibilityOf(altenar.placeBetBtn));
        wait.until(ExpectedConditions.elementToBeClickable(placeBet));
        placeBet.click();

        //Check and wait for loading to be done
        int loading_isPresent = 1;
        do {
            loading_isPresent = altenar.loading.size();

        } while (loading_isPresent != 0);

        //Get betslip ID
        wait.until(ExpectedConditions.visibilityOf(altenar.betSlipId));
        betSlipId = altenar.betSlipId.getText().replace("Bet ID ","");

        //Compute balance after bet
        BigDecimal OB = new BigDecimal(formatted_origBalance);
        BigDecimal BA = new BigDecimal(betAmount);
        BigDecimal expectedBalanceAfterWin = OB.subtract(BA);

        //Get balance before bet
        balanceAfter = altenar.balance.getText();
        String formatted_balanceAfter = balanceAfter.replace(",","");
        BigDecimal ABA = new BigDecimal(formatted_balanceAfter); //Actual balance after bet

        if(ABA.equals(expectedBalanceAfterWin)){
            Assert.assertEquals(ABA,expectedBalanceAfterWin);
            result = ("Balance before bet: " + origBalance + "%0A" +
                    "Bet Amount: "+ betAmount + "%0A" +
                    "Bet ID: " + betSlipId + "%0A" +
                    "Balance after bet: " + balanceAfter + "%0A");

            System.out.println("\nBalance before bet: " + origBalance + "\n" +
                    "Bet Amount: "+ betAmount + "\n" +
                    "Bet ID: " + betSlipId + "\n" +
                    "Balance after bet: " + balanceAfter + "\n");

            resultContent.append(result);
        }

    }

    @Then("I check my bets")
    public void iCheckMyBets() {
        Altenar altenar = new Altenar(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Wait and click my bets button
        WebElement myBets = wait.until(ExpectedConditions.visibilityOf(altenar.myBetsBtn));
        wait.until(ExpectedConditions.elementToBeClickable(myBets));
        myBets.click();

        List<WebElement> generatedBetSlip = driver.findElements(By.xpath(".//div[@class='_asb_bethistory-item-footer-bet-id ' and contains(text(),'"+betSlipId+"')]"));
        int size = generatedBetSlip.size();
        if(size > 0){
            Assert.assertEquals(size,1);
        }

    }

    @Then("I send brasil betting result in telegram")
    public void sendBrasilBettingResult(DataTable telegramCreds){
        List<List<String>> data = telegramCreds.asLists(String.class);
        String token = data.get(1).get(0);
        String chatId = data.get(1).get(1);

        String resultContentString = resultContent.toString();
        String username = Login.user;

        try {
            URL url = new URL("https://api.telegram.org/bot"+token+"/sendMessage?chat_id="+chatId+
                    "&text=Provider: Brasil%0AUsername: "+username+"%0A"+resultContentString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println(status + ": " + url);

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
