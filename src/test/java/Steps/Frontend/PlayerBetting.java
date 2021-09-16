package Steps.Frontend;

import Base.BaseUtil;
import Database.DataBaseConnection;
import Pages.Frontend.HomePage;
import Pages.Frontend.LoginPage;
import Pages.WinLossPage;
import com.google.common.collect.Lists;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
            WebDriverWait wait = new WebDriverWait(base.Driver, 3);
            WebElement bannerExitBtn;
            base.Driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            bannerExitBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class = 'fa fa-times-circle fa-2x']")));
            // bannerExitBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(new By.ByCssSelector("i.fa.fa-times-circle.fa-2x")));
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

    @And("i click the login button")
    public void iClickTheLoginButton() {

        LoginPage page = new LoginPage(base.Driver);

        //Get captcha and input captcha
        page.getAndInputCaptcha();

        //Click login button
        page.clickLoginBtn();
    }

    @Then("i can access the homepage")
    public boolean iCanAccessTheHomepage() {

        HomePage page = new HomePage(base.Driver);

        try {
            //Click continue button
            base.Driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            page.clickContinueBtn();
        }
        catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }

        //Verify if user account is display
        base.Driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        page.userAccountDisplay();
        return false;
    }

    @And("i click the pool header button")
    public void iClickThePoolHeaderBtn() {

        //Click pool header button
        HomePage page = new HomePage(base.Driver);
        page.clickPoolBtn();

        // verify if More games is display
        WebDriverWait wait = new WebDriverWait(base.Driver, 5);
        WebElement sportsModal;
        sportsModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("iframe.border-none")));
        base.Driver.switchTo().frame(sportsModal);
        page.selectGameTxtDisplay();

    }

    @And("i click the available sports")
    public void iInputTheDateFilter() {

        HomePage page = new HomePage(base.Driver);

        //click sports
        page.clickLolGame();


    }


    @And("i click the search button")
    public void iClickTheSearchButton() {

        WinLossPage page = new WinLossPage(base.Driver);
        page.Search();
    }


     String rcm1, rcm2, rcm3, rcm4, cm1, cm2, cm3, cm4;
    @And("^i query on the DB if CM is available")
    public void iQueryOnTheDBIfCMIsAvailable(List<String> cmList) throws SQLException {

        //cm1
         cm1 = (cmList.get(0));
        DataBaseConnection db = new DataBaseConnection();
        ResultSet cm1st = db.execDBQuery("CALL DBAdmin.sp_availableCMBets(0,'" + cm1 + "')");
         rcm1 = cm1st.getString("result");
        System.out.println(rcm1);

        //cm2
         cm2 = (cmList.get(1));
        ResultSet cm2nd = db.execDBQuery("CALL DBAdmin.sp_availableCMBets(0,'" + cm2 + "')");
         rcm2 = cm2nd.getString("result");
        System.out.println(rcm2);

        //cm3
         cm3 = (cmList.get(2));
        ResultSet cm3rd = db.execDBQuery("CALL DBAdmin.sp_availableCMBets(0,'" + cm3 + "')");
         rcm3 = cm3rd.getString("result");
        System.out.println(rcm3);

        //cm4
         cm4 = (cmList.get(3));
        ResultSet cm4th = db.execDBQuery("CALL DBAdmin.sp_availableCMBets(0,'" + cm4 + "')");
         rcm4 = cm4th.getString("result");
        System.out.println(rcm4);

    }

    List<String> ac = Lists.newArrayList();
    @And("i see all CM is present")
    public void iSeeAllCMIsPresent() {

        //cm1
        if (rcm1.equals("NULL")) {
            System.out.println("dg is not available");
        } else {
            WinLossPage page = new WinLossPage(base.Driver);
            page.checkCmdg();
            System.out.println(rcm1 + " is displayed");
            ac.add(rcm1);
        }

        //cm2
        if (rcm2.equals("NULL")) {
            System.out.println("gg is not available");
        } else {
            WinLossPage page = new WinLossPage(base.Driver);
            page.checkCmgg();
            System.out.println(rcm2 + " is displayed");
            ac.add(rcm2);
        }

        //cm3
        if (rcm3.equals("NULL")) {
            System.out.println("ggp is not available");
        } else {
            WinLossPage page = new WinLossPage(base.Driver);
            page.checkCmhd();
            System.out.println(rcm3 + " is displayed");
            ac.add(rcm3);
        }

        //cm4
        if (rcm4.equals("NULL")) {
            System.out.println("hd is not available");
        } else {
            WinLossPage page = new WinLossPage(base.Driver);
            page.checkCmks();
            System.out.println(rcm4 + " is displayed");
            ac.add(rcm4);
        }
        System.out.println("Active CM " + ac);
    }

    String cm1Stake, cm2Stake, cm3Stake, cm4Stake;
    @And("i query stake on the DB")
    public void iQueryStakeOnTheDB() throws SQLException{

        //Query cm1 stake
        if(ac.contains(cm1)){
            System.out.println(cm1 + " stake query in progress...");
            DataBaseConnection db = new DataBaseConnection();
            ResultSet rs = db.execDBQuery("CALL DBAdmin.sp_GetCM_TotalStake('"+cm1+"')");
             cm1Stake = rs.getString("Total Stake");
            System.out.println(cm1+" stake from DB " + cm1Stake);
        } else {
            System.out.println("NO stake found for "+ cm1);
        }

        //Query cm2 stake
        if(ac.contains(cm2)){
            System.out.println(cm2+" stake query in progress...");
            DataBaseConnection db = new DataBaseConnection();
            ResultSet rs = db.execDBQuery("CALL DBAdmin.sp_GetCM_TotalStake('"+cm2+"')");
             cm2Stake = rs.getString("Total Stake");
            System.out.println(cm2+" stake from DB " + cm2Stake);
        } else {
            System.out.println("NO stake found for "+ cm2);
        }

        //Query cm3 stake
        if(ac.contains(cm3)){
            System.out.println(cm3+" stake query in progress...");
            DataBaseConnection db = new DataBaseConnection();
            ResultSet rs = db.execDBQuery("CALL DBAdmin.sp_GetCM_TotalStake('"+cm3+"')");
             cm3Stake = rs.getString("Total Stake");
            System.out.println(cm3+" stake from DB " + cm3Stake);
        } else {
            System.out.println("NO stake found for hd "+cm3);
        }

        //Query cm4 stake
        if(ac.contains(cm4)){
            System.out.println(cm4+" stake query in progress...");
            DataBaseConnection db = new DataBaseConnection();
            ResultSet rs = db.execDBQuery("CALL DBAdmin.sp_GetCM_TotalStake('"+cm4+"')");
            cm4Stake = rs.getString("Total Stake");
            System.out.println(cm4+" stake from DB " + cm4Stake);
        } else {
            System.out.println("NO stake found for "+ cm4);
        }
    }

    @And("i see the correct stake for all CM")
    public void iSeeTheCorrectTotalForAllCM() {


        //cm1
        if (rcm1.equals("NULL")) {

            System.out.println("No stake display for " + cm1);

        } else {

            WinLossPage page = new WinLossPage(base.Driver);
            page.dgStake();
            Assert.assertEquals(cm1Stake, page.dgStake());
            System.out.println(rcm1+" Stake from winLoss page " + page.dgStake());
            System.out.println(rcm1+" Stake from DB " + cm1Stake);
        }

        //cm2
        if (rcm2.equals("NULL")) {

            System.out.println("No stake display for " + cm2);

        } else{

            WinLossPage page = new WinLossPage(base.Driver);
            page.dgStake();

            Assert.assertEquals(cm2Stake, page.dgStake());
            System.out.println(rcm2+" Stake from winLoss page " + page.dgStake());
            System.out.println(rcm2+" Stake from DB " + cm2Stake);

            }

        //cm3
        if (rcm3.equals("NULL")) {

            System.out.println("No stake display for " + cm3);

        } else {

            WinLossPage page = new WinLossPage(base.Driver);
            page.hdStake();

            Assert.assertEquals(cm3Stake, page.hdStake());
            System.out.println(rcm3+" Stake from winLoss page " + page.hdStake());
            System.out.println(rcm3+" Stake from DB " + cm3Stake);

        }

        //cm4
        if (rcm4.equals("NULL")) {

            System.out.println("No stake display for " + cm4);

        } else {

            WinLossPage page = new WinLossPage(base.Driver);
            page.ksStake();

            Assert.assertEquals(cm4Stake, page.ksStake());
            System.out.println(rcm4+" Stake from winLoss page" + page.ksStake());
            System.out.println(rcm4+" Stake from DB " + cm4Stake);

        }
    }

    String cm1volume, cm2volume, cm3volume, cm4volume;
    @And("i query volume on the DB")
    public void iQueryVolumeOnTheDB() throws SQLException {

        //Query cm1 stake
        if(ac.contains(cm1)){
            System.out.println(cm1 + " volume query in progress...");
            DataBaseConnection db = new DataBaseConnection();
            ResultSet rs = db.execDBQuery("CALL DBAdmin.sp_GetAgentVolume('"+cm1+"')");
            cm1volume = rs.getString("Total Volume");
            System.out.println(cm1+" volume from DB " + cm1volume);
        } else {
            System.out.println("NO volume found for "+ cm1);
        }

        //Query cm2 stake
        if(ac.contains(cm2)){
            System.out.println(cm2+" volume query in progress...");
            DataBaseConnection db = new DataBaseConnection();
            ResultSet rs = db.execDBQuery("CALL DBAdmin.sp_GetAgentVolume('"+cm2+"')");
            cm2volume = rs.getString("Total Volume");
            System.out.println(cm2+" volume from DB " + cm2volume);
        } else {
            System.out.println("NO volume found for "+ cm2);
        }

        //Query cm3 stake
        if(ac.contains(cm3)){
            System.out.println(cm3+" volume query in progress...");
            DataBaseConnection db = new DataBaseConnection();
            ResultSet rs = db.execDBQuery("CALL DBAdmin.sp_GetAgentVolume('"+cm3+"')");
            cm3volume = rs.getString("Total Volume");
            System.out.println(cm3+" volume from DB " + cm3volume);
        } else {
            System.out.println("NO volume found for hd "+cm3);
        }

        //Query cm4 stake
        if(ac.contains(cm4)){
            System.out.println(cm4+" volume query in progress...");
            DataBaseConnection db = new DataBaseConnection();
            ResultSet rs = db.execDBQuery("CALL DBAdmin.sp_GetAgentVolume('"+cm4+"')");
            cm4volume = rs.getString("Total Volume");
            System.out.println(cm4+" volume from DB " + cm4volume);
        } else {
            System.out.println("NO volume found for "+ cm4);
        }
    }

    String CM1;
    @And("i click CM1")
    public void iClickCM1() {

        WinLossPage page = new WinLossPage(base.Driver);

        int iSwitch = 0; //default value for dg

        if (ac.get(0).equals("gg")){iSwitch = 1;}
        if (ac.get(0).equals("hd")){iSwitch = 2;}
        if (ac.get(0).equals("ks")){iSwitch = 3;}


        System.out.println("switch to "+ iSwitch);
        switch(iSwitch){

            case 0:
                page.clickCmdg();
                System.out.println(rcm1 + " is clicked");

                break;

            case 1:
                page.clickCmgg();
                System.out.println(rcm2 + " is clicked");
                break;

            case 2:
                page.clickCmhd();
                System.out.println(rcm3 + " is clicked");
                break;

            case 3:
                page.clickCmks();
                System.out.println(rcm4 + " is clicked");
                break;

            default:
                System.out.println("Not in the list");
                break;
        }

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

