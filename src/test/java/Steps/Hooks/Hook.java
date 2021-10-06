package Steps.Hooks;

import base.BaseUtil;
import io.cucumber.java.Before;
import org.openqa.selenium.chrome.ChromeDriver;

public class Hook extends BaseUtil {

    private  BaseUtil base;

    public Hook(BaseUtil base){
        this.base = base;
    }


    @Before()
    public void beforeScenarioStart() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
      //  ChromeOptions options = new ChromeOptions();
     //   options.addArguments("--remote-debugging-port=9223");
      //  options.addArguments("--start-maximized");
       // WebDriver browser = new ChromeDriver(options);
       // options.setExperimentalOption("debuggerAddress", "127.0.0.1:9223");
        base.Driver = new ChromeDriver();
    }


/*    @After
    public void TearDownTest(Scenario scenario)
    {
        System.out.println("Closing the browser: hook");
    }*/
}