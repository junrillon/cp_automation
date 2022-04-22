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


    StringBuffer reportRsult = new StringBuffer();
    @Given("I click the pool header button")
    public void iClickThePoolHeaderButton() {
        Dashboard page = new Dashboard(driver);  //Click pool header button
        page.EsportsHeaderBtn.click();
    }

    String balanceBeforeBet;
    @When("I click the test sports")
    public void iClickTheAvailableSports() throws InterruptedException {
        Dashboard page = new Dashboard(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfAllElements(page.walletBalance)); //get wallet balance display before betting
        String balanceBeforeBetOrigin = page.walletBalance.getText();
         balanceBeforeBet = balanceBeforeBetOrigin.replace(",","");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page.iFramePool)); // verify if iframe is available and switch to that iframe
        wait.until(ExpectedConditions.elementToBeClickable(page.TestSport)); //Click the test sports
        page.TestSport.click();
        String noMatchAvailable;
        WebDriverWait tryWait = new WebDriverWait(driver, 5);
        try {
            tryWait.until(ExpectedConditions.elementToBeClickable(page.noLivegames));
            noMatchAvailable = String.valueOf(page.noLivegames.isDisplayed());
        }catch (org.openqa.selenium.TimeoutException e){
              return;
        }
                System.out.println("no match prompt " + noMatchAvailable);
        if(noMatchAvailable == "true") {
                do {
                    tryWait.until(ExpectedConditions.elementToBeClickable(page.noLivegames));
                    page.noLivegames.click();
                    tryWait.until(ExpectedConditions.elementToBeClickable(page.TestSport));
                    page.TestSport.click();
                    try {
                        tryWait.until(ExpectedConditions.elementToBeClickable(page.noLivegames));
                        noMatchAvailable = String.valueOf(page.noLivegames.isDisplayed());

                    }
                        catch (org.openqa.selenium.TimeoutException e) {
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
        List<List<String>> data = matchDetails.asLists(String.class); //get the value list from feature file
        BetSelection = Integer.parseInt(data.get(1).get(0));
        BetAmount = data.get(1).get(1);
        WebDriverWait wait = new WebDriverWait(driver, 10); //wait for odds to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(page.selectionA));
        switch(BetSelection) { //betting selection
            case 1:
                page.selectionA.click();
                wait.until(ExpectedConditions.visibilityOf(page.amountInputTeamA));//imput bet amount
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                page.amountInputTeamA.sendKeys(BetAmount);
                wait.until(ExpectedConditions.elementToBeClickable(page.clickSubmitBtnTeamA)); //place bet submit
                page.clickSubmitBtnTeamA.click();
                wait.until(ExpectedConditions.elementToBeClickable(page.confirmPlacebetTeamA)); //confirm place bet
                page.confirmPlacebetTeamA.click();
                break;
            case 2:
                page.selectionB.click();
                wait.until(ExpectedConditions.visibilityOf(page.amountInputTeamB)); //imput bet amount
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                page.amountInputTeamB.sendKeys(BetAmount);
                wait.until(ExpectedConditions.elementToBeClickable(page.clickSubmitBtnTeamB));  //place bet submit
                page.clickSubmitBtnTeamB.click();
                wait.until(ExpectedConditions.elementToBeClickable(page.confirmPlacebetTeamB)); //confirm place bet
                page.confirmPlacebetTeamB.click();
                break;
            case 3:
                page.selectionDraw.click();
                wait.until(ExpectedConditions.visibilityOf(page.amountInputDraw)); //input bet amount
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                page.amountInputDraw.sendKeys(BetAmount);
                wait.until(ExpectedConditions.elementToBeClickable(page.clickSubmitBtnDraw));  //place bet submit
                page.clickSubmitBtnDraw.click();
                wait.until(ExpectedConditions.elementToBeClickable(page.confirmPlacebetDraw)); //confirm place bet
                page.confirmPlacebetDraw.click();
                break;
            default:
                System.out.println("WRONG SELECTION FORMAT!");
                break;


        }
        wait.until(ExpectedConditions.visibilityOfAllElements(page.betSlipCount)); //betslip count is display after betting
    }

    BigDecimal actualBalanceAfterBetFinal; BigDecimal balanceAfterbetFinal;
    @And("place bet success")
    public void placeBetSuccess() throws InterruptedException {
        driver.switchTo().defaultContent(); //switch back to dashboard content
        var BB = Double.valueOf(balanceBeforeBet);
        var BA = Double.valueOf(BetAmount);
        var balanceAfterbet = (BB - BA); //balance b4 bet minus bet amount
        System.out.println("Expected New BB: " + BB );
        System.out.println("Expected New BA: " + BA );
        balanceAfterbetFinal = new BigDecimal(balanceAfterbet).setScale(2); //set 2 decimal for expected new balance
        System.out.println("Expected New balance: " + balanceAfterbetFinal);
        Thread.sleep(5000); //delay for checking the wallet broadcast

        Dashboard page = new Dashboard(driver);
        var actualBalanceAfterBetTrim = page.walletBalance.getText().replace(",","");;
        var actualBalanceAfterBet = Double.valueOf(actualBalanceAfterBetTrim);
        actualBalanceAfterBetFinal =  new BigDecimal(actualBalanceAfterBet).setScale(2);
        System.out.println("Actual balance After Bet: " + actualBalanceAfterBetFinal);
        Assert.assertEquals(balanceAfterbetFinal, actualBalanceAfterBetFinal);

    }

    @And("I wait for the match to settle")
    public void iWaitForTheMatchToSettle() throws SQLException, InterruptedException {
        int settlementStatus;
        do { //check p match if settlement status is already settled
                String getSettlementStatus = "SELECT settlement_status FROM `stage_pool_betting`.`p_match` WHERE sport_id = 83 AND league_id = 191";
                ResultSet settlementResult = DatabaseConnection.execDBQuery(getSettlementStatus);
                settlementStatus = Integer.parseInt(settlementResult.getString("settlement_status"));
                System.out.println("match settlement status is: " + settlementStatus);
                Thread.sleep(5000);
        }while(settlementStatus != 2);
             System.out.println("Match is already settled!!");
    }

    @Then("settlement is correct")
    public void settlementIsCorrect() throws SQLException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Dashboard page2 = new Dashboard(driver);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page2.iFramePool));
        MatchDetails page = new MatchDetails(driver);
        Double oddsTeamA = Double.valueOf(page.teamAOdds.getText());
        Double oddsTeamB = Double.valueOf(page.teamBOdds.getText());
        Double oddsDraw = Double.valueOf(page.drawOdds.getText());
        System.out.println("team selection bet is : " + BetSelection);
        System.out.println("the odds value of team a : " + oddsTeamA);
        System.out.println("the odds value of team b : " + oddsTeamB);
        System.out.println("the odds value of draw : " + oddsDraw);
        String getWinner = "SELECT team_winner FROM `stage_pool_betting`.`p_match` WHERE sport_id = 83 AND league_id = 191 AND settlement_status = 2";
        ResultSet resultValue = DatabaseConnection.execDBQuery(getWinner);
        String matchWinner = resultValue.getString("team_winner");
        System.out.println("match winner : " + matchWinner);
        driver.switchTo().defaultContent(); //switch back to dashboard content
        var actualBalanceAfterSettlementTrim = page2.walletBalance.getText().replace(",",""); //get new wallet balance after settlement
        var actualBalanceAfterSettlement = Double.valueOf(actualBalanceAfterSettlementTrim);
        var actualBalanceAfterSettlementFinal =  new BigDecimal(actualBalanceAfterSettlement).setScale(2);
        var balanceBeforeBetFinal =  new BigDecimal(balanceBeforeBet).setScale(2);
        if(Double.valueOf(oddsTeamA) <= 0.01)  {   // if odds is canceled
            System.out.println("match is cancel return bet amount and cancelled match is displayed");
            System.out.println("balance b4  betting " + balanceBeforeBetFinal);
            System.out.println("expected balance after  cancel " + actualBalanceAfterSettlementFinal);
            Assert.assertEquals(balanceBeforeBetFinal, actualBalanceAfterSettlementFinal);
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page2.iFramePool));
            wait.until(ExpectedConditions.visibilityOfAllElements(page.cancelledBroadcast));
        }
            else if(Integer.parseInt(matchWinner) == 3) {
                if(BetSelection == 3) { //Draw win and bet selection is draw (win amount will add to wallet balance)
                    System.out.println("you WIN! compute draw odds x bet amount and draw winner is displayed");
                    var drawWin = oddsDraw * Double.valueOf(BetAmount); //win amount from draw odds
                    System.out.println("draw win = " + drawWin);
                    var expectedBalance = Double.valueOf(String.valueOf(balanceAfterbetFinal)) + drawWin;
                    System.out.println("balance after settlement  " + actualBalanceAfterSettlementFinal);
                    System.out.println("expected balance after settlement  " + expectedBalance);
                    Assert.assertEquals(BigDecimal.valueOf(expectedBalance).setScale(2), actualBalanceAfterSettlementFinal);
                    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page2.iFramePool));
                    wait.until(ExpectedConditions.visibilityOfAllElements(page.winBroadcast));
                }else { // Draw win but bet selection is team A or B (return bet amount to player)
                    System.out.println("return bet amount and draw winner is displayed");
                    System.out.println("balance b4  betting " + balanceBeforeBetFinal);
                    System.out.println("expected balance after settlement " + actualBalanceAfterSettlementFinal);
                    Assert.assertEquals(balanceBeforeBetFinal, actualBalanceAfterSettlementFinal);
                    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page2.iFramePool));
                    wait.until(ExpectedConditions.visibilityOfAllElements(page.winBroadcast));
                }
            }else if(BetSelection == Integer.parseInt(matchWinner) && Integer.parseInt(matchWinner) != 3)  { //Team A or B win and bet selection is A or B
                if(BetSelection == 1) { // Player win Team A
                    System.out.println("you WIN! compute team A odds x bet amount and winner is displayed");
                    var teamAWin = oddsTeamA * Double.valueOf(BetAmount); //win amount from draw odds
                    System.out.println("team a win = " + teamAWin);
                    var expectedBalance = Double.valueOf(String.valueOf(balanceAfterbetFinal)) + teamAWin;
                    System.out.println("balance after settlement  " + actualBalanceAfterSettlementFinal);
                    System.out.println("expected balance after settlement  " + expectedBalance);
                    Assert.assertEquals(BigDecimal.valueOf(expectedBalance).setScale(2), actualBalanceAfterSettlementFinal);
                    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page2.iFramePool));
                    wait.until(ExpectedConditions.visibilityOfAllElements(page.winBroadcast));
                }else { // Player win Team B
                    System.out.println("you WIN! compute team b odds x bet amount and winner is displayed");
                    var teamBWin = oddsTeamB * Double.valueOf(BetAmount); //win amount from draw odds
                    System.out.println("team a win = " + teamBWin);
                    var expectedBalance = Double.valueOf(String.valueOf(balanceAfterbetFinal)) + teamBWin;
                    System.out.println("balance after settlement  " + actualBalanceAfterSettlementFinal);
                    System.out.println("expected balance after settlement  " + expectedBalance);
                    Assert.assertEquals(BigDecimal.valueOf(expectedBalance).setScale(2), actualBalanceAfterSettlementFinal);
                    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page2.iFramePool));
                    wait.until(ExpectedConditions.visibilityOfAllElements(page.winBroadcast));
                }
            }
            else { //Player lose
                System.out.println("you LOSE! no settlement amount will deduct or return");
                System.out.println("balance after betting " + actualBalanceAfterBetFinal);
                System.out.println("expected balance after Settlement " + actualBalanceAfterSettlementFinal);
                Assert.assertEquals(actualBalanceAfterBetFinal, actualBalanceAfterSettlementFinal);
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page2.iFramePool));
                wait.until(ExpectedConditions.visibilityOfAllElements(page.winBroadcast));
            }



      /*  try {
            URL url = new URL("https://api.telegram.org/bot5325722800:AAESQyezs3QY_7JXY0ZFVn83eQExVfTgYgg/sendMessage?chat_id=-1001766036425&text=bull testing");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println(status + ": " + url);

        } catch (IOException e) {
            e.printStackTrace();

        }*/

        }
    }
