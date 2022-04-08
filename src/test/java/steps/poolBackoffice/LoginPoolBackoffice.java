package steps.poolBackoffice;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.pool.backoffice.Dashboard;
import pages.pool.backoffice.Login;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LoginPoolBackoffice {

    private WebDriver driver;

    public LoginPoolBackoffice(Driver driver) {
        this.driver = driver.get();


    }

    @Given("I logged in at pool backoffice ([^\"]*)$")
    public void iLoggedInAtPoolBackoffice(String url, DataTable loginDetails) {


        //Open browser plus url
        driver.get(url);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        List<List<String>> data = loginDetails.asLists(String.class);


        //Get data table from feature file
        String user = data.get(1).get(0); String pass = data.get(1).get(1);


        System.out.println("username and passowrd is "+ user + pass);


        //Input username and password
        Login pageLogin = new Login(driver);
        pageLogin.LoginAdmin(user, pass);

        // wait for captcha removal
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);

        //Verify if user account is display
        Dashboard pageDashboard = new Dashboard(driver);
        pageDashboard.userAccountDisplay();


    }


}
