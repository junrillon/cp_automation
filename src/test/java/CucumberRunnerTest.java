import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true,
        plugin = {"pretty"},
        features = "src/test/resources/pool/backoffice",
        glue = "steps",
        tags = "@regression"
)

public class CucumberRunnerTest {
}