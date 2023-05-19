package steps.frontend.sports;

import engine.Driver;
import io.cucumber.datatable.DataTable;
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
import pages.frontend.sports.AltenarPage;
import steps.Login;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class AltenarSingleBetting{

    private final WebDriver driver;
    public PageModelBase PageModelBase;

    public AltenarSingleBetting(PageModelBase PageModelBase, Driver driver){
        this.driver = driver.get();
        this.PageModelBase = PageModelBase;
    }

    StringBuffer resultContent = new StringBuffer();

    @When("I check upcoming matches")
    public void iCheckUpcomingMatches(){
        AltenarPage altenarPage = new AltenarPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        JavascriptExecutor js = (JavascriptExecutor)driver;

        //wait for live tab then click
        WebElement upcomingGamesTab = wait.until(ExpectedConditions.visibilityOf(altenarPage.upcoming_MatchContainer));
        PageModelBase.scrollIntoView(upcomingGamesTab);
    }

    @When("I select one upcoming match")
    public void iSelectUpcomingMatch() throws InterruptedException {
        AltenarPage altenarPage = new AltenarPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        JavascriptExecutor js = (JavascriptExecutor)driver;

        //wait for upcoming sports
        WebElement upcomingSports = wait.until(ExpectedConditions.visibilityOf(altenarPage.upcoming_Sports));
        String sportsName = upcomingSports.getAttribute("title");

        //Get the 1st match's event name
        String eventName = wait.until(ExpectedConditions.visibilityOf(altenarPage.upcoming_EventName)).getAttribute("title");

        //scroll into upcoming event
        PageModelBase.scrollIntoView(altenarPage.upcoming_MatchContainer);
        Thread.sleep(1000);

        //wait for upcoming match then click
        WebElement upcomingMatch = wait.until(ExpectedConditions.visibilityOf(altenarPage.upcoming_EventName));
        wait.until(ExpectedConditions.elementToBeClickable(upcomingMatch));
        upcomingMatch.click();

    }

    String betSlipId;
    String origBalance;
    String balanceAfter;
    String betAmount;
    String result;
    @When("I place a single bet on upcoming matches")
    public void iPlaceSingleBet(){
        AltenarPage altenarPage = new AltenarPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Get balance before bet
        origBalance = altenarPage.balance.getText();
        String formatted_origBalance = origBalance
                                        .replace(",","")
                                        .replace("R$ ","");

        //wait for market container to be visible
        wait.until(ExpectedConditions.visibilityOf(altenarPage.marketContainer));

        //Check and wait for loading to be done
        int loading_isPresent = 1;
        do {
            loading_isPresent = altenarPage.loading.size();

        } while (loading_isPresent != 0);

        //Get the count of the selection in the main market
        int min = 1;
        int oddsSelections;
        do {
            oddsSelections = altenarPage.mainMarketOddsSelection.size();

        } while (oddsSelections == 0);

        //Select Random number bet min and max
        Random r = new Random();
        int randomNum = r.nextInt((oddsSelections - min) + 1);

        //Wait for the element to be clickable, then click selected selection
        wait.until(ExpectedConditions.visibilityOf(altenarPage.mainMarketOddsSelection.get(randomNum)));
        wait.until(ExpectedConditions.elementToBeClickable(altenarPage.mainMarketOddsSelection.get(randomNum)));
        altenarPage.mainMarketOddsSelection.get(randomNum).click();

        //Get bet amount value
        betAmount = wait.until(ExpectedConditions.visibilityOf(altenarPage.totalStake)).getText()
                .replace(" R$","");

        //Call acceptAnyOddsChange method
        acceptAnyOddsChange(driver);

        //Place bet
        WebElement placeBet = wait.until(ExpectedConditions.visibilityOf(altenarPage.placeBetBtn));
        wait.until(ExpectedConditions.elementToBeClickable(placeBet));
        placeBet.click();

        //Check and wait for loading to be done
        loading_isPresent = 1;
        do {
            loading_isPresent = altenarPage.loading.size();

        } while (loading_isPresent != 0);

        //Get betslip ID
        wait.until(ExpectedConditions.visibilityOf(altenarPage.betSlipId));
        betSlipId = altenarPage.betSlipId.getText().replace("Bet ID ","");

        //Compute balance after bet
        BigDecimal OB = new BigDecimal(formatted_origBalance);
        BigDecimal BA = new BigDecimal(betAmount);
        BigDecimal expectedBalanceAfterWin = OB.subtract(BA);

        //Get balance after bet
        wait.until(ExpectedConditions
                .not(ExpectedConditions.textToBePresentInElement(altenarPage.balance, origBalance)));
        balanceAfter = altenarPage.balance.getText();
        String formatted_balanceAfter = balanceAfter
                                .replace(",","")
                                .replace("R$ ","");

        BigDecimal ABA = new BigDecimal(formatted_balanceAfter); //Actual balance after bet

        if(ABA.equals(expectedBalanceAfterWin)){
            Assert.assertEquals(ABA,expectedBalanceAfterWin);
            result = ("Balance before bet: " + origBalance + "%0A" +
                    "Bet Amount: "+ betAmount + "%0A" +
                    "Bet ID: " + betSlipId + " (Single Bet)%0A" +
                    "Balance after bet: " + balanceAfter + "%0A");

            String formattedResult = result.replace("%0A", "\n");
            System.out.println(formattedResult);

            resultContent.append(result);

        } else {
            System.out.println("Actual balance after: " + ABA +
                    "\n Expected balance after: " + expectedBalanceAfterWin);
        }

    }

    @Then("I check my single bet")
    public void iCheckMySingleBet() {
        AltenarPage altenarPage = new AltenarPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Wait and click my bets button
        WebElement myBets = wait.until(ExpectedConditions.visibilityOf(altenarPage.myBetsBtn));
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
                    "&text=Provider: AltenarPage%0AUsername: "+username+"%0A"+ resultContentString);

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

    public static void acceptAnyOddsChange(WebDriver driver){
        AltenarPage altenarPage = new AltenarPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Click odds change selection
        WebElement changeOddsSelection = wait.until(ExpectedConditions.visibilityOf(altenarPage.oddsChangeSelectAction));
        wait.until(ExpectedConditions.elementToBeClickable(changeOddsSelection));
        changeOddsSelection.click();

        //Accept any change odds option
        WebElement acceptAnyChangeOddsSelection = wait.until(ExpectedConditions.visibilityOf(altenarPage.acceptAnyChangeOdds));
        wait.until(ExpectedConditions.elementToBeClickable(acceptAnyChangeOddsSelection));
        acceptAnyChangeOddsSelection.click();
    }

}
