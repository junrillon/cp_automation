package steps.pool.backoffice;

import database.DatabaseConnection;
import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.pool.backoffice.Dashboard;
import pages.pool.backoffice.MatchDetails;
import pages.pool.backoffice.Matches;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SettleMatch {
    private final WebDriver driver;
    public SettleMatch(Driver driver){
        this.driver = driver.get();
    }

    String cMatchStatus;
    public static String winningTeam;
    public static By email;

//    @And("I click the games header dropdown")
//    public void clickTheGamesHeaderDropdown() {
//        //Click pool header button
//        Dashboard page = new Dashboard(driver);
//        page.clickGamesDropdown();
//
//    }

//    String matchID;
//    @And("get match id from DB")
//    public void getMatchIdFromDB() throws SQLException {
//
//        // generate date today
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date referenceDate = new Date();
//        Calendar c = Calendar.getInstance();
//        c.setTime(referenceDate);
//        Date currentDatePlusOne = c.getTime();
//        String dateToday = dateFormat.format(currentDatePlusOne);
//        //System.out.println(dateToday);
//
//        //cm1
//        DataBaseConnection db = new DataBaseConnection();
//        String sql = "SELECT * FROM p_match WHERE sport_id = 2 AND league_id = 2 order by match_date desc ";
//        ResultSet cm1st = db.execDBQuery(sql);
//        matchID = cm1st.getString("id");
//        System.out.println(matchID);
//
//    }

//    @And("redirect to match details")
//    public void redirectToMatchDetails() throws InterruptedException {
//        Thread.sleep(1000);
//        String mdUrl = "https://admin.cpp555.com/match-details/" + matchID;
//        driver.navigate().to(mdUrl);
//    }

    @When("I navigate to matches page")
    public void clickMatches() {
        Dashboard page = new Dashboard(driver);

        //Wait
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(page.matches));
        page.matches.click();

    }

    @When("I click the search field")
    public void iClickTheSearchButton(DataTable matchDetails) {
        Matches page = new Matches(driver);

        //get the value from feature file
        List<List<String>> data = matchDetails.asLists(String.class);
        String selectedSports = data.get(1).get(0);

        //Wait
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(page.searchField));
        wait.until(ExpectedConditions.elementToBeClickable(page.searchField));
        page.searchField.click();
        page.searchField.sendKeys(selectedSports);
    }

    @When("I verify if has match")
    public void verifyIfHasMatch() throws InterruptedException {
        Matches page = new Matches(driver);
        if (page.matchPageMatchesTable.getText().contains("No matching records found")) {
            Assert.fail("Datatable is empty, possible no match available.");
        } else {
            System.out.println("Match Found");
        }
    }

    @When("I verify if has bets")
    public void verifyIfHasBets(DataTable matchDetails) {
        //get the value from feature file
        List<List<String>> sportsDetails = matchDetails.asLists(String.class);
        String sport_id = sportsDetails.get(1).get(0);
        String league_id = sportsDetails.get(1).get(1);
        String xtotalBetCount = sportsDetails.get(1).get(2);
        String xbetSelection1 = sportsDetails.get(1).get(3);
        String xbetSelection2 = sportsDetails.get(1).get(4);
        String xbetSelection3 = sportsDetails.get(1).get(5);

        int totalBetCount = Integer.parseInt(xtotalBetCount);
        int betSelection1 = Integer.parseInt(xbetSelection1);
        int betSelection2 = Integer.parseInt(xbetSelection2);
        int betSelection3 = Integer.parseInt(xbetSelection3);

        try {
            //get match id
            String sql = "SELECT * FROM p_match WHERE `sport_id` = "+sport_id+" AND league_id = "+league_id+" order by created_at desc";
            ResultSet p_match = DatabaseConnection.execDBQuery(sql);
            String matchID = p_match.getString("id");

            //get the count for bet selection per sports (to check if sport 2 or 3 selection)
            String sportSelectionCount = "SELECT COUNT(result_key) AS selectionCount FROM `p_bet_selection` WHERE sport_id = "+sport_id+"";
            ResultSet p_bet_selection = DatabaseConnection.execDBQuery(sportSelectionCount);
            int count = p_bet_selection.getInt("selectionCount");

            int totalBets;
            //int bpSelection1; int bpSelection2; int bpSelection3; <-- bpc_bet_slip
            int esSelection1 = 0; int esSelection2 = 0; int esSelection3 = 0;
            boolean end = false;

            do{
                //esdev_bet_slip =========================
                String ggstgBetSlip = "SELECT count(id) FROM ggstg_bet_slip WHERE match_id = "+ matchID;
                ResultSet ggstg = DatabaseConnection.execDBQuery(ggstgBetSlip);
                String ggstgResult = ggstg.getString("count(id)");
                System.out.println("*ggstg_bet_slip: " + ggstgResult);

                //esdev_bet_slip bet count per bet selection
                String ggStgQry = "SELECT c.label, COUNT(a.bet_selection) AS BetCount " +
                        "FROM ggstg_bet_slip a " +
                        "LEFT JOIN p_sport b ON a.sport_id = b.id " +
                        "LEFT JOIN p_bet_selection c ON a.bet_selection = c.result_key AND a.sport_id = c.sport_id " +
                        "WHERE a.match_id = "+matchID+" " +
                        "GROUP BY a.bet_selection " +
                        "ORDER BY a.bet_selection ASC";

                ResultSet rs = DatabaseConnection.execDBQuery(ggStgQry);
                rs.beforeFirst();

                List<Integer> actualSelectionCount = new ArrayList<>();
                while(rs.next()){
                    String label = rs.getString("label");
                    int xcount = rs.getInt("BetCount");
                    actualSelectionCount.add(xcount);
                    System.out.println("--- " + label + ": " + xcount);

                    if(actualSelectionCount.size() == 1){
                        esSelection1 = actualSelectionCount.get(0);

                    } else if (actualSelectionCount.size() == 2){
                        esSelection1 = actualSelectionCount.get(0);
                        esSelection2 = actualSelectionCount.get(1);

                    } else if (actualSelectionCount.size() == 3) {
                        esSelection1 = actualSelectionCount.get(0);
                        esSelection2 = actualSelectionCount.get(1);
                        esSelection3 = actualSelectionCount.get(2);

                    }
                }

//                //bpc_bet_slip ======================
//                String bpcBetSlip = "SELECT count(id) FROM `bpc_bet_slip` WHERE match_id = "+ matchID;
//                ResultSet bpc = DatabaseConnection.execDBQuery(bpcBetSlip);
//                String bpcResult = bpc.getString("count(id)");
//                System.out.println("*bpc_bet_slip: " + bpcResult);
//
//                String bpBC = "SELECT c.label, COUNT(a.bet_selection) AS BetCount " +
//                        "FROM `bpc_bet_slip` a " +
//                        "LEFT JOIN `p_sport` b " +
//                        "ON a.sport_id = b.id " +
//                        "LEFT JOIN `p_bet_selection` c " +
//                        "ON a.bet_selection = c.result_key " +
//                        "AND a.sport_id = c.sport_id " +
//                        "WHERE a.match_id = "+matchID+" " +
//                        "GROUP BY a.bet_selection " +
//                        "ORDER BY a.bet_selection ASC";
//
//                ResultSet rs2 = DatabaseConnection.execDBQuery(bpBC);
//                if(!rs2.next()) {
//                    System.out.println("--- No bets available.");
//                } else {
//                    rs2.absolute(1); //<-- row 1
//                    String bpb1 = rs2.getString(1);
//                    String bpbc1 = rs2.getString(2);
//                    rs2.absolute(2); //<-- row 2
//                    String bpb2 = rs2.getString(1);
//                    String bpbc2 = rs2.getString(2);
//                    rs2.absolute(3); //<-- row 3
//                    String bpb3 = rs2.getString(1);
//                    String bpbc3 = rs2.getString(2);
//
//                    bpSelection1 = Integer.parseInt(bpbc1);
//                    bpSelection2 = Integer.parseInt(bpbc2);
//                    bpSelection3 = Integer.parseInt(bpbc3);
//                    //esdev == Display the count per bet selection
//                    System.out.println("    " + bpb1 + ": " + bpbc1 + "" +
//                            "\n " + bpb2 + ": " + bpbc2 + "" +
//                            "\n " + bpb3 + ": " + bpbc3 + "");
//                }
                //total bet count of es and bpc
                //totalBets = Integer.parseInt(esdevResult) + Integer.parseInt(bpcResult);.
                totalBets = Integer.parseInt(ggstgResult);
                System.out.println("Bet(s) found in match " + matchID + ": " + totalBets + "\n_______________");
                Thread.sleep(5000);

                //check the total bet count & total bet count per selection
                if(totalBets >= totalBetCount &&
                        esSelection1 >= betSelection1 &&
                        esSelection2 >= betSelection2 &&
                        esSelection3 >= betSelection3){
                    end = true;
                }

            } while(!end);
            System.out.println("Total bet(s) found in match " + matchID + ": " + totalBets);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("I view match details")
    public void viewMatchDetails() {
        //view match details
        Matches page = new Matches(driver);

        //Wait
        WebDriverWait wait = new WebDriverWait(driver, 20) ;
        wait.until(ExpectedConditions.visibilityOf(page.actionDrpdown));

        //Select option with text = "View Match Details"
        Select actionDrpDown = new Select(page.actionDrpdown);
        actionDrpDown.selectByVisibleText("View Match Details");


    }

    @When("I check the current settlement status and match status")
    public void checkMatchStatus(){
        MatchDetails matchDetails = new MatchDetails(driver);

        //view match details
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        //Wait
        WebDriverWait wait = new WebDriverWait(driver, 20) ;
        wait.until(ExpectedConditions.visibilityOf(matchDetails.matchStatusColumn));

        String matchStatus = matchDetails.matchStatusColumn.getText();
        String settleStatus = matchDetails.matchSettleStatusColumn.getText();
        if(!settleStatus.equals("Settled")){
            Assert.assertNotEquals("Settled", settleStatus);
            if (matchStatus.equalsIgnoreCase("NOT STARTED")) {
                Assert.assertEquals("NOT STARTED", matchStatus);
                cMatchStatus = "NOT STARTED";
                System.out.println("Match has not yet started.");

            } else if (matchStatus.equalsIgnoreCase("OPEN")) {
                Assert.assertEquals("OPEN", matchStatus);
                cMatchStatus = "OPEN";
                System.out.println("Match is currently open.");

            } else {
                Assert.assertEquals("CLOSE", matchStatus);
                cMatchStatus = "CLOSE";
                System.out.println("Match is currently closed.");
            }
        } else {
            Assert.fail("No need to settle. The match is already settled.");
        }
    }

    @When("I close the match")
    public void closingMatch(){
        MatchDetails matchDetails = new MatchDetails(driver);

        //wait
        WebDriverWait wait = new WebDriverWait(driver, 20) ;

        if (cMatchStatus.equalsIgnoreCase("NOT STARTED")) {
            Assert.assertEquals("NOT STARTED", cMatchStatus);
            //Open Match
            wait.until(ExpectedConditions.visibilityOf(matchDetails.matchOpenButton));
            wait.until(ExpectedConditions.elementToBeClickable(matchDetails.matchOpenButton));
            matchDetails.matchOpenButton.click();

            wait.until(ExpectedConditions.visibilityOf(matchDetails.openMatchConfirmationModal));

            wait.until(ExpectedConditions.visibilityOf(matchDetails.confirmOpenMatch));
            wait.until(ExpectedConditions.elementToBeClickable(matchDetails.confirmOpenMatch));
            matchDetails.confirmOpenMatch.click();

            wait.until(ExpectedConditions.visibilityOf(matchDetails.openMatchSuccessMessage));
            wait.until(ExpectedConditions.elementToBeClickable(matchDetails.openMatchSuccessModalOkButton));
            matchDetails.openMatchSuccessModalOkButton.click();

            //Close Match
            wait.until(ExpectedConditions.visibilityOf(matchDetails.matchCloseButton));
            wait.until(ExpectedConditions.elementToBeClickable(matchDetails.matchCloseButton));
            matchDetails.matchCloseButton.click();

            wait.until(ExpectedConditions.visibilityOf(matchDetails.closeMatchConfirmationModal));

            wait.until(ExpectedConditions.visibilityOf(matchDetails.confirmCloseMatch));
            wait.until(ExpectedConditions.elementToBeClickable(matchDetails.confirmCloseMatch));
            matchDetails.confirmCloseMatch.click();

            wait.until(ExpectedConditions.visibilityOf(matchDetails.closeMatchSuccessModal));
            wait.until(ExpectedConditions.elementToBeClickable(matchDetails.closeMatchSuccessModalOkButton));
            matchDetails.closeMatchSuccessModalOkButton.click();

            System.out.println("The match is now closed.");


        } else if (cMatchStatus.equalsIgnoreCase("OPEN")) {
            Assert.assertEquals("OPEN", cMatchStatus);

            //Close Match
            wait.until(ExpectedConditions.visibilityOf(matchDetails.matchCloseButton));
            wait.until(ExpectedConditions.elementToBeClickable(matchDetails.matchCloseButton));
            matchDetails.matchCloseButton.click();

            wait.until(ExpectedConditions.visibilityOf(matchDetails.closeMatchConfirmationModal));

            wait.until(ExpectedConditions.visibilityOf(matchDetails.confirmCloseMatch));
            wait.until(ExpectedConditions.elementToBeClickable(matchDetails.confirmCloseMatch));
            matchDetails.confirmCloseMatch.click();

            wait.until(ExpectedConditions.visibilityOf(matchDetails.closeMatchSuccessModal));
            wait.until(ExpectedConditions.elementToBeClickable(matchDetails.closeMatchSuccessModalOkButton));
            matchDetails.closeMatchSuccessModalOkButton.click();
            System.out.println("The match is now closed.");

        } else {
            Assert.assertEquals("CLOSE", cMatchStatus);
            System.out.println("Set match winner.");
        }
    }

    @When("I select winner")
    public void selectWinner() {
        MatchDetails matchDetails = new MatchDetails(driver);
        //Wait
        WebDriverWait wait = new WebDriverWait(driver, 20) ;

        matchDetails.selectMatchWinner(); //<-Open select match winner modal

        //Confirm match winner selection
        wait.until(ExpectedConditions.visibilityOf(matchDetails.matchSelectWinnerButton));
        wait.until(ExpectedConditions.elementToBeClickable(matchDetails.matchSelectWinnerButton));
        matchDetails.matchSelectWinnerButton.click();

        wait.until(ExpectedConditions.visibilityOf(matchDetails.matchSelectWinnerModal));

        wait.until(ExpectedConditions.visibilityOf(matchDetails.matchSelectWinnerDropdown));
        wait.until(ExpectedConditions.elementToBeClickable(matchDetails.matchSelectWinnerDropdown));
        matchDetails.matchSelectWinnerDropdown.click();
        //---Confirm match winner selection

        WebElement mySelectElm = matchDetails.matchSelectWinnerDropdown;

        Select mySelect= new Select(mySelectElm);
        List<String> betSelections = new ArrayList<>();
        List<WebElement> options = mySelect.getOptions();
        for (WebElement option : options) {
            betSelections.add(option.getText());
        }

        betSelections.remove(0); //<-Remove the first index which is "Select Winner"
        Random r = new Random(); //<-Select Random Text/Index in a List<String>
        int selection = r.nextInt(betSelections.size()); //<-Get the size/length of List<String> Texts
        winningTeam = betSelections.get(selection); //<-Assign the random selection to the variable winning team

        driver.findElement(By.xpath("//div[@id='modal-select-winner']//select[@id='winner']//option[contains(text(),'"+winningTeam+"')]")).click();

        System.out.println("Bet Selections: " + betSelections);
        System.out.println("Selected Match Winner: " + winningTeam);

        //Assert if the selected random winning team is not the "Select a Winner" option
        Assert.assertNotEquals("Select Winner", winningTeam);

        //Confirm match winner selection
        wait.until(ExpectedConditions.visibilityOf(matchDetails.matchSelectWinnerSubmitButton));
        wait.until(ExpectedConditions.elementToBeClickable(matchDetails.matchSelectWinnerSubmitButton));
        matchDetails.matchSelectWinnerSubmitButton.click();

        wait.until(ExpectedConditions.visibilityOf(matchDetails.matchSelectWinnerModalConfirmation));

        wait.until(ExpectedConditions.visibilityOf(matchDetails.matchSelectWinnerSubmitButton2));
        wait.until(ExpectedConditions.elementToBeClickable(matchDetails.matchSelectWinnerSubmitButton2));
        matchDetails.matchSelectWinnerSubmitButton2.click();

        wait.until(ExpectedConditions.visibilityOf(matchDetails.matchSelectWinnerSuccessMessage));
        wait.until(ExpectedConditions.elementToBeClickable(matchDetails.matchSelectWinnerSuccessModalOkButton));
        matchDetails.matchSelectWinnerSuccessModalOkButton.click();

    }

    @Then("I settle match")
    public void settleMatch() {
        MatchDetails matchDetails = new MatchDetails(driver);
        //Wait
        WebDriverWait wait = new WebDriverWait(driver, 20) ;

        //Click settle button
        wait.until(ExpectedConditions.visibilityOf(matchDetails.settleMatchButton));
        wait.until(ExpectedConditions.elementToBeClickable(matchDetails.settleMatchButton));
        matchDetails.settleMatchButton.click();

        wait.until(ExpectedConditions.visibilityOf(matchDetails.settleMatchConfirmationMessage));

        wait.until(ExpectedConditions.visibilityOf(matchDetails.settleMatchConfirmationYesButton));
        wait.until(ExpectedConditions.elementToBeClickable(matchDetails.settleMatchConfirmationYesButton));
        matchDetails.settleMatchConfirmationYesButton.click();

        wait.until(ExpectedConditions.visibilityOf(matchDetails.settleMatchSuccessMessage));
        wait.until(ExpectedConditions.elementToBeClickable(matchDetails.settleMatchSuccessModalOkButton));
        matchDetails.settleMatchSuccessModalOkButton.click();

        driver.navigate().refresh();

        //Wait for match details table
        wait.until(ExpectedConditions.visibilityOf(matchDetails.matchDetailsTable));

        //Variables for the match details
        String selectedWinner = winningTeam;
        String currentSettleStatus = matchDetails.matchSettleStatusColumn.getText();
        String currentWinningTeam = matchDetails.matchWinnerColumn.getText();
        String currentMatchStatus = matchDetails.matchStatusColumn.getText();

        //Wait until current winning team to be present in match winner column
        wait.until(ExpectedConditions.textToBePresentInElement(matchDetails.matchWinnerColumn, currentWinningTeam));
        Assert.assertEquals(selectedWinner, currentWinningTeam);
        System.out.println("Selected Winner: " + selectedWinner + " | Actual Winner: " + currentWinningTeam);

        //Wait until current match status to be present in match status column
        wait.until(ExpectedConditions.textToBePresentInElement(matchDetails.matchStatusColumn, "CLOSE"));
        Assert.assertEquals("CLOSE", currentMatchStatus);
        System.out.println("Expected Match Status: CLOSE | Actual Match Status: " + currentWinningTeam);

        //Wait until current settlement status to be present in settlement status column
        wait.until(ExpectedConditions.textToBePresentInElement(matchDetails.matchSettleStatusColumn, "Settled"));
        Assert.assertEquals("Settled", currentSettleStatus);
        System.out.println("Expected Settlement Status: Settled | Actual Settlement Status: " + currentSettleStatus);

    }
    
    
}
