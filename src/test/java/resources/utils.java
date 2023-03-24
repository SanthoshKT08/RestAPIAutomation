package resources;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

public class utils {

    public static RequestSpecification req;
    public static ResponseSpecification res;
    public static PrintStream logs;

    public static RequestSpecification requestSpecification() throws IOException {
        if (req == null) {
            //Create a object for Print Stream class.
            PrintStream log = new PrintStream(new FileOutputStream("Logging.txt"));
            req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseurl")).addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log)).addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
        }
        return req;
    }

    public static ResponseSpecification responeSpecification() {
        res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        return res;
    }

    public static String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("./src/test/java/resources/global.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public String getJsonpath(Response APIresponse, String key) {
        String response = APIresponse.asString();
        JsonPath js = new JsonPath(response);
        return js.getString(key);
    }

    public RequestSpecification requestSpecificationExtension() throws IOException {
        if (req == null) {
            //Create a object for Print Stream class.
            logs = new PrintStream(new FileOutputStream("ExtensionLogs.txt"));
            req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseurl")).addFilter(RequestLoggingFilter.logRequestTo(logs)).addFilter(ResponseLoggingFilter.logResponseTo(logs)).setContentType(ContentType.JSON).build();
        }
        return req;
    }

    public ResponseSpecification responseSpecification() {
        res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        return res;
    }

    public Map<String, String> addHeaders() {
        Map<String, String> headers = new HashMap<>();
        return headers;
    }

    public RequestSpecBuilder requestSpecBuilder() {
        return new RequestSpecBuilder();
    }

