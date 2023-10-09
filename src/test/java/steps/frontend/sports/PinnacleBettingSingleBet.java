package steps.frontend.sports;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageModelBase;
import pages.frontend.ggplay.Dashboard;
import pages.frontend.sports.PinnaclePage;
import steps.Login;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class PinnacleBettingSingleBet {
    private WebDriver driver;
    public PageModelBase PageModelBase;

    public PinnacleBettingSingleBet(PageModelBase PageModelBase, Driver driver) {
        this.driver = driver.get();
        this.PageModelBase = PageModelBase;
    }


    String scenarioTitle;
    String result;
    StringBuffer reportResult = new StringBuffer();

    @Given("I click the sports pinnacle header button")
    public void iClickTheSportsHeaderButton() {

        //Click sports header button
        PinnaclePage page = new PinnaclePage(driver);
        page.SportsHeaderBtn.click();
    }

    public int leagueSelection(){
        PinnaclePage page = new PinnaclePage(driver);

        //Get the count of the markets available in the match
        int min = 1;
        int leagueSelection = page.sportLeague.size();

        //Select Random number between min and max; will generate until generated number is not equal to 1 and 0
        Random r = new Random();
        int selectedEvent;
        do {
            selectedEvent = r.nextInt((leagueSelection - min) + 1);

        } while (selectedEvent == 0);

        return selectedEvent;

    }


    int league;
    String selectedMatch;

    public int matchSelection(){
        league = leagueSelection();

        selectedMatch =  ".//div[@class=\"early-mk\"]//div[@class='league']["+league+"]/following-sibling::div[1]//table";
        List<WebElement> matchSize = driver.findElements(By.xpath(selectedMatch));

        //Get the count of the markets available in the match
        int min = 1;
        int matchSelection = matchSize.size();

        //Select Random number between min and max; will generate until generated number is not equal to 1 and 0
        Random r = new Random();
        int selectedMatch = 0;

        //check if there's only 1 match in the selected league;
        if(matchSelection == 1 ){
            selectedMatch = 1;

        } else {
            do {
                selectedMatch = r.nextInt((matchSelection - min) + 1);

            } while (selectedMatch == 0);
        }

        return selectedMatch;

    }

    public void oddsPicking(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        String mLOdds = "//td[@class=\"col-1x2 col-1x2-0\"]//a[@data-team-type=\"0\"]";
        int match = matchSelection();
        WebElement odds = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(selectedMatch + "["+match+"]" + mLOdds))));

        PageModelBase.scrollIntoView(odds);
        wait.until(ExpectedConditions.elementToBeClickable(odds));
        odds.click();

    }

    public void enterAmountAndPlaceBet(String amount){
        PinnaclePage page = new PinnaclePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //input bet amount
        wait.until(ExpectedConditions.visibilityOf(page.SinglePinnacleBetAmount));
        page.SinglePinnacleBetAmount.sendKeys(amount);

        //Click Accept Better Odds button
        wait.until(ExpectedConditions.elementToBeClickable(page.AcceptBetterOdds));
        page.AcceptBetterOdds.click();

        //Click Place Bet Button
        wait.until(ExpectedConditions.elementToBeClickable(page.SinglePlaceBetButton));
        page.SinglePlaceBetButton.click();
    }

    String balanceBeforeBet;
    String balanceBeforeBet_formatted;
    String pUsername;

    @When("I click the early matches")
    public void iClickTheEarlyMatches() throws InterruptedException {

        PinnaclePage page = new PinnaclePage(driver);
        Dashboard page2 = new Dashboard(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //get wallet balance display before betting
        WebElement balanceBeforeBetOrigin = wait.until(ExpectedConditions.visibilityOf(page2.walletBalance));

        //get the text of the inGameBalance, then remove the comma and PHP
        balanceBeforeBet = balanceBeforeBetOrigin.getText();
        balanceBeforeBet_formatted = balanceBeforeBet.replace(",","");
        System.out.println("Balance: " + balanceBeforeBet_formatted);

        // verify if iframe is available and switch to that iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page.iFramePinnacle));
        //wait.until(ExpectedConditions.visibilityOf(page.oddspage1));

        //Check if sports tab is displayed
        int sportsTab = 0;
        do{
            sportsTab = page.SportsTab.size();
            System.out.println("i: "+sportsTab);

        } while(sportsTab != 1);

        //Click Basketball Button
        wait.until(ExpectedConditions.visibilityOf(page.BasketballButton));
        wait.until(ExpectedConditions.elementToBeClickable(page.BasketballButton));
        page.BasketballButton.click();

        //Click the early match button
        wait.until(ExpectedConditions.elementToBeClickable(page.EarlyMatchButton));
        page.EarlyMatchButton.click();

    }


    String BetAmount;
    int BetSelection;
    String odds;

    @When("I place a bet on single bet")
    public void iPlaceSingeBet(DataTable pinnacle) throws InterruptedException{

        PinnaclePage page = new PinnaclePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //get the value list from feature file
        List<List<String>> data = pinnacle.asLists(String.class);
        BetAmount = data.get(1).get(0);

        //Check if sports tab is displayed
        int oddspage = 0;
        do{
            oddspage = page.oddspage.size();

        } while(oddspage != 1);

        //Delay
        Thread.sleep(1000);
        System.out.println("league: " + leagueSelection());
        System.out.println("match: " + matchSelection());

        //select odds
        oddsPicking();

        //enter amount then place bet
        enterAmountAndPlaceBet(BetAmount);


    }

    @When("I confirm place bet")
    public void iConfirmPlaceBet() {
        PinnaclePage page = new PinnaclePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        WebElement alert;
        String alertMessage;

        int alertIsPresent = page.alert.size();
        if(alertIsPresent > 0){
            //Get alert message
            alert = wait.until(ExpectedConditions.visibilityOf(page.alert.get(0)));
            alertMessage = alert.getText();
            System.out.println(alertMessage);

            if(alertMessage.contains("Are you sure you want to submit this wager?")){

                //Click Confirm OK Button
                wait.until(ExpectedConditions.elementToBeClickable(page.alertOkButton));
                page.alertOkButton.click();

                //Wait for alert to be visible again then get text
                wait.until(ExpectedConditions.visibilityOf(alert));
                alertMessage = alert.getText();
                System.out.println(alertMessage);

                //Click Confirm OK Button
                wait.until(ExpectedConditions.elementToBeClickable(page.alertOkButton));
                page.alertOkButton.click();
            }

        }

    }

    BigDecimal actualBalanceAfterBetFinal;
    BigDecimal balanceAfterbet;
    String wagerID;

    @When("I check my balance")
    public void iCheckMyBalance(){

        PinnaclePage page = new PinnaclePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        Dashboard page2 = new Dashboard(driver);

        //get wager ID after successful bet
        WebElement wager = wait.until(ExpectedConditions.visibilityOf(page.BetSlipWagerId));
        wagerID = wager.getText();
        System.out.println("WagerID: " + wagerID);

        //switch back to dashboard content
        driver.switchTo().defaultContent();

        BigDecimal BB = new BigDecimal(balanceBeforeBet_formatted);
        BigDecimal BA = new BigDecimal(BetAmount);
        balanceAfterbet = BB.subtract(BA);

        System.out.println("Balance before bet: " + BB );
        System.out.println("Bet amount: " + BA );
        System.out.println("Expected balance after bet: " + balanceAfterbet);
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(page2.walletBalance, balanceBeforeBet)));

        String actualBalanceAfterBetTrim = page2.walletBalance.getText().replace(",","");
        actualBalanceAfterBetFinal =  new BigDecimal(actualBalanceAfterBetTrim);
        System.out.println("Actual balance After Bet: " + actualBalanceAfterBetFinal);
        Assert.assertEquals(balanceAfterbet, actualBalanceAfterBetFinal);

    }

    @Then("I check wager id in myBets")
    public void iCheckWagerIdInMyBets() {

        PinnaclePage page = new PinnaclePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //get the username for reporting
        String username = Login.user;

        // verify if iframe is available and switch to that iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page.iFramePinnacle));

        String winHandleBefore = driver.getWindowHandle();

        //Click the PinnaclePage My Bets Button
        wait.until(ExpectedConditions.elementToBeClickable(page.PinnacleMyBets));
        page.PinnacleMyBets.click();

        //Switch to My bets window
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //Check if my bets table is displayed
        int myBetsTable = page.myBetsTable.size();
        int wagerId = 0;

        if(myBetsTable > 0){

            //Check if specific wager ID is present
            wagerId = driver.findElements(By.xpath(".//span[@class=\"wager_id\" and contains(text(),'"+wagerID+"')]")).size();
            if(wagerId > 0){
                System.out.println("Wager ID is Present!");
            }

        } else {
            driver.navigate().refresh();
        }

        //Wager ID assertion
        Assert.assertEquals(1, wagerId);

        //Close the new tab (My Bets)
        driver.close();

        //Switch to Old tab (main tab)
        driver.switchTo().window(winHandleBefore);


        //Assign values to result variable
        result = ("Scenario: " + scenarioTitle + "%0A" +
                "Username: " + username + "%0A" +
                "Wager ID: " + wagerID + "%0A" +
                "Balance before bet: " + balanceBeforeBet + "%0A" +
                "Bet amount: " + BetAmount + "%0A" +
                "Balance after bet: "+balanceAfterbet + "%0A" + "\n");

        //Append the result
        reportResult.append(result);

        //Display the results
        String formattedResult = result.replace("%0A", "\n");
        System.out.println(formattedResult);

    }

    @Then("I send pinnacle betting result in telegram")
    public void sendPinnacleBettingResult(DataTable telegramCreds){
        List<List<String>> data = telegramCreds.asLists(String.class);
        String token = data.get(1).get(0);
        String chatId = data.get(1).get(1);

        String resultContentString = reportResult.toString();

        try {
            URL url = new URL("https://api.telegram.org/bot"+token+"/sendMessage?chat_id="+chatId+
                    "&text=**** PINNACLE BETTING ****%0A" + resultContentString);

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
