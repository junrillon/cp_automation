package steps;

import engine.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.Node;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;


import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import static java.lang.Thread.*;


public class Hooks {

    private Driver driver;
    public static String scenarioName;
    private Scenario scenario;

    public Hooks(Driver driver) {
        this.driver = driver;
    }

    @Before()
    public void before(Scenario scenario) {
     Collection<String> tag = scenario.getSourceTagNames();
         scenarioName = scenario.getName();
        System.out.println("Tag: " + tag);
        System.out.println("scenario: " + scenarioName);
        driver.start();

    }

   @After()
   public void after() {


    //  driver.close();
   }
}