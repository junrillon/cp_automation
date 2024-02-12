package steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrokenLinks {

/*    private static WebDriver driver;

    public BrokenLinks(Driver driver) {
        this.driver = driver.get();
    }*/



    public static void ICheckTheBrokenLinks(WebDriver driver) throws IOException {


            List<WebElement> links=driver.findElements(By.xpath(".//a[contains(@href,'/')]"));
        //  links = driver.findElements(By.tagName("a"));

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
