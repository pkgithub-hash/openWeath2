package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/FeatureFiles",plugin ="json:target/jsonReports/cucumber-report.json",glue= {"stepDef"})
public class RunCukeTest {
}