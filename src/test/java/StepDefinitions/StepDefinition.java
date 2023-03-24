package StepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.runner.RunWith;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.utils;


@RunWith(Cucumber.class)
public class StepDefinition extends utils {


    RequestSpecification req;
    ResponseSpecification res;
    RequestSpecification sendreq;//Make it Global variables, So you can access anywhere in methods.
    Response APIresponse;
    TestDataBuild data = new TestDataBuild();
    static String placeID;
    APIResources resouceAPI;


    @Given("Add place Payload {string} {string} {string}")
    public void add_place_payload(String name, String langauage, String address) throws IOException {


        sendreq = given().spec(requestSpecification()).body(data.addPlacePayload(name, langauage, address));
    }


    @When("^User calls \"([^\"]*)\" with \"([^\"]*)\" httpRequest$")
    public void user_calls_something_with_something_httprequest(String resource, String httprequest) throws Throwable {


        resouceAPI = APIResources.valueOf(resource);
        System.out.println(resouceAPI.getResource());

        if (httprequest.equalsIgnoreCase("POST")) {
            APIresponse = sendreq.when().post(resouceAPI.getResource())
                    .then().spec(responeSpecification()).extract().response();
        } else if (httprequest.equalsIgnoreCase("GET")) {
            APIresponse = sendreq.when().get(resouceAPI.getResource())
                    .then().spec(responeSpecification()).extract().response();
        }


    }


    @Then("^The response got successsfully with status code is 200$")
    public void the_response_got_successsfully_with_status_code_is_200() throws Throwable {


        assertEquals(APIresponse.getStatusCode(), 200);
    }


    @And("^\"([^\"]*)\" in response is \"([^\"]*)\"$")
    public void something_in_response_is_something(String Key, String Value) throws Throwable {


        assertEquals(getJsonpath(APIresponse, Key), Value);


    }

    @And("^Verify place_id created maps to \"([^\"]*)\" using \"([^\"]*)\"$")
    public void verify_placeid_created_maps_to_something_using_something(String expectedname, String resource) throws Throwable {

        placeID = getJsonpath(APIresponse, "place_id");

        sendreq = given().spec(requestSpecification()).queryParam("place_id", placeID);

        user_calls_something_with_something_httprequest(resource, "GET");

        String actualName = getJsonpath(APIresponse, "name");

        assertEquals(actualName, expectedname);

    }

    @Given("^DeletePlace Payload$")
    public void deleteplace_payload() throws Throwable {

        sendreq = given().spec(requestSpecification()).body(data.deletePlacePayload(placeID));

    }

}
