package automatedtests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "com.littleBeasts",
        tags = "" // Add tags you want to test here e.g.: "@StartGame, @AttackPlayerPicksActivity"
)
public class TagCaseTests {
}
