package steps;

import atu.testrecorder.ATUTestRecorder;
import engine.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class Hooks {

    private final Driver driver;
    ATUTestRecorder recorder;
    public static String sce;

    public Hooks(Driver driver) {
        this.driver = driver;
    }

    @Before()
    public void before(Scenario scenario) {
        Collection<String> tags = scenario.getSourceTagNames();
        String scenarioName = scenario.getName();

        if (tags.contains("@Record")) {
            System.out.println("Starting execution of test case with recording: " + scenarioName);
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
            Date date = new Date();

            try {
                recorder = new ATUTestRecorder(System.getProperty("user.dir") + "\\scriptVideos\\",
                        scenarioName + "-" + dateFormat.format(date), false);
            } catch (Exception e) {
                System.out.println("Error in finding the location of the video.");
            }

            try {
                recorder.start();
            } catch (Exception e){
                System.out.println("Error in starting the video.");
            }

            driver.start();
        } else {
            System.out.println("Starting execution of test case without recording: " + scenarioName);
        }

        // Additional setup code for scenarios with and without the @Record tag
        // ...

        driver.start();
    }

   @After("@Record")
    public void after(Scenario scenario){
       Collection<String> tags = scenario.getSourceTagNames();
        if(tags.contains("@Record")) {
            try {
                recorder.stop();

            } catch (Exception e) {
                System.out.println("Unable to stop the screen recording.");
            }
        }

        driver.close();
   }
}