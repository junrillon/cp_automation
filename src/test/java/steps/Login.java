package steps;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.aams.Navigation;
import pages.aams.AuditLogin;
import pages.agents.AgentLoginPage;

import java.util.List;

public class Login {

    private final WebDriver driver;
    private final AgentLoginPage agentLoginPage;
    private final AuditLogin auditLogin;
    private final Navigation navigation;

    public static String user;
    public static String www_url;

    public Login(Driver driver) {
        this.driver = driver.get();
        this.agentLoginPage = new AgentLoginPage(this.driver);
        this.auditLogin = new AuditLogin(this.driver);;
        this.navigation = new Navigation(this.driver);

    }

    @Given("I am logged in on frontend {string}")
    public void iLogInOnFrontend(String url, DataTable loginDetails) throws InterruptedException {
        //Open browser plus url
        driver.get(url);

        www_url = url.replace("https://","")
                     .replace("/login","");

        WebDriverWait wait = new WebDriverWait(driver, 20);
        pages.frontend.ggplay.Login pageLogin = new pages.frontend.ggplay.Login(driver);

        //Check if banner exit button is present

        int ageVerificationModal = pageLogin.ageVerificationModal.size();

        if(ageVerificationModal > 0) {
            System.out.println("Age Verification Modal Visible");
            wait.until(ExpectedConditions.visibilityOf(pageLogin.ageVerificationButton.get(0)));
            wait.until(ExpectedConditions.elementToBeClickable(pageLogin.ageVerificationButton.get(0)));
            pageLogin.ageVerificationButton.get(0).click();
        }

        Thread.sleep(1500);
        int bannerExitBtn = pageLogin.bannerExitBtn.size();
        if(bannerExitBtn > 0){
            System.out.println("Banner Modal Visible");
            wait.until(ExpectedConditions.elementToBeClickable(pageLogin.bannerExitBtn.get(0)));
            pageLogin.bannerExitBtn.get(0).click();

        }

        //Get data table from feature file
        List<List<String>> data = loginDetails.asLists(String.class);
        user = data.get(1).get(0);
        String pass = data.get(1).get(1);

        System.out.println("username and password is "+ user +", "+ pass);

        //Input username and password
        wait.until(ExpectedConditions.visibilityOf(pageLogin.txtUserName));
        pageLogin.Login(user, pass);

        // wait for captcha removal
        pageLogin.getAndInputCaptcha();
        pageLogin.clickLoginBtn();

        //Check if continue session alert is present
        Thread.sleep(500);
        int ContinueSession = pageLogin.AlertModal.size();

        if(ContinueSession > 0){
            wait.until(ExpectedConditions.elementToBeClickable(pageLogin.ContinueSession));
            pageLogin.ContinueSession.click();
        }

    }

    @Given("I am logged in on agent {string}")
    public void iLogInOnAgent(String url, DataTable loginDetails){
        //Get data table from feature file
        List<List<String>> data = loginDetails.asLists(String.class);
        String agentUsername = data.get(1).get(0);
        String agentPassword = data.get(1).get(1);

        //Open browser plus url
        driver.get(url);

        //Enter credentials then login
        agentLoginPage.loginInAgentSite(agentUsername,agentPassword);

        //Assert if already logged in.
        String homeUrl = driver.getCurrentUrl();
        Assert.assertTrue(homeUrl.contains("home"));

    }

    @Given("I login on audit page {string}")
    public void iLoginOnAuditPage(String url, DataTable loginDetails){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        //Get data table from feature file
        List<List<String>> data = loginDetails.asLists(String.class);
        String email = data.get(1).get(0);
        String password = data.get(1).get(1);

        //Open browser plus url
        driver.get(url);

        //Enter credentials then login
        auditLogin.inputCredentials(email, password);

        //Click Sent OTP button
        auditLogin.requestOtp();

        //Click login
        auditLogin.clickLogin();

        //Wait for the page load
        wait.until(ExpectedConditions.visibilityOf(navigation.navigation));

        //Assert if already logged in.
        String homeUrl = driver.getCurrentUrl();
        System.out.println(homeUrl);
        Assert.assertTrue(homeUrl.contains("dashboard"));

    }

}