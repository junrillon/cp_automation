package steps;

import engine.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.util.Collection;


public class Hooks {

    private final Driver driver;
    public static String sce;

    public Hooks(Driver driver) {
        this.driver = driver;
    }

    @Before()
    public void before(Scenario scenario) {
     Collection<String> tag = scenario.getSourceTagNames();
     sce = scenario.getName();
        System.out.println("Tag: " + tag);
        System.out.println("scenario: " + sce);
        driver.start();
    }

   @After()
   public void after() {
       driver.close();
   }
}