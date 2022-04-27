package steps;

import engine.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.Node;

import java.util.Collection;


public class Hooks {

    private Driver driver;

    public Hooks(Driver driver) {
        this.driver = driver;
    }

    @Before()
    public void before(Scenario scenario) {
     Collection<String> tag = scenario.getSourceTagNames();
     String sdf = scenario.getName();


        System.out.print("Tag: " + tag);
        System.out.print("scenario: " + sdf);
        driver.start();
    }

    @After()
   public void after() {
        driver.close();
    }
}