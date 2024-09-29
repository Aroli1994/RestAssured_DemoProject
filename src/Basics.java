import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import file.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basics {
	public static void main(String[] args) throws IOException {
		//validate if AddPlace API is working as expected
		//rest assured works on 3 principles or methods -> given, when, then
		
		//given method() -> takes all input details for an API
		//when method() -> submit the API with all details - resource, http method
		//then method() -> validate the response
		
		//content of the file to String -> content of file convert into Byte(datatype in java) -> Byte data into String
		
		//AddPlace -> UpdatePlace with new address -> GetPlace to validate if new address is present in response
		
		//AddPlace
		System.out.println("\n*************** ADD PLACE ***************************\n");
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		//.body(Payload.AddPlacePayload())
		.body(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "//AddPlace.json"))))
		.when().post("maps/api/place/add/json")
		.then().log().all().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = ReUsableMethods.rawToJson(response);  //for parsing Json
		String placeId = js.getString("place_id");
		
		System.out.println(placeId);
		
		//UpdatePlace
		System.out.println("\n*************** UPDATE PLACE ***************************\n");
		String newAddress = "70 winter walk, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\n"
				+ "    \"place_id\": \""+placeId+"\",\n"
				+ "    \"address\": \""+newAddress+"\",\n"
				+ "    \"key\": \"qaclick123\"\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//GetPlace
		System.out.println("\n*************** GET PLACE ***************************\n");
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println("new address :: "+actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
		//Cucumber JUnit, TestNG
	}
}
