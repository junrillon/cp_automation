package Steps.AdminBO;

import Base.BaseUtil;
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
    public void canAccessAdminBackofficeLoginPage(String feUrl) {
        //Open Chrome with URL
        base.Driver.navigate().to(feUrl);
        base.Driver.manage().window().maximize();
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

        //get the value list from feature file
        List<List<String>> data = matchDetails.asLists(String.class);
        String selectedSports = data.get(1).get(0);

        page.clickSearchField();
        page.searchField.sendKeys(selectedSports);
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
    public void closingMatch() throws InterruptedException {
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

        WebElement mySelectElm = base.Driver.findElement(By.xpath("//div[@id='modal-select-winner']//select[@id='winner']"));
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
        WebElement mdTable = matchDetails.matchDetailsTable;
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@class='table-responsive']//table[@class='table table-striped table-hover table-sm pt-2']"))));

        String selectedWinner = winningTeam.toString();
        String currentSettleStatus = matchDetails.matchSettleStatusColumn.getText();
        String currentWinningTeam = matchDetails.matchWinnerColumn.getText();
        String currentMatchStatus = matchDetails.matchStatusColumn.getText();

        Assert.assertEquals(selectedWinner, currentWinningTeam);
        Assert.assertEquals("Settled", currentSettleStatus);
        Assert.assertEquals("CLOSE", currentMatchStatus);

    }
}

