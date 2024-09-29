import org.testng.Assert;

import file.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		JsonPath js = new JsonPath(Payload.CoursePrice());

		System.out.println("\nQ1. Print No of courses returned by API :: ");
		int count = js.getInt("courses.size()");
		System.out.println(count);

		System.out.println("\nQ2.Print Purchase Amount :: ");
		int totalTotal = js.getInt("dashboard.purchasedAmount");
		System.out.println(totalTotal);

		System.out.println("\nQ3. Print Title of the first course :: ");
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);

		System.out.println("\nQ4. Print All course titles and their respective Prices :: ");
		for (int i = 0; i < count; i++) {
			String courseTitle = js.get("courses[" + i + "].title");
			int coursePrice = js.getInt("courses[" + i + "].price");
			System.out.println(courseTitle + " price is  " + coursePrice);
		}

		System.out.println("\nQ5. Print no of copies sold by RPA Course :: ");
		for (int i = 0; i < count; i++) {
			String courseTitle = js.get("courses[" + i + "].title");
			int copies = 0;
			if (courseTitle.equalsIgnoreCase("RPA")) {
				copies = js.getInt("courses[" + i + "].copies");
				System.out.println(courseTitle + " sold " + copies + " copies");
				break;
			}

		}

		System.out.println("\nQ6. Verify if Sum of all Course prices matches with Purchase Amount :: ");
		int purchasedAmount = js.getInt("dashboard.purchasedAmount");
		int actualAmount = 0;
		for (int i = 0; i < count; i++) {
			int copies = js.getInt("courses[" + i + "].copies");
			int price = js.getInt("courses[" + i + "].price");
			actualAmount = actualAmount + copies * price;
		}
		System.out.println("actualAmount = " + actualAmount);
		System.out.println("purchasedAmount = " + purchasedAmount);
		Assert.assertEquals(actualAmount, purchasedAmount);

	}

}
