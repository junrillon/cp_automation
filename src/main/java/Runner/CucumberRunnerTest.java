package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src\\main\\java\\Features\\Jira\\Login.feature",
        plugin = {"pretty"},
        glue = {"Steps"}
        )
public class CucumberRunnerTest {
}
