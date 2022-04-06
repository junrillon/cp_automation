package steps.agentSite;

import base.BaseUtil;
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
        pages.agentSite.LoginPageAgents page = new pages.agentSite.LoginPageAgents(base.Driver);
        page.LoginAgents(agentUsername,agentPassword);
    }

    @And("I click the agent site login button")
    public void iClickTheAgentSiteLoginButton() {
        // Input Captcha Manually
    }

    @Then("I can access the agent dashboard")
    public void iCanAccessTheAgentDashboard() {
        pages.agentSite.CreateUserPage page = new pages.agentSite.CreateUserPage(base.Driver);

        //Verify if user account is display
        base.Driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        page.agUsernameDisplay();
    }

    @And("I click create user icon")
    public void iClickCreateUserIcon() {
        pages.agentSite.CreateUserPage page = new pages.agentSite.CreateUserPage(base.Driver);
        page.clickUserIcon();
    }

    @And("I click create customer")
    public void iClickCreateCustomer() {
        pages.agentSite.CreateUserPage page = new pages.agentSite.CreateUserPage(base.Driver);
        page.clickCreateCustomer();
    }

    @And("I select referral agents")
    public void iSelectReferralAgents() {
        pages.agentSite.CreateUserPage page = new pages.agentSite.CreateUserPage(base.Driver);
        page.referralAgents();
    }

    @And("I input player {string} ,{string} and {string}")
    public void iInputPlayerAnd(String plUsername, String plPassword, String cfPassword) {
        pages.agentSite.CreateUserPage page = new pages.agentSite.CreateUserPage(base.Driver);
        page.inputPlayerCreds(plUsername,plPassword,cfPassword);

    }

    @And("I copy the the player username")
    public void iCopyTheThePlayerUsername() {
        pages.agentSite.CreateUserPage page = new pages.agentSite.CreateUserPage(base.Driver);
        page.copyToClipboard();
    }


    @And("I transfer ([^\"]*) balance to player$")
    public void iTransferBalanceToPlayer(String trFunds) {
        pages.agentSite.CreateUserPage page = new pages.agentSite.CreateUserPage(base.Driver);
        page.transferFund(trFunds);
    }




    @When("I click the submit button")
    public void iClickTheSubmitButton() {
        pages.agentSite.CreateUserPage page = new pages.agentSite.CreateUserPage(base.Driver);
        page.clickSubmit();
    }


    @Then("I should be able to find the created player in Customer List")
    public void iShouldBeAbleToFindTheCreatedPlayerInCustomerList() throws IOException, UnsupportedFlavorException {
        pages.agentSite.CreateUserPage page = new pages.agentSite.CreateUserPage(base.Driver);
        page.customerListPage();
    }
}
