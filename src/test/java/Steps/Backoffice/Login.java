package Steps.Backoffice;

import Base.BaseUtil;
import Pages.Backoffice.Backoffice;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class Login {
    private BaseUtil base;
    public Login(BaseUtil base) {
        this.base = base;
    }

    @Given("^I access b2c backoffice ([^\"]*)$")
    public void IAccessB2cBackoffice(String feUrl){
        Backoffice page = new Backoffice(base.Driver);

    }

    @When("^input the Username ([^\"]*) and Password ([^\"]*)$")
    public void inputTheUsernameAndPassword(String usernameAdmin, String passwordAdmin) {
        //Input username and password
        Backoffice page = new Backoffice(base.Driver);
        page.inputCredentials(usernameAdmin, passwordAdmin);
    }
}
