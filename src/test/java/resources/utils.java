package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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

public class utils {

	//Declaring Global Varaible.
	public static RequestSpecification req;
	public static ResponseSpecification res;

	public static RequestSpecification requestSpecification() throws IOException
	{
		if(req==null)
		{
			//Create a object for Print Stream class.
			PrintStream log = new PrintStream(new FileOutputStream("Logging.txt"));
			req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseurl")).addQueryParam("key", "qaclick123")
					.addFilter(RequestLoggingFilter.logRequestTo(log)).addFilter(ResponseLoggingFilter.logResponseTo(log))
					.setContentType(ContentType.JSON).build();
		}
		return req;
	}

	public static ResponseSpecification responeSpecification()
	{
		res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		return res;
	}
	
	public static String getGlobalValue(String key) throws IOException
	{
		Properties prop = new Properties();
		
		FileInputStream fis = new FileInputStream("./src/test/java/resources/global.properties");
		
		//integrate both prop and fis.
		
		prop.load(fis);
		
		return prop.getProperty(key);
	}
	
	public String getJsonpath(Response APIresponse,String key)
	{
		String  response=APIresponse.asString();

		//System.out.println(response);

		JsonPath js = new JsonPath(response);
		
		return js.getString(key);
	}

}
