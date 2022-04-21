package engine;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;


public class Driver {
    private WebDriver driver;


    public void start() {
        driver = new GoogleChromeDriver();
        driver.manage().deleteAllCookies();
      //  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public WebDriver get() {
        return this.driver;
    }

    public void close() {
        driver.quit();
    }
}