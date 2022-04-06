package steps.adminBO;

import base.BaseUtil;
import pages.adminBO.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    @When("^i input the admin Username ([^\"]*) and Password ([^\"]*)$")
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


    }

    @And("i click matches")
    public void iClickMatches() {

        //click Matches
        HomePageAdmin page = new HomePageAdmin(base.Driver);
        page.clickMatches();


    }


    @And("i click the create match button")
    public void iClickTheCreateMatchBtn() {

        // create match display
        MatchesPage page = new MatchesPage(base.Driver);
        page.clickCreateMatch();

    }


    @And("^i input match details")
    public void iSelectSports(DataTable matchDetails){

        //get the value list from feature file
        List<List<String>> data = matchDetails.asLists(String.class);
        String selectedSports = data.get(1).get(0);
        String selectedLeague = data.get(1).get(1);
        String matchCountInput = data.get(1).get(2);

        //input match details
        MatchesPage page = new MatchesPage(base.Driver);
        page.selectSports(selectedSports);
        page.selectLeague(selectedLeague);

        // generate date today
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date referenceDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(referenceDate);
        Date currentDatePlusOne = c.getTime();
        String dateToday = dateFormat.format(currentDatePlusOne);

      //  System.out.println("111111111111111" + dateToday);


        page.inputDate(dateToday);

        page.inputMatchCount(matchCountInput);

    }


    @And("i click submit button")
    public void iClickSubmitBtn() {

        //click submit button
        MatchesPage page = new MatchesPage(base.Driver);
        page.clickSubmitBtn();

    }


    @And("i select view match details from action dropdown")
    public void iSelectViewMatchDetails() {

        //view match details
        MatchesPage page = new MatchesPage(base.Driver);
        page.selectFromActionDrpDown();

    }

    @Then("i click open bet status")
    public void iClickOpenBetStatus() {

        //view match details
        for(String winHandle : base.Driver.getWindowHandles()){
            base.Driver.switchTo().window(winHandle);
        }
        MatchDetails page = new MatchDetails(base.Driver);
        page.matchOpenButton();

    }



}

