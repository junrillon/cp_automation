package Steps.Hooks;

import Base.BaseUtil;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hook extends BaseUtil {

    private  BaseUtil base;
    public Hook(BaseUtil base){
        this.base = base;
    }

    private static WebDriver Driver;
    public static WebDriver getDriver(){
        return Driver;
    }
    public static void setWebDriver(WebDriver driver){
        Driver = driver;
    }


    @Before()
    public void beforeScenarioStart() {
        System.setProperty("webdriver.chrome.driver", "jars/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/usr/bin/google-chrome"); //<--- newly added
        options.addArguments("--headless"); //<--- newly added
        options.addArguments("--no-sandbox"); //<--- newly added
        options.addArguments("--remote-debugging-port=9223");
        options.addArguments("--start-maximized");
        //options.addArguments("--auto-open-devtools-for-tabs");
        WebDriver browser = new ChromeDriver(options);
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9223");
        base.Driver = new ChromeDriver(options);
    }

}
