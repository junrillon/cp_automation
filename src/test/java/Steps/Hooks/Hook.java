package Steps.Hooks;

import Base.BaseUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.chrome.ChromeDriver;

public class Hook extends BaseUtil {

    private  BaseUtil base;

    public Hook(BaseUtil base){
        this.base = base;


    }


    @Before()
    public void beforeScenarioStart()
    {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        base.Driver = new ChromeDriver();

    }


/*    @After
    public void TearDownTest(Scenario scenario)
    {
        System.out.println("Closing the browser: hook");
    }*/
    }
