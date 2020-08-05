Feature: Validating place API
@AddPlace 
Scenario Outline: Verify if place is beign succefully added using AddPlaceAPI
Given Add place Payload "<name>" "<langauage>" "<address>"
When User calls "AddPlaceAPI" with "POST" httpRequest
Then The response got successsfully with status code is 200
And "status" in response is "OK"
And "scope" in response is "APP"
And Verify place_id created maps to "<name>" using "GetPlaceAPI"

Examples:

|name | langauage | address |
|Nisha| Tamil     | Kelamangalam |
#|Hemanth| Telugu     | Hosur |
#|santhosh| Telugu     | Kelamangalam |
#|sathish| Telugu     | Hosur |
@DeletePlace 
Scenario: Verfiy if delete place functionality is working
Given DeletePlace Payload
When User calls "DeletePlaceAPI" with "POST" httpRequest
Then The response got successsfully with status code is 200
And "status" in response is "OK"