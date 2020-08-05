package resources;

import java.util.ArrayList;
import java.util.List;

import Pojo.AddPlace;
import Pojo.LocationAddPlace;

public class TestDataBuild {
	
	public AddPlace addPlacePayload(String name, String langauage, String address)
	{
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setAddress(address);
		p.setLanguage(langauage);
		p.setName(name);
		p.setWebsite("http://google.com");
		p.setPhone_number("7904122047");

		//Set array things in Types [].
		List<String> mylist = new ArrayList<String>();
		mylist.add("shoe park");
		mylist.add("shop");
		p.setTypes(mylist);

		//set location: its in sub-Class, we neee to create a object for that.
		LocationAddPlace la =new LocationAddPlace();
		la.setLng(33.427362);
		la.setLat(-38.383494);
		//We need pass the variable into parent class.
		p.setLocation(la);
		return p;
	}
	
	public String deletePlacePayload(String placeid)
	{
		return "{\r\n" + 
				"    \"place_id\":\""+placeid+"\"   	\r\n" + 
				"}";
	}

}
