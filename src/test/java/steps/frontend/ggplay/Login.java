package steps.frontend.ggplay;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.frontend.ggplay.LoginGGplay;

import java.util.List;

public class Login {

    private WebDriver driver;
    public static String user;
    public Login(Driver driver) {
        this.driver = driver.get();

    }

    @Given("I logged in on frontend page ([^\"]*)$")
    public void iLoggedInOnFrontend(String url, DataTable loginDetails) throws InterruptedException {
        //Open browser plus url
        driver.get(url);

        try {
            //Click Banner Exit button
            WebDriverWait wait = new WebDriverWait(driver, 20);
            LoginGGplay pageLogin = new  LoginGGplay(driver);

            //  base.Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            wait.until(ExpectedConditions.elementToBeClickable(pageLogin.bannerExitBtn));
            pageLogin.bannerExitBtn.click();
        } catch (org.openqa.selenium.TimeoutException e) {
            return;
        }

        WebDriverWait wait = new WebDriverWait(driver, 20);
        List<List<String>> data = loginDetails.asLists(String.class);

        //Get data table from feature file
        user = data.get(1).get(0);
        String pass = data.get(1).get(1);


        System.out.println("username and password is "+ user + pass);


        //Input username and password
        LoginGGplay pageLogin = new  LoginGGplay(driver);
        pageLogin.Login(user, pass);

        // wait for captcha removal
        pageLogin.getAndInputCaptcha();
        Thread.onSpinWait();
        pageLogin.clickLoginBtn();


        try {

            //Click continue
            wait.until(ExpectedConditions.elementToBeClickable(pageLogin.ContinueSession));
            pageLogin.ContinueSession.click();

        }

        catch (org.openqa.selenium.TimeoutException e)
        {
            return;
        }



    }


}
