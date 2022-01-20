package io.cucumber.skeleton;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class StepDefinitions {
    public static final String HTTP_API_OPENWEATHERMAP_ORG_DATA_2_5_WEATHER = "http://api.openweathermap.org/data/2.5/weather";
    public static final String API_KEY = Helper.GET_API_KEY();
    public static final String UNITS = "metric";
    private final Map<String, Float> dutchCityTemp = new HashMap<>();
    private final Map<String, Float> southEuropeCityTemp = new HashMap<>();


    @Given("that i'm in a city in The Netherlands")
    public void that_i_m_in_a_city_in_the_netherlands() {
        System.out.println("Hello FOSDEM! ");
    }

    public static Response doGetRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;
        return
                given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                        when().get(endpoint).
                        then().contentType(ContentType.JSON).extract().response();
    }
    public String getUrl(String city){
        return String.format("%s?q=%s&appid=%s&units=%s",
                HTTP_API_OPENWEATHERMAP_ORG_DATA_2_5_WEATHER, city, API_KEY, UNITS);
    }

    @When("I check the current weather")
    public void i_check_the_current_weather() {
        final String city = "Rotterdam";
        Response response = doGetRequest(getUrl(city));
        dutchCityTemp.put(city, response.jsonPath().get("main.temp"));
    }
    @Then("the weather in a southern European country should be better")
    public void the_weather_in_a_southern_european_country_should_be_better() {
        final String city = "Rome";
        Response response = doGetRequest(getUrl(city));
        southEuropeCityTemp.put(city, response.jsonPath().get("main.temp"));

        assertThat("Should be warmer in this Country ! ",
                southEuropeCityTemp.get(city) > dutchCityTemp.get("Rotterdam"));
    }

}