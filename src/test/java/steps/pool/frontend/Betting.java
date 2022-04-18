package steps.pool.frontend;

import database.DatabaseConnection;
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
import java.sql.ResultSet;
import java.sql.SQLException;
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


        //get wallet balance display before betting
        wait.until(ExpectedConditions.visibilityOfAllElements(page.walletBalance));
        balanceBeforeBet = page.walletBalance();

        // verify if iframe is available and switch to that iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page.iFramePool));


        //Click the test sports
        wait.until(ExpectedConditions.elementToBeClickable(page.TestSport));
        page.clickTestSport();
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
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(page.selectionA));


        //betting selection
        switch(BetSelection) {
            case 1:
                page.ClickTeamA();
                break;
            case 2:
                page.ClickDraw();
                break;
            case 3:
                page.ClickTeamB();
                break;
            default:
                System.out.println("WRONG SELECTION FORMAT!");
                break;
    }



        //imput bet amount
        wait.until(ExpectedConditions.visibilityOf(page.amountInput));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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

    @And("place bet success")
    public void placeBetSuccess() throws InterruptedException {

        //switch back to dashboard content
        driver.switchTo().defaultContent();

        // Remove comma on balance b4 bet for math operation
        var trimbalB4Bet = balanceBeforeBet.replace(",","");
        var BB = Double.valueOf(trimbalB4Bet);
        var BA = Double.valueOf(BetAmount);

        //balance b4 bet minus bet amount
        var balanceAfterbet = (BB - BA);
        System.out.println("Expected New BB: " + BB );
        System.out.println("Expected New BA: " + BA );

        //set 2 decimal for expected new balance
        var balanceAfterbetFinal = new BigDecimal(balanceAfterbet).setScale(2);
        System.out.println("Expected New balance: " + balanceAfterbetFinal);


        //delay for checking the wallet broadcast
        Thread.sleep(7000);

        Dashboard page = new Dashboard(driver);

        var actualBalanceAfterBetTrim = page.walletBalance().replace(",","");;
        //var actualBalanceAfterBetTrim = actualBalanceAfterBet1.replace(",","");
        var actualBalanceAfterBet = Double.valueOf(actualBalanceAfterBetTrim);
        var actualBalanceAfterBetFinal = new BigDecimal(actualBalanceAfterBet).setScale(2);
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

            Thread.sleep(7000);

            }while(settlementStatus != 2);


        //do while end
             System.out.println("Match is already settled!!");
    }

    @Then("settlement is correct")
    public void settlementIsCorrect() throws SQLException {



        // verify if iframe is available and switch to that iframe
        WebDriverWait wait = new WebDriverWait(driver, 20);
        Dashboard page2 = new Dashboard(driver);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(page2.iFramePool));

        MatchDetails page = new MatchDetails(driver);

        String oddsTeamA = page.getTeamAOdds();
        String oddsTeamB =page.getTeamBOdds();
        String oddsDraw = page.getDrawOdds();


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


      /*  if (BetSelection == Integer.parseInt(matchWinner)){

            System.out.println("I WIN!!!");

        }else{

            System.out.println("I LOSE!!!");
        }*/

        //settlement wallet and  winner checking


        // if odds is canceled
        if(Integer.parseInt(oddsTeamA) < 1.01);
            {
                System.out.println("match is cancel return bet amount and cancelled match is displayed");
               // if match winner is draw
            }  if(Integer.parseInt(matchWinner) == 3);
                        {
                            // if bet selection is draw
                            if(BetSelection == 3)
                                {
                                  System.out.println("you WIN! compute draw odds x bet amount and draw winner is displayed");
                                }
                           // if match winner is same as bet selection
                        }  if(BetSelection == Integer.parseInt(matchWinner));
                                    {
                                        System.out.println("you WIN! compute odds x bet amount and winner is displayed");
                                    }




        }
    }
}
