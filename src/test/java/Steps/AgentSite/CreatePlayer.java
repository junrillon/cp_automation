package Steps.AgentSite;

import Base.BaseUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CreatePlayer extends BaseUtil {
    public BaseUtil base;

    public CreatePlayer(BaseUtil base) {
        this.base = base;
    }

    @Given("^I navigate to agent site ([^\"]*)$")
    public void iAccessTheAgentSite(String agUrl)

    {

        //Open Chrome with URL
        base.Driver.navigate().to(agUrl);
        base.Driver.manage().window().maximize();

    }


    @When("I input the agent username ([^\"]*) and password ([^\"]*)$")
    public void iInputTheAgentUsernameAndPassword(String agentUsername,String agentPassword) {

        //Input agent username and password
        Pages.AgentSite.LoginPageAgents page = new Pages.AgentSite.LoginPageAgents(base.Driver);
        page.LoginAgents(agentUsername,agentPassword);
    }

    @And("I click the agent site login button")
    public void iClickTheAgentSiteLoginButton() {
        // Input Captcha Manually
    }

    @Then("I can access the agent dashboard")
    public void iCanAccessTheAgentDashboard() {
        Pages.AgentSite.CreateUserPage page = new Pages.AgentSite.CreateUserPage(base.Driver);

        //Verify if user account is display
        base.Driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        page.agUsernameDisplay();
    }

    @And("I click create user icon")
    public void iClickCreateUserIcon() {
        Pages.AgentSite.CreateUserPage page = new Pages.AgentSite.CreateUserPage(base.Driver);
        page.clickUserIcon();
    }

    @And("I click create customer")
    public void iClickCreateCustomer() {
        Pages.AgentSite.CreateUserPage page = new Pages.AgentSite.CreateUserPage(base.Driver);
        page.clickCreateCustomer();
    }

    @And("I select referral agents")
    public void iSelectReferralAgents() {
        Pages.AgentSite.CreateUserPage page = new Pages.AgentSite.CreateUserPage(base.Driver);
        page.referralAgents();
    }

    @And("I input player {string} ,{string} and {string}")
    public void iInputPlayerAnd(String plUsername, String plPassword, String cfPassword) {
        Pages.AgentSite.CreateUserPage page = new Pages.AgentSite.CreateUserPage(base.Driver);
        page.inputPlayerCreds(plUsername,plPassword,cfPassword);

    }

    @And("I copy the the player username")
    public void iCopyTheThePlayerUsername() {
        Pages.AgentSite.CreateUserPage page = new Pages.AgentSite.CreateUserPage(base.Driver);
        page.copyToClipboard();
    }


    @And("I transfer ([^\"]*) balance to player$")
    public void iTransferBalanceToPlayer(String trFunds) {
        Pages.AgentSite.CreateUserPage page = new Pages.AgentSite.CreateUserPage(base.Driver);
        page.transferFund(trFunds);
    }




    //    @When("I click the submit button")
 //   public void iClickTheSubmitButton() {
 //       Pages.AgentSite.CreateUserPage page = new Pages.AgentSite.CreateUserPage(base.Driver);
 //       page.clickSubmit();
 //   }


    @Then("I should be able to find the created player in Customer List")
    public void iShouldBeAbleToFindTheCreatedPlayerInCustomerList() throws IOException, UnsupportedFlavorException {
        Pages.AgentSite.CreateUserPage page = new Pages.AgentSite.CreateUserPage(base.Driver);
        page.customerListPage();
    }
}
