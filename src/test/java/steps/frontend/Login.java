package steps.frontend;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Login {

    private WebDriver driver;
    public static String user;
    public static String userId;

    public Login(Driver driver) {
        this.driver = driver.get();

    }

    @Given("I logged in on frontend page ([^\"]*)$")
    public void iLoggedInOnFrontend(String url, DataTable loginDetails) throws InterruptedException {
        //Open browser plus url
        driver.get(url);

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

        //Thread.sleep(1500);
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

}


//        try {
//            //Click Banner Exit button
//            WebDriverWait wait = new WebDriverWait(driver, 20);
//
//
//            //  base.Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//            wait.until(ExpectedConditions.elementToBeClickable(pageLogin.bannerExitBtn.get(0)));
//            pageLogin.bannerExitBtn.get(0).click();
//
//        } catch (org.openqa.selenium.TimeoutException e) {
//            return;
//
//        }


//        try {
//            //Click continue
//            wait.until(ExpectedConditions.elementToBeClickable(pageLogin.ContinueSession));
//            pageLogin.ContinueSession.click();
//
//        } catch (org.openqa.selenium.TimeoutException e){
//            return;
//        }