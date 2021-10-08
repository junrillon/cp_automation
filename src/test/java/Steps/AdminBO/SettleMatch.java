package Steps.AdminBO;

import Base.BaseUtil;
import Database.DataBaseConnection;
import Pages.AdminBO.HomePageAdmin;
import Pages.AdminBO.LoginPageAdmin;
import Pages.AdminBO.MatchDetails;
import Pages.AdminBO.MatchesPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class SettleMatch extends BaseUtil {
    String cMatchStatus;
    private BaseUtil base;
    public SettleMatch(BaseUtil base) {
        this.base = base;
    }

    @Given("^i access admin backoffice login page ([^\"]*)$")
    public void canAccessAdminBackofficeLoginPage(String feUrl){

//        //To check the details of the browser (Version, sessionId, debuggerAddress)
//            Capabilities c = ((HasCapabilities) base.Driver).getCapabilities();
//            System.out.println(c);

//        Dimension dm = base.Driver.manage().window().getSize();
//        int relativemaxwidth = 946;
//
//        //Check if the current window is maximized
//        if(dm.width < relativemaxwidth){
//            //Open Chrome with URL
//            base.Driver.manage().window().maximize();
//            System.out.println("Max");
//
//        }
            //Open Chrome with URL
            base.Driver.navigate().to(feUrl);
    }

    @When("^input the Username ([^\"]*) and Password ([^\"]*)$")
    public void inputTheUsernameAndPassword(String usernameAdmin, String passwordAdmin) {
        //Input username and password
        LoginPageAdmin page = new LoginPageAdmin(base.Driver);
        page.LoginAdmin(usernameAdmin, passwordAdmin);
    }

    @And("click the login button")
    public void clickTheLoginButton() {
        //MANUAL INPUT THE CAPTCHA

    }

    @Then("access the homepage")
    public void canAccessTheHomepage() {
        HomePageAdmin page = new HomePageAdmin(base.Driver);

        //Verify if user account is display
        base.Driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        page.userAccountDisplay();
    }

    @And("click the games header dropdown")
    public void clickTheGamesHeaderDropdown() {
        //Click pool header button
        HomePageAdmin page = new HomePageAdmin(base.Driver);
        page.clickGamesDropdown();

    }

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
//        base.Driver.navigate().to(mdUrl);
//    }

    @And("click matches")
    public void clickMatches() {
        HomePageAdmin page = new HomePageAdmin(base.Driver);

        //click Matches
        page.clickMatches();
    }

    @And("click the search field")
    public void iClickTheSearchButton(DataTable matchDetails) {
        MatchesPage page = new MatchesPage(base.Driver);

        //get the value from feature file
        List<List<String>> data = matchDetails.asLists(String.class);
        String selectedSports = data.get(1).get(0);

        //Wait
        WebDriverWait wait = new WebDriverWait(base.Driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='search']")));

        page.clickSearchField();
        page.searchField.sendKeys(selectedSports);
    }

    @And("verify if has match")
    public void verifyIfHasMatch() throws InterruptedException {
        MatchesPage page = new MatchesPage(base.Driver);
        Thread.sleep(1000);
        if (page.matchPageMatchesTable.getText().contains("No matching records found")) {
            Assert.fail("Datatable is empty, possible no match available.");
        } else {
            System.out.println("Match Found");
        }
    }

    @And("verify if has bets")
    public void verifyIfHasBets(DataTable matchDetails){
        //get the value from feature file
        List<List<Integer>> sportsDetails = matchDetails.asLists(Integer.class);
        int sport_id = sportsDetails.get(1).get(0);
        int league_id = sportsDetails.get(1).get(1);
        int totalBetCount = sportsDetails.get(1).get(2);
        int betSelection1 = sportsDetails.get(1).get(3);
        int betSelection2 = sportsDetails.get(1).get(4);
        int betSelection3 = sportsDetails.get(1).get(5);
        int selectionCount = sportsDetails.get(1).get(6);

        try {
            //get match id
            String sql = "SELECT * FROM p_match WHERE sport_id = "+sport_id+" AND league_id = "+league_id+" order by match_date desc";
            ResultSet p_match = DataBaseConnection.execDBQuery(sql);
            String matchID = p_match.getString("id");

//            get the count for bet selection per sports (to check if sport 2 or 3 selection)
//            String sportSelectionCount = "SELECT COUNT(result_key) AS selectionCount FROM p_bet_selection WHERE sport_id = "+sport_id+"";
//            ResultSet p_bet_selection = DataBaseConnection.execDBQuery(sportSelectionCount);
//            String count = p_bet_selection.getString("selectionCount");

            int totalBets;
            int bpSelection1; int bpSelection2; int bpSelection3;
            int esSelection1 = 0; int esSelection2 = 0; int esSelection3 = 0;
            boolean end = false;

            do{
                //esdev_bet_slip =========================
                String esdevBetSlip = "SELECT count(id) FROM `esdev_bet_slip` WHERE match_id = "+ matchID;
                ResultSet esdev = DataBaseConnection.execDBQuery(esdevBetSlip);
                String esdevResult = esdev.getString("count(id)");
                System.out.println("*esdev_bet_slip: " + esdevResult);

                //esdev_bet_slip bet count per bet selection
                String esdevBC = "SELECT c.label, COUNT(a.bet_selection) AS BetCount " +
                "FROM `esdev_bet_slip` a " +
                "LEFT JOIN `p_sport` b " +
                    "ON a.sport_id = b.id " +
                "LEFT JOIN `p_bet_selection` c " +
                    "ON a.bet_selection = c.result_key " +
                    "AND a.sport_id = c.sport_id " +
                "WHERE a.match_id = "+matchID+" " +
                "GROUP BY a.bet_selection " +
                "ORDER BY a.bet_selection ASC";

                ResultSet rs = DataBaseConnection.execDBQuery(esdevBC);
                if(!rs.next()) {
                    System.out.println("--- No bets available.");
                } else {
                    rs.absolute(1); //<-- row 1
                        String esdevb1 = rs.getString(1);
                        String esdevbc1 = rs.getString(2);
                    rs.absolute(2); //<-- row 2
                        String esdevb2 = rs.getString(1);
                        String esdevbc2 = rs.getString(2);

                    if(selectionCount == 3) {
                        rs.absolute(3); //<-- row 3
                        String esdevb3 = rs.getString(1);
                        String esdevbc3 = rs.getString(2);

                        esSelection3 = Integer.parseInt(esdevbc3);
                        //esdev == Display the count per bet selection
                        System.out.println("--- " + esdevb1 + ": " + esdevbc1 + "" +
                                "\n--- " + esdevb2 + ": " + esdevbc2 + "" +
                                "\n--- " + esdevb3 + ": " + esdevbc3 + "");
                    } else {
                        esSelection1 = Integer.parseInt(esdevbc1);
                        esSelection2 = Integer.parseInt(esdevbc2);
                        //esdev == Display the count per bet selection
                        System.out.println("--- " + esdevb1 + ": " + esdevbc1 + "" +
                                "\n--- " + esdevb2 + ": " + esdevbc2 + "");

                    }
                }

                //bpc_bet_slip ======================
                String bpcBetSlip = "SELECT count(id) FROM `bpc_bet_slip` WHERE match_id = "+ matchID;
                ResultSet bpc = DataBaseConnection.execDBQuery(bpcBetSlip);
                String bpcResult = bpc.getString("count(id)");
                System.out.println("*bpc_bet_slip: " + bpcResult);

                String bpBC = "SELECT c.label, COUNT(a.bet_selection) AS BetCount " +
                        "FROM `bpc_bet_slip` a " +
                        "LEFT JOIN `p_sport` b " +
                        "ON a.sport_id = b.id " +
                        "LEFT JOIN `p_bet_selection` c " +
                        "ON a.bet_selection = c.result_key " +
                        "AND a.sport_id = c.sport_id " +
                        "WHERE a.match_id = "+matchID+" " +
                        "GROUP BY a.bet_selection " +
                        "ORDER BY a.bet_selection ASC";

                ResultSet rs2 = DataBaseConnection.execDBQuery(bpBC);
                if(!rs2.next()) {
                    System.out.println("--- No bets available.");
                } else {
                    rs2.absolute(1); //<-- row 1
                        String bpb1 = rs2.getString(1);
                        String bpbc1 = rs2.getString(2);
                    rs2.absolute(2); //<-- row 2
                        String bpb2 = rs2.getString(1);
                        String bpbc2 = rs2.getString(2);
                    rs2.absolute(3); //<-- row 3
                        String bpb3 = rs2.getString(1);
                        String bpbc3 = rs2.getString(2);

                    bpSelection1 = Integer.parseInt(bpbc1);
                    bpSelection2 = Integer.parseInt(bpbc2);
                    bpSelection3 = Integer.parseInt(bpbc3);
                    //esdev == Display the count per bet selection
                    System.out.println("    " + bpb1 + ": " + bpbc1 + "" +
                            "\n " + bpb2 + ": " + bpbc2 + "" +
                            "\n " + bpb3 + ": " + bpbc3 + "");
                }
                //total bet count of es and bpc
                totalBets = Integer.parseInt(esdevResult) + Integer.parseInt(bpcResult);
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

    @And("view match details")
    public void viewMatchDetails() {
        ///view match details
        MatchesPage page = new MatchesPage(base.Driver);
        page.selectFromActionDrpDown();
    }

    @And("checking the current settlement status and match status")
    public void checkMatchStatus(){

        //view match details
        for(String winHandle : base.Driver.getWindowHandles()){
            base.Driver.switchTo().window(winHandle);
        }

        MatchDetails matchDetails = new MatchDetails(base.Driver);
        //match status is display
        matchDetails.matchStatusDisplay();

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

    @And("closing the match")
    public void closingMatch(){
        MatchDetails matchDetails = new MatchDetails(base.Driver);

            if (cMatchStatus.equalsIgnoreCase("NOT STARTED")) {
                Assert.assertEquals("NOT STARTED", cMatchStatus);
                matchDetails.matchOpenButton();
                matchDetails.matchCloseButton();
                System.out.println("The match is now closed.");

            } else if (cMatchStatus.equalsIgnoreCase("OPEN")) {
                Assert.assertEquals("OPEN", cMatchStatus);
                matchDetails.matchCloseButton();
                System.out.println("The match is now closed.");

            } else {
                Assert.assertEquals("CLOSE", cMatchStatus);
                System.out.println("Set match winner.");
            }
        }

        String winningTeam;
    @And("select winner")
    public void selectWinner() throws InterruptedException {
        Thread.sleep(1000);
        MatchDetails matchDetails = new MatchDetails(base.Driver);
        matchDetails.selectMatchWinner(); //<-Open select match winner modal

       // WebElement mySelectElm = base.Driver.findElement(By.xpath("//div[@id='modal-select-winner']//select[@id='winner']"));
        WebElement mySelectElm = matchDetails.matchSelectWinnerDropdown;

        Select mySelect= new Select(mySelectElm);
        List<String> texts = new ArrayList<>();
        List<WebElement> options = mySelect.getOptions();
            for (WebElement option : options) {
                texts.add(option.getText());
            }
        texts.remove(0); //<-Remove the first index which is "Select Winner"
        Random r = new Random(); //<-Select Random Text/Index in a List<String>
        int selection = r.nextInt(texts.size()); //<-Get the size/length of List<String> Texts
        winningTeam = texts.get(selection); //<-Assign the random selection to the variable winning team
        base.Driver.findElement(By.xpath("//div[@id='modal-select-winner']//select[@id='winner']//option[contains(text(),'"+winningTeam+"')]")).click();
        System.out.println("Bet Selections: " + texts);
        System.out.println("Selected Match Winner: " + winningTeam);

        //Assert if the selected random winning team is not the "Select a Winner" option
        Assert.assertNotEquals("Select Winner", winningTeam);
        matchDetails.confirmMatchWinner(); //<-Confirm match winner selection
    }

    @And("settle match")
    public void settleMatch() throws InterruptedException {
        Thread.sleep(1000);
        MatchDetails matchDetails = new MatchDetails(base.Driver);
        matchDetails.settleMatchButton();

        base.Driver.navigate().refresh();

        //Wait
        WebDriverWait wait = new WebDriverWait(base.Driver, 10);
        //WebElement mdTable = matchDetails.matchDetailsTable;
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@class='table-responsive']//table[@class='table table-striped table-hover table-sm pt-2']"))));
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@id='brandSlider']/div[1]/div/div/div/img)[50]")));
//        element.click();


        String selectedWinner = winningTeam;
        String currentSettleStatus = matchDetails.matchSettleStatusColumn.getText();
        String currentWinningTeam = matchDetails.matchWinnerColumn.getText();
        String currentMatchStatus = matchDetails.matchStatusColumn.getText();

        Assert.assertEquals(selectedWinner, currentWinningTeam);
        Assert.assertEquals("Settled", currentSettleStatus);
        Assert.assertEquals("CLOSE", currentMatchStatus);

    }

}

