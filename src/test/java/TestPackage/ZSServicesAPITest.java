package TestPackage;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.*;

import LibraryFile.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
public class ZSServicesAPITest implements ConstantVaribales {
	Properties prp;

	@BeforeClass
	public void commonService() throws IOException
	{
		prp = ReusableMethodsClass.getPropertyFile();
	}

	@Test
	public void Get() {
			//BaseURL or Host
			RestAssured.baseURI = prp.getProperty("HOST1");
			given().
			when().
					get("/"+prp.getProperty("PORT1")+"/"+prp.getProperty("QA1API")+"/cohort/my_cohorts").

			then().assertThat().
					statusCode(STATUS_OK).and().contentType(ContentType.JSON).and().
					body("status", equalTo("success")).and().
					body("message",equalTo("success")).log().all();

	}
}