    public RequestSpecification _given(Object payload) throws IOException {
        if (logs == null) {
            try {
                logs = new PrintStream(new FileOutputStream("ExtensionLogs.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return requestSpecBuilder().setBaseUri(getGlobalValue("baseurl")).addFilter(RequestLoggingFilter.logRequestTo(logs)).addFilter(ResponseLoggingFilter.logResponseTo(logs)).setContentType(ContentType.JSON).setBody(payload).build();
    }

    public RequestSpecification _given() throws IOException {
        if (logs == null) {
            try {
                logs = new PrintStream(new FileOutputStream("ExtensionLogs.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return requestSpecBuilder().setBaseUri(getGlobalValue("baseurl")).addFilter(RequestLoggingFilter.logRequestTo(logs)).addFilter(ResponseLoggingFilter.logResponseTo(logs)).setContentType(ContentType.JSON).build();
    }

    public RequestSpecification _given(Object payload, Map<String, String> headers, Map<String, String> queryPara) throws IOException {
        if (logs == null) {
            try {
                logs = new PrintStream(new FileOutputStream("ExtensionLogs.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return requestSpecBuilder().setBaseUri(getGlobalValue("baseurl")).addFilter(RequestLoggingFilter.logRequestTo(logs)).addFilter(ResponseLoggingFilter.logResponseTo(logs)).setContentType(ContentType.JSON).setBody(payload).addHeaders(headers).addQueryParams(queryPara).build();
    }

    public RequestSpecification _given(Object payload, Map<String, String> headers) throws IOException {
        if (logs == null) {
            try {
                logs = new PrintStream(new FileOutputStream("ExtensionLogs.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return requestSpecBuilder().setBaseUri(getGlobalValue("baseurl")).addFilter(RequestLoggingFilter.logRequestTo(logs)).addFilter(ResponseLoggingFilter.logResponseTo(logs)).setContentType(ContentType.JSON).setBody(payload).addHeaders(headers).build();
    }

    public RequestSpecification _given(Map<String, String> headers, Map<String, String> queryPara) throws IOException {
        if (logs == null) {
            try {
                logs = new PrintStream(new FileOutputStream("ExtensionLogs.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return requestSpecBuilder().setBaseUri(getGlobalValue("baseurl")).addFilter(RequestLoggingFilter.logRequestTo(logs)).addFilter(ResponseLoggingFilter.logResponseTo(logs)).setContentType(ContentType.JSON).addHeaders(headers).addQueryParams(queryPara).build();
    }

    /**
     * This method is used to Hit API and get Json Response in String for [POST,GET,DELETE,OPTIONS,PUT]
     */
    public String requestAPI(String methodType, Object payload, String basePath, int statusCode) throws IOException {
        RequestSpecification requestSpecification = null;
        switch (methodType) {

            case "POST":
                requestSpecification = _given(payload);
                return given().spec(requestSpecification).when().post(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();

            case "GET":
                requestSpecification = _given();
                return given().spec(requestSpecification).when().get(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();

            case "DELETE":
                requestSpecification = _given(payload);
                return given().spec(requestSpecification).when().delete(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();

            case "OPTIONS":
                requestSpecification = _given(payload);
                return given().spec(requestSpecification).when().options(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();

            case "PUT":
                requestSpecification = _given(payload);
                given().spec(requestSpecification).when().put(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();
        }
        return "[No Http Method are not found]";
    }

    /**
     * This method is used to Hit API and get Json Response in String for [POST,GET,DELETE,OPTIONS,PUT]
     */
    public String requestAPI(String methodType, Object payload, Map<String, String> headers, Map<String, String> queryPara, String basePath, int statusCode) throws IOException {
        RequestSpecification requestSpecification = null;
        switch (methodType) {

            case "POST":
                requestSpecification = _given(payload, headers, queryPara);
                return given().spec(requestSpecification).when().post(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();

            case "GET":
                requestSpecification = _given(headers, queryPara);
                return given().spec(requestSpecification).when().get(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();

            case "DELETE":
                requestSpecification = _given(payload, headers, queryPara);
                return given().spec(requestSpecification).when().delete(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();

            case "OPTIONS":
                requestSpecification = _given(payload, headers, queryPara);
                return given().spec(requestSpecification).when().options(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();

            case "PUT":
                requestSpecification = _given(payload, headers, queryPara);
                return given().spec(requestSpecification).when().put(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();
        }
        return "[No Http Method are not found]";
    }

    /**
     * This method is used to Hit API and get Json Response in String for [POST,GET,DELETE,OPTIONS,PUT]
     */

    public String requestAPI(String methodType, Object payload, Map<String, String> headers, String basePath, int statusCode) throws IOException {
        RequestSpecification requestSpecification = null;
        switch (methodType) {

            case "POST":
                requestSpecification = _given(payload, headers);
                return given().spec(requestSpecification).when().post(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();

            case "GET":
                requestSpecification = _given(headers);
                return given().spec(requestSpecification).when().get(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();

            case "DELETE":
                requestSpecification = _given(payload, headers);
                return given().spec(requestSpecification).when().delete(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();

            case "OPTIONS":
                requestSpecification = _given(payload, headers);
                return given().spec(requestSpecification).when().options(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();

            case "PUT":
                requestSpecification = _given(payload, headers);
                return given().spec(requestSpecification).when().put(basePath)
                        .then().assertThat().statusCode(statusCode).extract().asString();
        }
        return "[No Http Method are not found]";
    }

    /**
     * This method is used to Hit API and get Response for [POST,GET,DELETE,OPTIONS,PUT]
     */
    public Response getResponse(String methodType, Object payload, String basePath) throws IOException {
        RequestSpecification requestSpecification = null;
        switch (methodType) {

            case "POST":
                requestSpecification = _given(payload);
                return given().spec(requestSpecification).when().post(basePath);

            case "GET":
                requestSpecification = _given(payload);
                return given().spec(requestSpecification).when().get(basePath);

            case "DELETE":
                requestSpecification = _given(payload);
                return given().spec(requestSpecification).when().delete(basePath);

            case "OPTIONS":
                requestSpecification = _given(payload);
                return given().spec(requestSpecification).when().options(basePath);

            case "PUT":
                requestSpecification = _given(payload);
                return given().spec(requestSpecification).when().put(basePath);
        }
        return null;
    }

    /**
     * This method is used to Hit API and get Response for [POST,GET,DELETE,OPTIONS,PUT]
     */
    public Response getResponse(String methodType, Object payload, Map<String, String> headers, Map<String, String> queryPara, String basePath, int statusCode) throws IOException {
        RequestSpecification requestSpecification = null;
        switch (methodType) {

            case "POST":
                requestSpecification = _given(payload, headers, queryPara);
                return given().spec(requestSpecification).when().post(basePath);

            case "GET":
                requestSpecification = _given(headers, queryPara);
                return given().spec(requestSpecification).when().get(basePath);

            case "DELETE":
                requestSpecification = _given(payload, headers, queryPara);
                return given().spec(requestSpecification).when().delete(basePath);

            case "OPTIONS":
                requestSpecification = _given(payload, headers, queryPara);
                return given().spec(requestSpecification).when().options(basePath);

            case "PUT":
                requestSpecification = _given(payload, headers, queryPara);
                return given().spec(requestSpecification).when().put(basePath);
        }
        return null;
    }

    /**
     * This method is used to Hit API and get Response for [POST,GET,DELETE,OPTIONS,PUT]
     */
    public Response response(String methodType, Object payload, Map<String, String> headers, String basePath) throws IOException {
        RequestSpecification requestSpecification = null;
        switch (methodType) {

            case "POST":
                requestSpecification = _given(payload, headers);
                return given().spec(requestSpecification).when().post(basePath);

            case "GET":
                requestSpecification = _given(headers);
                return given().spec(requestSpecification).when().get(basePath);

            case "DELETE":
                requestSpecification = _given(payload, headers);
                return given().spec(requestSpecification).when().delete(basePath);

            case "OPTIONS":
                requestSpecification = _given(payload, headers);
                return given().spec(requestSpecification).when().options(basePath);

            case "PUT":
                requestSpecification = _given(payload, headers);
                return given().spec(requestSpecification).when().put(basePath);
        }
        return null;
    }

    public static void validateResponse(Response response, int expectedStatusCode, String expectedContentType, String jsonFinder, String expectedValue) {
        int statusCode = response.getStatusCode();
        if (statusCode != expectedStatusCode) {
            throw new AssertionError("Unexpected status code: " + statusCode + ". Expected: " + expectedStatusCode);
        }
        String contentType = response.header("Content-Type");
        if (!contentType.equals(expectedContentType)) {
            throw new AssertionError("Unexpected Content-Type: " + contentType + ". Expected: " + expectedContentType);
        }
        String res = response.asString();
        JsonPath js = new JsonPath(res);
        if (!js.getString(jsonFinder).equals(expectedValue)) {
            throw new AssertionError("Unexpected Value: " + js.getString(jsonFinder) + ". Expected: " + expectedValue);
        }
    }

    public JsonPath json(String value) {
        return new JsonPath(value);
    }

    /**
     * This method is used to get json value.
     */
    public String getJsonValue(String res, String data) {
        return json(res).getString(data);
    }

    /**
     * This method is used to get the size of the Json.
     */
    public int getJsonSize(String res, String data) {
        return json(res).getInt(data);
    }

}
