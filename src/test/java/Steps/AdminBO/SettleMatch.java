package Steps.AdminBO;

import Base.BaseUtil;
import Pages.AdminBO.HomePageAdmin;
import Pages.AdminBO.LoginPageAdmin;
import Pages.AdminBO.MatchDetails;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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

    @Given("^access admin backoffice login page ([^\"]*)$")
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

    @Then("can access the homepage")
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

        //matches button is display
        page.MatchesDisplay();
    }

    @And("^redirect to match details ([^\"]*)")
    public void redirectToMatchDetails(String mdUrl) {
        base.Driver.navigate().to(mdUrl);
    }

//    @And("click matches")
//    public void clickMatches() {
//        HomePageAdmin page = new HomePageAdmin(base.Driver);
//
//        //click Matches
//        page.clickMatches();
//    }
//
//    @And("click the search field")
//    public void iClickTheSearchButton() {
//        HomePageAdmin page = new HomePageAdmin(base.Driver);
//        page.clickSearchField();
//    }
//
//    @And("^input the match ID ([^\"]*)")
//    public void iInputTheMatchId(String matchId) {
//        //Input username and password
//        HomePageAdmin page = new HomePageAdmin(base.Driver);
//        page.enterMatchId(matchId);
//    }

    @And("checking the current match status")
    public void checkMatchStatus(){
        MatchDetails matchDetails = new MatchDetails(base.Driver);
        //match status is display
        matchDetails.matchStatusDisplay();

        String matchStatus = matchDetails.matchStatus.getText();
            if(matchStatus.equals("NOT STARTED")){
                cMatchStatus = "NOT STARTED";
                System.out.println("Match has not yet started.");

            } else if(matchStatus.equals("OPEN")) {
                cMatchStatus = "OPEN";
                System.out.println("Match is currently open.");

            } else {
                cMatchStatus = "CLOSED";
                System.out.println("Match is currently closed.");
            }
        }

    @And("closing the match")
    public void closingMatch() throws InterruptedException {
        MatchDetails matchDetails = new MatchDetails(base.Driver);
            if(cMatchStatus.equalsIgnoreCase("NOT STARTED")){
                Thread.sleep(1500);
                matchDetails.matchOpenButton();
                matchDetails.matchCloseButton();
                System.out.println("The match is now open.");
            } else if(cMatchStatus.equalsIgnoreCase("OPEN")){
                Thread.sleep(1500);
                matchDetails.matchCloseButton();
                System.out.println("The match is now closed.");
            } else {
//                Thread.sleep(1500);
//                page.selectMatchWinner();
                System.out.println("Set match winner.");
            }
        }

    @And("select winner")
    public void selectWinner() throws InterruptedException {
        Thread.sleep(1500);
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
        String winningTeam = texts.get(selection); //<-Assign the random selection to the variable winning team
        base.Driver.findElement(By.xpath("//div[@id='modal-select-winner']//select[@id='winner']//option[contains(text(),'"+winningTeam+"')]")).click();
        System.out.println(texts + ": " + winningTeam);

        matchDetails.confirmMatchWinner(); //<-Confirm match winner selection
        }

    }

