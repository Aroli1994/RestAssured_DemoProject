package file;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().header("Content-Type","application/json")
		.body(Payload.AddBook(isbn, aisle))
		.when().post("Library/Addbook.php")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
	} 
	
	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		//array = collection of elements
		//multidimensional = collection of arrays
		Object[][] object = new Object[][] {{"ajsaw","1212"}, {"ajsww","1222"}, {"ajqaw","1112"}};
		return object;
	}

}
