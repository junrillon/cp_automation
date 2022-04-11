package steps.pool.frontend;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.pool.backoffice.Dashboard;
import pages.frontend.ggplay.LoginGGplay;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Login {

    private WebDriver driver;

    public Login(Driver driver) {
        this.driver = driver.get();


    }


    @Given("I logged in on frontend page ([^\"]*)$")
    public void iLoggedInOnFrontendPageHttpsStagingGgplayCo(String url, DataTable loginDetails) throws InterruptedException {


        //Open browser plus url
        driver.get(url);


        try {

            //Click Banner Exit button
            WebDriverWait wait = new WebDriverWait(driver, 2);
            WebElement bannerExitBtn;
            //  base.Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            bannerExitBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class = 'fa fa-times-circle fa-2x']")));
            bannerExitBtn.click();

        }

        catch (org.openqa.selenium.TimeoutException e)
        {
            return;
        }


        WebDriverWait wait = new WebDriverWait(driver, 20);
        List<List<String>> data = loginDetails.asLists(String.class);


        //Get data table from feature file
        String user = data.get(1).get(0); String pass = data.get(1).get(1);


        System.out.println("username and passowrd is "+ user + pass);


        //Input username and password
        LoginGGplay pageLogin = new  LoginGGplay(driver);
        pageLogin.Login(user, pass);

        // wait for captcha removal
        pageLogin.getAndInputCaptcha();
        Thread.sleep(1000);
        pageLogin.clickLoginBtn();





    }


}
