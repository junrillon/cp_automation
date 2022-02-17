import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/Features/Jira",
        plugin = {"pretty"},
        glue = {"Steps"}
)

public class CucumberRunnerTest {
}
