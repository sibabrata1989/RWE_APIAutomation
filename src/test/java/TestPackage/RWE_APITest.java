package TestPackage;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.*;

import LibraryFile.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
public class RWE_APITest implements ConstantVaribales {
	Properties prp;
	String payload;

	@BeforeClass
	public void commonService() throws IOException
	{
		prp = ReusableMethodsClass.getPropertyFile();
	}

	private void preConditionSet(String host,String port, String env, String payLoadPath) throws IOException
	{
		payload = ReusableMethodsClass.generateStringFromResources(payLoadPath);
		prp = ReusableMethodsClass.getPropertyFile();
		RestAssured.baseURI= prp.getProperty(host)+"/"+prp.getProperty(port)+"/"+prp.getProperty(env);
	}

	@Test
	public void getMyCohorts() {
		try{
		
			preConditionSet("HOST1","PORT1","QA1API","");
			
			given().
					header("Content-Type","application/json").
			when().
					get("/cohort/my_cohorts").

			then().assertThat().
					statusCode(STATUS_OK).and().contentType(ContentType.JSON).and().
					body(STATUS, equalTo("success")).and().
					body(MESSAGE,equalTo("success")).log().all();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail("Execution failed due to exception in try block!");
		}

	}
	
	@Test
	public void getCohortLib() {
		try{
			
			preConditionSet("HOST1","PORT1","QA1API","");
			given().
					header("Content-Type","application/json").
			when().
					get("/cohort/cohort_lib").

			then().assertThat().
					statusCode(STATUS_OK).and().contentType(ContentType.JSON).and().
					body(STATUS, equalTo(SUCCESS)).and().
					body(MESSAGE,equalTo(SUCCESS)).log().all();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail("Execution failed due to exception in try block!");
		}
	}
	@Test
	public void getCohortApproveRequest() {
		try{
			
			preConditionSet("HOST1","PORT1","QA1API","");
			given().
					header("Content-Type","application/json").
			when().
					get("/cohort/apprvr_req").

			then().assertThat().
					statusCode(STATUS_OK).and().contentType(ContentType.JSON).and().
					body(STATUS, equalTo(SUCCESS)).log().all();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail("Execution failed due to exception in try block!");
		}		

	}
	@Test
	public void postCohortAPI() {
		
		try{
			
			preConditionSet("HOST1","PORT1","QA1API","./Resources/createCohort.json");
			given().
					header("Content-Type","application/json").
					body(payload).
			when().
					post("/cohort/create_cohort").

			then().assertThat().
					statusCode(STATUS_OK).and().contentType(ContentType.JSON).and().
					body(STATUS, equalTo(SUCCESS)).
					body("title_status", equalTo("SUCCESS")).log().all();
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail("Execution failed due to exception in try block!");
		}	
	}
}
