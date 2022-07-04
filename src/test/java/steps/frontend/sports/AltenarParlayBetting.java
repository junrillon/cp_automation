package steps.frontend.sports;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
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
import java.util.Random;

public class AltenarParlayBetting {

    private final WebDriver driver;
    public PageModelBase PageModelBase;

    public AltenarParlayBetting(PageModelBase PageModelBase, Driver driver){
        this.driver = driver.get();
        this.PageModelBase = PageModelBase;
    }

    String eventList = ".//div[@name='asb-upcoming']//div[contains(concat(' ',@class,' '), ' _asb_events-table ')]/child::div[2]/div";
    String marketList = "//div[contains(concat(' ',@class,''), ' _asb_prices-market ')]";
    String oddsList = "//div[contains(concat(' ',@class,''), ' _asb_prices-markets--price-block ')]";

    public int eventSelection(){
        Altenar altenar = new Altenar(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Get the count of the markets available in the match
        int min = 1;
        int eventSelection = altenar.upcoming_EventList.size();

        //Select Random number between min and max; will generate until generated number is not equal to 1 and 0
        Random r = new Random();
        int selectedEvent;
        do {
            selectedEvent = r.nextInt((eventSelection - min) + 1);

        } while (selectedEvent == 0);

        return selectedEvent;

    }

    public int marketSelection(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        int eventIndex = eventSelection();
        String selectedEvent = eventList + "[" + eventIndex + "]";
        List<WebElement> marketSelection = driver.findElements(By.xpath(selectedEvent + marketList));

        //Get the count of the markets available in the match
        int min = 1;
        int marketCount = marketSelection.size();

        //Select Random number between min and max; will generate until generated number is not equal to 1 and 0
        Random r = new Random();
        int selectedMarket;
        do {
            selectedMarket = r.nextInt((marketCount - min) + 1);

        } while (selectedMarket == 0);

        return selectedMarket;

    }

    public void oddsSelectionPerEvent() throws InterruptedException {
        Altenar altenar = new Altenar(driver);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        WebDriverWait wait = new WebDriverWait(driver, 20);

        int selectedEventIndex = eventSelection();
        int selectedMarketIndex = marketSelection();

        //Scroll into the selected event
        WebElement selectedEvent = driver.findElement(By.xpath(eventList + "[" + selectedEventIndex + "]"));
        PageModelBase.scrollIntoView(altenar.upcoming_MatchContainer);
        Thread.sleep(1000);

        //Concat xpath of the selected event and selected market
        String selectedMarket = eventList + "[" + selectedEventIndex + "]" + marketList + "[" + selectedMarketIndex + "]";
        List<WebElement> oddsSelection = driver.findElements(By.xpath(selectedMarket + oddsList));

        //Get the count of the markets available in the match
        int min = 1;
        int oddsSelectionCount = oddsSelection.size();

        //Select Random number between min and max
        Random r = new Random();
        int selectedOdds;
        do {
            selectedOdds = r.nextInt((oddsSelectionCount - min) + 1);

        } while (selectedOdds == 0);

        //Wait for the element to be clickable, then click selected selection
        wait.until(ExpectedConditions.visibilityOf(oddsSelection.get(selectedOdds)));
        wait.until(ExpectedConditions.elementToBeClickable(oddsSelection.get(selectedOdds)));
        oddsSelection.get(selectedOdds).click();

    }

    StringBuffer resultContent = new StringBuffer();
    String betSlipId;
    String origBalance;
    String balanceAfter;
    String betAmount;
    String result;

    @When("I select odds on upcoming matches")
    public void iSelectOdds() throws InterruptedException {
        Altenar altenar = new Altenar(driver);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Call market selection
        oddsSelectionPerEvent();
        oddsSelectionPerEvent();

        //Scroll up into betSlip details
        WebElement tag = driver.findElement(By.tagName("html"));
        tag.sendKeys(Keys.HOME);

    }

    @When("I place parlay bet")
    public void iPlaceBet(){
        Altenar altenar = new Altenar(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Get balance before bet
        origBalance = altenar.balance.getText();
        String formatted_origBalance = origBalance.replace(",","");

        //Get bet amount value
        betAmount = wait.until(ExpectedConditions.visibilityOf(altenar.totalStake)).getText()
                .replace(" R$","");

        //Accept any change odds
        AltenarSingleBetting.acceptAnyOddsChange(driver);

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

        //================= Compute balance after bet
        BigDecimal OB = new BigDecimal(formatted_origBalance);
        BigDecimal BA = new BigDecimal(betAmount);
        BigDecimal expectedBalanceAfterWin = OB.subtract(BA);

        //Get balance after bet
        wait.until(ExpectedConditions
                .not(ExpectedConditions.textToBePresentInElement(altenar.balance, origBalance)));
        balanceAfter = altenar.balance.getText();
        String formatted_balanceAfter = balanceAfter.replace(",","");
        BigDecimal ABA = new BigDecimal(formatted_balanceAfter); //Actual balance after bet

        if(ABA.equals(expectedBalanceAfterWin)){
            Assert.assertEquals(ABA,expectedBalanceAfterWin);
            result = ("Balance before bet: " + origBalance + "%0A" +
                    "Bet Amount: "+ betAmount + "%0A" +
                    "Bet ID: " + betSlipId + " (Parlay Bet)%0A" +
                    "Balance after bet: " + balanceAfter + "%0A");

            String formattedResult = result.replace("%0A", "\n");
            System.out.println(formattedResult);

            resultContent.append(result);

        } else {
            System.out.println("Actual balance after: " + ABA +
                    "\n Expected balance after: " + expectedBalanceAfterWin);
        }

    }

    @Then("I check my parlay bet")
    public void iCheckMyParlayBet() {
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

    @Then("I send brasil parlay betting result in telegram")
    public void sendBrasilBettingResult(DataTable telegramCreds){
        List<List<String>> data = telegramCreds.asLists(String.class);
        String token = data.get(1).get(0);
        String chatId = data.get(1).get(1);

        String resultContentString = resultContent.toString();
        String username = Login.user;

        try {
            URL url = new URL("https://api.telegram.org/bot"+token+"/sendMessage?chat_id="+chatId+
                    "&text=Provider: Brasil%0AUsername: "+username+"%0A"+ resultContentString);

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
