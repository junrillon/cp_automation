package steps.frontend.sports;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.frontend.ggplay.Dashboard;
import pages.frontend.sports.Pinnacle;
import pages.pool.frontend.MatchDetails;
import steps.Hooks;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PinnacleBettingSingleBet {
    private WebDriver driver;

    public PinnacleBettingSingleBet(Driver driver) {this.driver = driver.get();}

    String scenarioTitle = Hooks.sce;
    StringBuffer reportResult = new StringBuffer();

    @Given("I click the sports pinnacle header button")
    public void iClickTheSportsHeaderButton() {

        //Click sports header button
        Pinnacle page = new Pinnacle(driver);
        page.SportsHeaderBtn.click();
    }

    String balanceBeforeBet; String pUsername;
    @When("I click the early matches")
    public void iClickTheEarlyMatches() {

        Pinnacle page = new Pinnacle(driver);
        Dashboard page2 = new Dashboard(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //get wallet balance display before betting
        wait.until(ExpectedConditions.visibilityOfAllElements(page2.walletBalance));
        String balanceBeforeBetOrigin = page2.walletBalance.getText();
        balanceBeforeBet = balanceBeforeBetOrigin.replace(",","");
        pUsername = page2.tcxtUsername.getText();

        // verify if iframe is available and switch to that iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page.iFramePinnacle));

        //Click Basketball Button
        wait.until(ExpectedConditions.elementToBeClickable(page.BasketballButton));
        page.BasketballButton.click();

        //Click the early match button
        wait.until(ExpectedConditions.elementToBeClickable(page.EarlyMatchButton));
        page.EarlyMatchButton.click();


    }

    String BetAmount; int BetSelection; String odds;
    @And("I select team A odds and input bet amount")
    public void iSelectTeamAoddsAndInputBetAmount(DataTable pinnacle) throws InterruptedException{

        Pinnacle page = new Pinnacle(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //get the value list from feature file
        List<List<String>> data = pinnacle.asLists(String.class);
        BetSelection = Integer.parseInt(data.get(1).get(0));
        BetAmount = data.get(1).get(1);

        //Wait for odds to be clickable
        for(int i=0; i<=2;i++){
            try{
                wait.until(ExpectedConditions.elementToBeClickable(page.TeamAOddsML));
                //Betting Selection
                switch(BetSelection) {
                    case 1:
                        page.TeamAOddsML.click();
                        odds = page.TeamAOddsML.getText();
                        //input bet amount
                        wait.until(ExpectedConditions.visibilityOf(page.SinglePinnacleBetAmount));
                        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                        page.SinglePinnacleBetAmount.sendKeys(BetAmount);

                        //Click Accept Better Odds button
                        wait.until(ExpectedConditions.elementToBeClickable(page.AcceptBetterOdds));
                        page.AcceptBetterOdds.click();

                        //Click Place Bet Button
                        wait.until(ExpectedConditions.elementToBeClickable(page.SinglePlaceBetButton));
                        page.SinglePlaceBetButton.click();
                        break;
                    case 2:
                        page.TeamBOddsML.click();
                        odds = page.TeamBOddsML.getText();
                        //input bet amount
                        wait.until(ExpectedConditions.visibilityOf(page.SinglePinnacleBetAmount));
                        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                        page.SinglePinnacleBetAmount.sendKeys(BetAmount);

                        //Click Accept Better Odds button
                        wait.until(ExpectedConditions.elementToBeClickable(page.AcceptBetterOdds));
                        page.AcceptBetterOdds.click();

                        //Click Place Bet Button
                        wait.until(ExpectedConditions.elementToBeClickable(page.SinglePlaceBetButton));
                        page.SinglePlaceBetButton.click();
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

    @And("I confirm my place bet in pinnacle")
    public void iClickPinnacleConfirmBet() throws InterruptedException {
        Pinnacle page = new Pinnacle(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Click Confirm OK Button
        wait.until(ExpectedConditions.elementToBeClickable(page.PinnacleSuccessBetOK));
        page.PinnacleSuccessBetOK.click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Click OK Success Message
        wait.until(ExpectedConditions.elementToBeClickable(page.PinnacleSuccessBetOK));
        page.PinnacleSuccessBetOK.click();
        Thread.sleep(5000);

    }

    BigDecimal actualBalanceAfterBetFinal; BigDecimal balanceAfterbet; String wagerID;
    @And("pinnacle place bet success")
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

        BigDecimal BB = new BigDecimal(balanceBeforeBet);
        BigDecimal BA = new BigDecimal(BetAmount);
        balanceAfterbet = BB.subtract(BA);
        System.out.println("Expected New BB: " + BB );
        System.out.println("Expected New BA: " + BA );
        System.out.println("Expected balance after bet: " + balanceAfterbet);
        Thread.sleep(5000); //delay for checking the wallet broadcast


        var actualBalanceAfterBetTrim = page2.walletBalance.getText().replace(",","");
        actualBalanceAfterBetFinal =  new BigDecimal(actualBalanceAfterBetTrim);
        System.out.println("Actual balance After Bet: " + actualBalanceAfterBetFinal);
        Assert.assertEquals(balanceAfterbet, actualBalanceAfterBetFinal);

    }

    String MyBetswagerID;
    @Then("pinnacle bet ticket is displayed")
    public void pinnacleBetTicketIsDisplayed() {

        Pinnacle page = new Pinnacle(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // verify if iframe is available and switch to that iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page.iFramePinnacle));

        String winHandleBefore = driver.getWindowHandle();

        //Click the Pinnacle My Bets Button
        wait.until(ExpectedConditions.elementToBeClickable(page.PinnacleMyBets));
        page.PinnacleMyBets.click();

        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //get My Bets wager ID
        wait.until(ExpectedConditions.visibilityOfAllElements(page.MyBetsWagerID));
        MyBetswagerID= page.MyBetsWagerID.getText();
        Assert.assertEquals(wagerID, MyBetswagerID);

        driver.close();
        driver.switchTo().window(winHandleBefore);

        reportResult.append("**** PINNACLE BETTING ****" + "%0A" +
                "Scenario: " + scenarioTitle + "%0A" +
                "Username: " + pUsername + "%0A" +
                "Balance before bet: " + balanceBeforeBet + "%0A" +
                "Bet amount: " + BetAmount + "%0A" +
                "Balance after bet: "+balanceAfterbet + "%0A" +
                "Selected Odds: " + odds + "\n");



        String testResult = ("**** PINNACLE BETTING ****" + "\n" +
                "Scenario: " + scenarioTitle + "\n" +
                "Username: " + pUsername + "\n" +
                "Balance before bet: " + balanceBeforeBet + "\n" +
                "Bet amount: " + BetAmount + "\n" +
                "Balance after bet: "+ balanceAfterbet + "\n" +
                "Selected Odds: " + odds + "\n");


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
