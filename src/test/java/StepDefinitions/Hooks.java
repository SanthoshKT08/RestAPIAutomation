package StepDefinitions;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws Throwable
	{
		
		if(StepDefinition.placeID==null)
		{
		StepDefinition m = new StepDefinition();
		
		m.add_place_payload("pen", "kannada", "bangalore");
		
		m.user_calls_something_with_something_httprequest("AddPlaceAPI", "POST");
		
		m.verify_placeid_created_maps_to_something_using_something("pen", "GetPlaceAPI");
		}
	}

}
