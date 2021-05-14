package automatedTests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "com.littleBeasts",
        tags = "@Done"
)
public class DoneAutomatedTests {
}
