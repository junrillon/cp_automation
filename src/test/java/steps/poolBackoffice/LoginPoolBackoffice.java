package steps.poolBackoffice;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.poolBackofficeObjects.LoginPoolBackofficeObjects;

import java.util.List;

public class LoginPoolBackoffice {

    private WebDriver driver;

    public LoginPoolBackoffice(Driver driver) {
        this.driver = driver.get();


    }

    @Given("i logged in at pool backoffice ([^\"]*)$")
    public void iLoggedInAtPoolBackoffice(String url, DataTable loginDetails) {


        //Open browser plus url
        driver.get(url);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        List<List<String>> data = loginDetails.asLists(String.class);


        //Get data table from feature file
        String user = data.get(1).get(0); String pass = data.get(1).get(1);


        System.out.println("username and passowrd is "+ user + pass);


        //Input username and password
        LoginPoolBackofficeObjects page = new LoginPoolBackofficeObjects(driver);
        page.LoginAdmin(user, pass);


    }


}
