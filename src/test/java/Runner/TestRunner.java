package Runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.testng.annotations.DataProvider;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = { "src/test/java/Features/Frontend/BettingParallel" },
        glue = {"Steps" })
public class TestRunner {


    @DataProvider(parallel = true)
    public Object[][] scenarios() {

        return scenarios();
    }
}


