package steps.pool.frontend;

import database.DatabaseConnection;
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
import pages.pool.frontend.MatchDetails;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        WebDriverWait wait = new WebDriverWait(driver, 10);


        //get wallet balance display before betting
        wait.until(ExpectedConditions.visibilityOfAllElements(page.walletBalance));
        String balanceBeforeBetOrigin = page.walletBalance();
         balanceBeforeBet = balanceBeforeBetOrigin.replace(",","");
        //balanceBeforeBet = new BigDecimal(trimbalB4Bet).setScale(2);


        // verify if iframe is available and switch to that iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page.iFramePool));


        //Click the test sports
        wait.until(ExpectedConditions.elementToBeClickable(page.TestSport));
        page.clickTestSport();



        String noMatchAvailable;
          //  do{
        WebDriverWait tryWait = new WebDriverWait(driver, 5);
        try {

                tryWait.until(ExpectedConditions.elementToBeClickable(page.noLivegames));
                noMatchAvailable = String.valueOf(page.noLivegames.isDisplayed());

            }

                catch (org.openqa.selenium.TimeoutException e)
                    {
                        return;
                    }

                System.out.println("no match prompt " + noMatchAvailable);

        if(noMatchAvailable == "true")
            {
                do {
                    tryWait.until(ExpectedConditions.elementToBeClickable(page.noLivegames));
                    page.noLivegames.click();

                    tryWait.until(ExpectedConditions.elementToBeClickable(page.TestSport));
                    page.clickTestSport();


                    try {
                        tryWait.until(ExpectedConditions.elementToBeClickable(page.noLivegames));
                        noMatchAvailable = String.valueOf(page.noLivegames.isDisplayed());

                        }

                        catch (org.openqa.selenium.TimeoutException e)
                            {
                                return;
                            }

                    Thread.sleep(5000);

                    } while(noMatchAvailable == "true");
            }



    }

    String BetAmount; int BetSelection;
    @And("I select team A and input bet amount")
    public void iSelectTeamAndInputBetAmount(DataTable matchDetails) throws InterruptedException {

        MatchDetails page = new MatchDetails(driver);

        //get the value list from feature file
        List<List<String>> data = matchDetails.asLists(String.class);
        BetSelection = Integer.parseInt(data.get(1).get(0));
        BetAmount = data.get(1).get(1);

        //wait for odds to be clickable
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(page.selectionA));


        //betting selection
        switch(BetSelection) {
            case 1:

                page.selectionA.click();

                //imput bet amount
                wait.until(ExpectedConditions.visibilityOf(page.amountInputTeamA));
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                page.amountInputTeamA.sendKeys(BetAmount);

                //place bet submit
                wait.until(ExpectedConditions.elementToBeClickable(page.clickSubmitBtnTeamA));
                page.clickSubmitBtnTeamA.click();

                //confirm place bet
                wait.until(ExpectedConditions.elementToBeClickable(page.confirmPlacebetTeamA));
                page.confirmPlacebetTeamA.click();

                break;
            case 2:

                page.selectionB.click();

                //imput bet amount
                wait.until(ExpectedConditions.visibilityOf(page.amountInputTeamB));
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                page.amountInputTeamB.sendKeys(BetAmount);

                //confirm place bet
                wait.until(ExpectedConditions.elementToBeClickable(page.confirmPlacebetTeamB));
                page.confirmPlacebetTeamB.click();

                break;
            case 3:

                page.selectionDraw.click();

                //imput bet amount
                wait.until(ExpectedConditions.visibilityOf(page.amountInputDraw));
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                page.amountInputDraw.sendKeys(BetAmount);

                //place bet submit
                wait.until(ExpectedConditions.elementToBeClickable(page.clickSubmitBtnDraw));
                page.clickSubmitBtnDraw.click();

                //confirm place bet
                wait.until(ExpectedConditions.elementToBeClickable(page.confirmPlacebetDraw));
                page.confirmPlacebetDraw.click();

                break;
            default:
                System.out.println("WRONG SELECTION FORMAT!");
                break;
    }




    }


    BigDecimal actualBalanceAfterBetFinal; BigDecimal balanceAfterbetFinal;
    @And("place bet success")
    public void placeBetSuccess() throws InterruptedException {

        //switch back to dashboard content
        driver.switchTo().defaultContent();

        // Remove comma on balance b4 bet for math operation
       // var trimbalB4Bet = balanceBeforeBet.replace(",","");
       // var BB = Double.valueOf(balanceBeforeBet); balanceBeforeBet
        var BB = Double.valueOf(balanceBeforeBet);
        var BA = Double.valueOf(BetAmount);

        //balance b4 bet minus bet amount
        var balanceAfterbet = (BB - BA);
        System.out.println("Expected New BB: " + BB );
        System.out.println("Expected New BA: " + BA );

        //set 2 decimal for expected new balance
        balanceAfterbetFinal = new BigDecimal(balanceAfterbet).setScale(2);
        System.out.println("Expected New balance: " + balanceAfterbetFinal);


        //delay for checking the wallet broadcast
        Thread.sleep(5000);

        Dashboard page = new Dashboard(driver);

        var actualBalanceAfterBetTrim = page.walletBalance().replace(",","");;
        //var actualBalanceAfterBetTrim = actualBalanceAfterBet1.replace(",","");
        var actualBalanceAfterBet = Double.valueOf(actualBalanceAfterBetTrim);
         actualBalanceAfterBetFinal =  new BigDecimal(actualBalanceAfterBet).setScale(2);
        System.out.println("Actual balance After Bet: " + actualBalanceAfterBetFinal);

        //assert if bet amount is deducted after betting
        Assert.assertEquals(balanceAfterbetFinal, actualBalanceAfterBetFinal);
    }

    @And("I wait for the match to settle")
    public void iWaitForTheMatchToSettle() throws SQLException, InterruptedException {


        int settlementStatus;

        do {

            //check p match if null
            String getSettlementStatus = "SELECT settlement_status FROM `stage_pool_betting`.`p_match` WHERE sport_id = 83 AND league_id = 191";
            ResultSet settlementResult = DatabaseConnection.execDBQuery(getSettlementStatus);
            settlementStatus = Integer.parseInt(settlementResult.getString("settlement_status"));

            System.out.println("match settlement status is: " + settlementStatus);

            Thread.sleep(5000);

            }while(settlementStatus != 2);


        //do while end
             System.out.println("Match is already settled!!");
    }

    @Then("settlement is correct")
    public void settlementIsCorrect() throws SQLException {

        // verify if iframe is available and switch to that iframe
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Dashboard page2 = new Dashboard(driver);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page2.iFramePool));

        MatchDetails page = new MatchDetails(driver);

        String oddsTeamA = page.teamAOdds.getText();
        String oddsTeamB = page.teamBOdds.getText();
        String oddsDraw = page.drawOdds.getText();


        System.out.println("team selection bet is : " + BetSelection);

      //  System.out.println("my query : " + getSelectionOdds);
      //  System.out.println("team selection bet is : " + BetSelection);
        System.out.println("the odds value of team a : " + oddsTeamA);
        System.out.println("the odds value of team b : " + oddsTeamB);
        System.out.println("the odds value of draw : " + oddsDraw);


        String getWinner = "SELECT team_winner FROM `stage_pool_betting`.`p_match`" +
                " WHERE sport_id = 83 AND league_id = 191 AND settlement_status = 2";
        ResultSet resultValue = DatabaseConnection.execDBQuery(getWinner);
        String matchWinner = resultValue.getString("team_winner");

        System.out.println("match winner : " + matchWinner);



        //settlement wallet and  winner checking   BigDecimal actualBalanceAfterBetFinal; BigDecimal balanceAfterbetFinal;


        // if odds is canceled
        if(Double.valueOf(oddsTeamA) <= 0.01){

            System.out.println("match is cancel return bet amount and cancelled match is displayed");
            wait.until(ExpectedConditions.visibilityOfAllElements(page.cancelledBroadcast));

            //switch back to dashboard content
            driver.switchTo().defaultContent();

            var actualBalanceAfterCancelTrim = page2.walletBalance().replace(",","");
            var actualBalanceAfterCancel = Double.valueOf(actualBalanceAfterCancelTrim);
            var actualBalanceAfterCancelFinal =  new BigDecimal(actualBalanceAfterCancel).setScale(2);

            var balanceBeforeBetFinal =  new BigDecimal(balanceBeforeBet).setScale(2);

            System.out.println("balance b4  betting " + balanceBeforeBetFinal);
            System.out.println("balance after  cancel " + actualBalanceAfterCancelFinal);
            Assert.assertEquals(balanceBeforeBetFinal, actualBalanceAfterCancelFinal);

        }
            else if(Integer.parseInt(matchWinner) == 3) {

                if(BetSelection == 3) {
                    System.out.println("you WIN! compute draw odds x bet amount and draw winner is displayed");
                }else {
                    System.out.println("return bet amount and draw winner is displayed");
                }

            } else if(BetSelection == Integer.parseInt(matchWinner) && Integer.parseInt(matchWinner) != 3){
                // draw win return bets for team a and b

                // if match winner is same as bet selection
                //if(BetSelection == Integer.parseInt(matchWinner)){
                System.out.println("you WIN! compute odds x bet amount and winner is displayed");
                //}
                //   else{

                //}
            }
            else {
                System.out.println("you LOSE!");
            }
        }
    }

