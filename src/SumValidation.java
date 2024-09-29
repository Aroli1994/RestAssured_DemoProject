import org.testng.Assert;
import org.testng.annotations.Test;

import file.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCourses() {

		System.out.println("\nQ6. Verify if Sum of all Course prices matches with Purchase Amount :: ");

		JsonPath js = new JsonPath(Payload.CoursePrice());
		int count = js.getInt("courses.size()");
		int purchasedAmount = js.getInt("dashboard.purchasedAmount");
		int sum = 0;
		for (int i = 0; i < count; i++) {
			String title = js.get("courses[" + i + "].title");
			int copies = js.getInt("courses[" + i + "].copies");
			int price = js.getInt("courses[" + i + "].price");
			int amount = copies * price;
			System.out.println("amount of " + title + " = " + amount);
			sum = sum + amount;
		}
		System.out.println("actualAmount = " + sum);
		System.out.println("purchasedAmount = " + purchasedAmount);
		Assert.assertEquals(sum, purchasedAmount);
	}

}
