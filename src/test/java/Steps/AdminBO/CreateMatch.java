package Steps.AdminBO;

import Base.BaseUtil;
import Database.DataBaseConnection;
import Pages.AdminBO.HomePageAdmin;
import Pages.AdminBO.LoginPageAdmin;
import Pages.AdminBO.MatchesPage;
import Pages.Frontend.HomePage;
import Pages.Frontend.LoginPage;
import Pages.WinLossPage;
import com.google.common.collect.Lists;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class CreateMatch extends BaseUtil {

    private BaseUtil base;

    public CreateMatch(BaseUtil base) {
        this.base = base;
    }


    @Given("^i can access admin backoffice login page ([^\"]*)$")
    public void iCanAccessAdminBackofficeLoginPage(String feUrl)

    {

        //Open Chrome with URL
        base.Driver.navigate().to(feUrl);
        base.Driver.manage().window().maximize();

    }

    @When("^i input the Username ([^\"]*) and Password ([^\"]*)$")
    public void iInputTheUsernameAndPassword(String usernameAdmin, String passwordAdmin) {

        //Input username and password
        LoginPageAdmin page = new LoginPageAdmin(base.Driver);
        page.LoginAdmin(usernameAdmin, passwordAdmin);


    }

    @And("i click the login button")
    public void iClickTheLoginButton() {

        //MANUAL INPUT THE CAPTCHA

    }

    @Then("i can access the homepage")
    public void iCanAccessTheHomepage() {

        HomePageAdmin page = new HomePageAdmin(base.Driver);

        //Verify if user account is display
        base.Driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        page.userAccountDisplay();
    }

    @And("i click the games header dropdown")
    public void iClickTheGamesHeaderDropdown() {

        //Click pool header button
        HomePageAdmin page = new HomePageAdmin(base.Driver);
        page.clickGamesDropdown();

        //matches button is display
        page.MatchesDisplay();


    }

    @And("i click matches")
    public void iClickMatches() {

        //click Matches
        HomePageAdmin page = new HomePageAdmin(base.Driver);
        page.clickMatches();

        // create match display
        MatchesPage page2 = new MatchesPage(base.Driver);
        page2.createMatchDisplay();

    }


    @And("i click the create match button")
    public void iClickTheCreateMatchBtn() {

        // create match display
        MatchesPage page = new MatchesPage(base.Driver);
        page.clickCreateMatch();

        page.sportDropDownDisplay();
    }


    @And("^i input match details")
    public void iSelectSports(DataTable matchDetails){

        List<List<String>> data = matchDetails.asLists(String.class);
        String selectedSports = data.get(1).get(0);
        String selectedLeague = data.get(1).get(1);
        String matchCountInput = data.get(1).get(2);

        MatchesPage page = new MatchesPage(base.Driver);
        page.selectSports(selectedSports);
        page.selectLeague(selectedLeague);
        page.inputMatchCount(matchCountInput);

    }

    List<String> ac = Lists.newArrayList();
    @And("i see all CM is present")
    public void iSeeAllCMIsPresent() {


    }

    String cm1Stake, cm2Stake, cm3Stake, cm4Stake;
    @And("i query stake on the DB")
    public void iQueryStakeOnTheDB() throws SQLException{


    }

    @And("i see the correct stake for all CM")
    public void iSeeTheCorrectTotalForAllCM() {



    }

    String cm1volume, cm2volume, cm3volume, cm4volume;
    @And("i query volume on the DB")
    public void iQueryVolumeOnTheDB() throws SQLException {


    }


    @And("i click CM1")
    public void iClickCM1() {


    }

    @And("i see all agents underling")
    public void iSeeAllAgentsUnderling() throws SQLException {


        DataBaseConnection db = new DataBaseConnection();
        ResultSet rs = db.execDBQuery("CALL DBAdmin.sp_availableCMBets(2,'dg')");


        int count = rs.getInt(1);
        System.out.println(count);

        rs.next();
        cm2Stake = rs.getString("agent_username");
        //System.out.println(cm2Stake);
    }


}

