package steps.frontend.sports;

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
import pages.frontend.sports.Pinnacle;
import steps.Hooks;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PinnacleBettingParlayBet {
    private WebDriver driver;

    public PinnacleBettingParlayBet(Driver driver) {this.driver = driver.get();}

    String scenarioTitle = Hooks.scenarioName;
    StringBuffer reportResult = new StringBuffer();

    String balanceBeforeBet; String balanceBeforeBet_formatted; String pUsername;
    @When("I click parlay tab button")
    public void iClickParlayTabButton() throws InterruptedException{

        Pinnacle page = new Pinnacle(driver);
        Dashboard page2 = new Dashboard(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //get wallet balance display before betting
        wait.until(ExpectedConditions.visibilityOf(page2.walletBalance));
        WebElement balanceBeforeBetOrigin = page2.walletBalance;

        //get the text of the inGameBalance, then remove the comma and PHP
        balanceBeforeBet = balanceBeforeBetOrigin.getText();
        balanceBeforeBet_formatted = balanceBeforeBet.replace(",","");
        pUsername = page2.tcxtUsername.getText();
        System.out.println("Expected New BB: " + balanceBeforeBet_formatted );

        // verify if iframe is available and switch to that iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page.iFramePinnacle));

        try{
            wait.until(ExpectedConditions.visibilityOf(page.SportsCollapseButton));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        int sportsTab = page.SportsTab.size();
        System.out.println(sportsTab);
        if(sportsTab > 0){
            System.out.println("Sports Tab is Present!");
        } else {
            driver.navigate().refresh();
        }

        //Uncollapse Sports Tab
        wait.until(ExpectedConditions.visibilityOf(page.SportsCollapseButton));
        wait.until(ExpectedConditions.elementToBeClickable(page.SportsCollapseButton));
        page.SportsCollapseButton.click();

        //Uncollapse Esports Tab
        wait.until(ExpectedConditions.elementToBeClickable(page.EsportsCollapseButton));
        page.EsportsCollapseButton.click();

        //Click Parlay Tab Button
        wait.until(ExpectedConditions.visibilityOf(page.ParlayTabButton));
        wait.until(ExpectedConditions.elementToBeClickable(page.ParlayTabButton));
        page.ParlayTabButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(page.ParlayBasketballButton));
        page.ParlayBasketballButton.click();

        Thread.sleep(5000);

    }

    String BetAmount; int BetOddsSelectedOne; int BetOddsSelectedTwo; String Parlayoddsone; String Parlayoddstwo;
    @When("I place a bet on parlay bet")
    public void iSelectTeamAoddsAndInputBetAmount(DataTable pinnacle) throws InterruptedException{

        Pinnacle page = new Pinnacle(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //get the value list from feature file
        List<List<String>> data = pinnacle.asLists(String.class);
        BetOddsSelectedOne = Integer.parseInt(data.get(1).get(0));
        BetOddsSelectedTwo = Integer.parseInt(data.get(1).get(1));
        BetAmount = data.get(1).get(2);

        //Select ParlayOddsOne
        for(int i=0; i<=2;i++){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(page.ParlayTeamAOddsML));
                //Betting Selection
                switch(BetOddsSelectedOne) {
                    case 1:
                        page.ParlayTeamAOddsML.click();
                        Parlayoddsone = page.ParlayTeamAOddsML.getText();
                        break;
                    case 2:
                        page.ParlayTeamBOddsML.click();
                        Parlayoddsone = page.ParlayTeamBOddsML.getText();
                        break;
                    default:
                        System.out.println("WRONG SELECTION FORMAT!");
                        break;
                }
                break;
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        Thread.sleep(5000); //Gap between selection for 1st and 2nd odds

        //Select ParlayOddsTwo
        for(int i=0; i<=2;i++){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(page.ParlayTeamAOddsOU));
                //Betting Selection
                switch(BetOddsSelectedTwo) {
                    case 1:
                        page.ParlayTeamAOddsOU.click();
                        Parlayoddstwo = page.ParlayTeamAOddsOU.getText();
                        //input bet amount
                        wait.until(ExpectedConditions.visibilityOf(page.ParlayPinnacleBetAmount));
                        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                        page.ParlayPinnacleBetAmount.sendKeys(BetAmount);

                        //Click Place Bet Button
                        wait.until(ExpectedConditions.elementToBeClickable(page.ParlayPlaceBetButton));
                        page.ParlayPlaceBetButton.click();
                        break;
                    case 2:
                        page.ParlayTeamBOddsML.click();
                        Parlayoddstwo = page.ParlayTeamBOddsML.getText();
                        //input bet amount
                        wait.until(ExpectedConditions.visibilityOf(page.ParlayPinnacleBetAmount));
                        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                        page.ParlayPinnacleBetAmount.sendKeys(BetAmount);

                        //Click Place Bet Button
                        wait.until(ExpectedConditions.elementToBeClickable(page.ParlayPlaceBetButton));
                        page.ParlayPlaceBetButton.click();
                        break;
                    default:
                        System.out.println("WRONG SELECTION FORMAT!");
                        break;
                }
                break;
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }


    }

    @When("I confirm my parlay place bet in pinnacle")
    public void iClickPinnacleConfirmBet() throws InterruptedException {
        Pinnacle page = new Pinnacle(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Click Confirm OK Button
//        wait.until(ExpectedConditions.elementToBeClickable(page.PinnacleSuccessBetOK));
//        page.PinnacleSuccessBetOK.click();
//
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        //Click OK Success Message
//        wait.until(ExpectedConditions.elementToBeClickable(page.PinnacleSuccessBetOK));
//        page.PinnacleSuccessBetOK.click();
//        Thread.sleep(5000);

    }

    BigDecimal actualBalanceAfterBetFinal; BigDecimal balanceAfterbet; String wagerID;
    @When("pinnacle parlay place bet success")
    public void pinnaclePlaceBetSuccess() throws InterruptedException {

        Pinnacle page = new Pinnacle(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        Dashboard page2 = new Dashboard(driver);

        //get wager ID after successful bet
        wait.until(ExpectedConditions.visibilityOfAllElements(page.BetSlipWagerId));
        wagerID= page.BetSlipWagerId.getText();
        System.out.println("WagerID: " + wagerID);

        //switch back to dashboard content
        driver.switchTo().defaultContent();

        BigDecimal BB = new BigDecimal(balanceBeforeBet_formatted);
        BigDecimal BA = new BigDecimal(BetAmount);
        balanceAfterbet = BB.subtract(BA);
        System.out.println("Expected New BB: " + BB );
        System.out.println("Expected New BA: " + BA );
        System.out.println("Expected balance after bet: " + balanceAfterbet);
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(page2.walletBalance, balanceBeforeBet)));


        var actualBalanceAfterBetTrim = page2.walletBalance.getText().replace(",","");
        actualBalanceAfterBetFinal =  new BigDecimal(actualBalanceAfterBetTrim);
        System.out.println("Actual balance After Bet: " + actualBalanceAfterBetFinal);
        Assert.assertEquals(balanceAfterbet, actualBalanceAfterBetFinal);

    }

    String MyBetswagerID;
    @Then("pinnacle parlay bet ticket is displayed")
    public void pinnacleBetTicketIsDisplayed() {

        Pinnacle page = new Pinnacle(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // verify if iframe is available and switch to that iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page.iFramePinnacle));

        String winHandleBefore = driver.getWindowHandle();

        //Click the Pinnacle My Bets Button
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

            wagerId = page.betDetail_WagerId.size();
            if(wagerId > 0){
                System.out.println("Wager ID is Present!");
            }

        } else {
            driver.navigate().refresh();
        }

        //Wager ID assertion
        Assert.assertEquals(wagerId, 1);

        //Close the new tab (My Bets)
        driver.close();

        //Switch to Old tab (main tab)
        driver.switchTo().window(winHandleBefore);

        reportResult.append("**** PINNACLE BETTING ****" + "%0A" +
                "Scenario: " + scenarioTitle + "%0A" +
                "Username: " + pUsername + "%0A" +
                "Balance before bet: " + balanceBeforeBet + "%0A" +
                "Bet amount: " + BetAmount + "%0A" +
                "Balance after bet: "+balanceAfterbet + "%0A" +
                "Parlay Odds One: " + Parlayoddsone + "%0A" +
                "Parlay Odds Two: " + Parlayoddstwo + "\n");



        String testResult = ("**** PINNACLE BETTING ****" + "\n" +
                "Scenario: " + scenarioTitle + "\n" +
                "Username: " + pUsername + "\n" +
                "Balance before bet: " + balanceBeforeBet + "\n" +
                "Bet amount: " + BetAmount + "\n" +
                "Balance after bet: "+ balanceAfterbet + "\n" +
                "Parlay Odds One: " + Parlayoddsone + "\n" +
                "Parlay Odds Two: " + Parlayoddstwo + "\n");


        System.out.println("rrrrrrrrrrrrrrrrrr" + testResult);

        try {
            // String result = "test result";
            URL url = new URL("https://api.telegram.org/bot5325722800:AAESQyezs3QY_7JXY0ZFVn83eQExVfTgYgg/sendMessage?chat_id=-1001766036425&text=" +reportResult);
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
