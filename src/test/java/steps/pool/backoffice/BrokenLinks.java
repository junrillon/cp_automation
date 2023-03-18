package steps.pool.backoffice;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.pool.backoffice.Dashboard;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BrokenLinks {

    private WebDriver driver;

    public BrokenLinks(Driver driver) {
        this.driver = driver.get();

    }
    @Given("I check the broken links")
    public void ICheckTheBrokenLinks() throws IOException {

    List<WebElement> links=driver.findElements(By.xpath("//a[contains(@href,'/')]"));

    for(WebElement link : links) {
        String url=link.getAttribute("href");
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("HEAD");
        con.connect();

        int respcode = con.getResponseCode();

        if(respcode>=400){
       // System.out.println("11111 " + respcode);

            System.out.println("link text: "+ url +" response code:"+ respcode);

           // driver.quit();
        }



    }


    }

}
