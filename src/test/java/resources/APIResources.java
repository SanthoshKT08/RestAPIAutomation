package resources;

public enum APIResources {
	
    //Define methods like this syntax..
	
	AddPlaceAPI("/maps/api/place/add/json"),
	GetPlaceAPI("/maps/api/place/get/json"),
	DeletePlaceAPI("/maps/api/place/delete/json");
	
	
	public String resource;
	
	APIResources(String resource) 
	{
       this.resource=resource;
	}
	
	public String getResource()
	{
		return resource;
	}

}
