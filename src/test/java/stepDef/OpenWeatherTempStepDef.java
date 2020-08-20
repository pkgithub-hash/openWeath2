package stepDef;

import actions.OpenWeatherTempAction;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import resources.Utils;

import java.io.IOException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.junit.Assert.assertEquals;

public class OpenWeatherTempStepDef extends Utils {

    private OpenWeatherTempAction openWeatherTempAction;

    @Given("I like to holiday in Sydney with {string}  {string} only on Thursday")
    public void i_like_to_holiday_in_sydney_with_only_on_thursday(String lat, String lon) {
        if (openWeatherTempAction == null) {
            openWeatherTempAction = new OpenWeatherTempAction();
        }
        try {
            openWeatherTempAction.buildGetRequest(lat, lon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("I look up weather forecast")
    public void i_look_up_weather_forecast() {
        assertEquals(openWeatherTempAction.getResponse(), 200);
        Assert.assertEquals("Some issue with response", 200, openWeatherTempAction.getResponse());
    }

    @Then("I make holiday plan if Temperature is warmer than {int} degree")
    public void i_make_holiday_plan_if_temperature_is_warmer_than_degree(Integer temp) {
        Assert.assertTrue("Temperature is not more than 10 degree", openWeatherTempAction.validateNextDayTemp(temp));
    }

}
