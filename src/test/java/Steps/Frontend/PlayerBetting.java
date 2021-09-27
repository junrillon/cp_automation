package Steps.Frontend;

import Base.BaseUtil;
import Database.DataBaseConnection;
import Pages.Frontend.HomePage;
import Pages.Frontend.LoginPage;
import Pages.Frontend.MatchDetails;
import Pages.WinLossPage;
import com.google.common.collect.Lists;
import com.sun.jdi.IntegerValue;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;




public class PlayerBetting extends BaseUtil {

    private BaseUtil base;

    public PlayerBetting(BaseUtil base) {
        this.base = base;
    }


    @Given("^i can access frontend login page ([^\"]*)$")
    public boolean iCanAccessFrontendLoginPage(String feUrl)

    {

        //Open Chrome with URL
        base.Driver.navigate().to(feUrl);
        base.Driver.manage().window().maximize();


        try {

            //Click Banner Exit button
            WebDriverWait wait = new WebDriverWait(base.Driver, 2);
            WebElement bannerExitBtn;
          //  base.Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            bannerExitBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class = 'fa fa-times-circle fa-2x']")));
            bannerExitBtn.click();

            }

        catch (org.openqa.selenium.TimeoutException e)
        {
            return false;
        }

        return false;

    }

    @When("^i input the Username ([^\"]*) and Password ([^\"]*)$")
    public void iInputTheUsernameAndPassword(String username, String password) {

        //Input username and password
        LoginPage page = new LoginPage(base.Driver);
        page.Login(username, password);


    }

    @And("i click the login button to access the betting page")
    public void iClickTheLoginButton() {

        LoginPage page = new LoginPage(base.Driver);

        //Get captcha and input captcha
        page.getAndInputCaptcha();

        //Click login button
        page.clickLoginBtn();
    }


    @Then("i can access the betting page")
    public boolean iCanAccessTheHomepage() throws InterruptedException {

        HomePage page = new HomePage(base.Driver);

        try {
            //Click continue button
            base.Driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            page.clickContinueBtn();
        }
        catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }


        return false;
    }

    @And("i click the pool header button")
    public void iClickThePoolHeaderBtn() {

        //Click pool header button
        //HomePage page = new HomePage(base.Driver);
        //page.clickPoolBtn();


    }
    String balanceBeforeBet;
    @And("i click the available sports")
    public void ICLickTheAvailableSports() throws InterruptedException {

        HomePage page = new HomePage(base.Driver);

        // verify if More games is display
        WebDriverWait wait = new WebDriverWait(base.Driver, 10);
        WebElement sportsModal;
        sportsModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("iframe.border-none")));

        //get wallet balance display
        balanceBeforeBet = page.walletBalance();
        System.out.println(" balance before betting: " + balanceBeforeBet);

        base.Driver.switchTo().frame(sportsModal);
        page.selectGameTxtDisplay();

        //click sports
        Thread.sleep(1000);
        base.Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        page.clickTestSport();

    }

    String BetAmount;
    @And("i select team and input bet amount")
    public void iSelectTeamToPlaceBet(DataTable matchDetails) {

        MatchDetails page = new MatchDetails(base.Driver);

        //get the value list from feature file
        List<List<String>> data = matchDetails.asLists(String.class);
        int BetSelection = Integer.parseInt(data.get(1).get(0));
         BetAmount = data.get(1).get(1);


        switch(BetSelection) {
            case 1:
                page.ClickTeamA();
                break;

            case 2:
                page.ClickTeamB();
                break;

            case 3:
                page.ClickDraw();
                break;

            default:
                System.out.println("WRONG SELECTION FORMAT!");
                break;
        }

        base.Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        page.inputAmount(BetAmount);


    }

    @And("i click place bet button")
    public void iClickPlaceBetButton() {

        MatchDetails page = new MatchDetails(base.Driver);
        page.clickPlaceBetBtn();
    }

    @And("i confirm my place bet")
    public void iConfirmMyPlaceBet() throws InterruptedException {


        MatchDetails page = new MatchDetails(base.Driver);

            Thread.sleep(1000);
            page.clickConfirmPlaceBetBtn();


    }

    @Then("place bet success")
    public void placeBetSuccess() throws NumberFormatException, InterruptedException{



        base.Driver.switchTo().defaultContent();


        // compute new balance after bet
        var trimbalB4Bet = balanceBeforeBet.replace(",","");
        var BB = Double.valueOf(trimbalB4Bet);
        var BA = Double.valueOf(BetAmount);
        var balanceAfterbet = (BB - BA);
        var balanceAfterbet1 = new BigDecimal(balanceAfterbet).setScale(2);
        System.out.println("Expected New balance: " + balanceAfterbet1);


        //get wallet balance display after bet
        Thread.sleep(3000);
        base.Driver.switchTo().defaultContent();
        HomePage page = new HomePage(base.Driver);
        var actualBalanceAfterBet = page.walletBalance();
        actualBalanceAfterBet = balanceBeforeBet.replace(",","");
        System.out.println("Actual balance After Bet: " + actualBalanceAfterBet);


        Assert.assertEquals(balanceAfterbet1, actualBalanceAfterBet);


    }


}

