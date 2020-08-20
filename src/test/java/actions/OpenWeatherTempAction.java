package actions;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class OpenWeatherTempAction extends Utils {
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    JsonPath js;

    public void buildGetRequest(String lat, String lon) throws IOException {

        res = given().spec(requestSpecificationWeather()).queryParam("lat", lat).queryParam("lon", lon).queryParam("daily");
        resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

    }

    public int getResponse() {
        response = res.when().get("/data/2.5/onecall");
        String respString = response.asString();
        js = new JsonPath(respString);
        return response.getStatusCode();
    }

    public boolean validateNextDayTemp(Integer temp) {
        int count = js.getInt("daily.size()");
        float dayTemp = 0;
        for (int i = 0; i < count; i++) {
            java.util.Date date = new java.util.Date(js.getLong("daily[" + i + "].dt") * 1000);
            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
            String dateText = date_format.format(date);
            if (dateText.equalsIgnoreCase(getNextDay())) {
                dayTemp = js.getFloat("daily[" + i + "].temp.day");
                break;
            }
        }
        return dayTemp > temp;
    }

    public String getNextDay() {
        LocalDate dt = LocalDate.now();
        String dayNext = dt.with(TemporalAdjusters.next(DayOfWeek.THURSDAY)).toString();
        return dayNext;
    }
}
