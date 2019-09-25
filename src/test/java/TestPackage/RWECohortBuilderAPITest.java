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
import io.restassured.http.Cookie;

public class RWECohortBuilderAPITest implements ConstantVaribales {
	Properties prp;
	String payload;
	String cookiesValue;
	Cookie cookie = null;

	@BeforeClass
	public void commonService() throws IOException
	{
		prp = ReusableMethodsClass.getPropertyFile();
		cookiesValue = GenerateCookies.generateCookiesAfterLogin().toString();
   		cookie = new Cookie.Builder(COOKIES_NAME, cookiesValue).setDomain(DOMAIN_NAME).build();
		
	}

	private void preConditionSet(String host,String port, String env, String payLoadPath) throws IOException
	{
		if(payLoadPath!="")
		{
		payload = ReusableMethodsClass.generateStringFromResources(payLoadPath);
		}
		prp = ReusableMethodsClass.getPropertyFile();
		RestAssured.baseURI= prp.getProperty(host)+"/"+prp.getProperty(port)+"/"+prp.getProperty(env);

	}

	@Test
	public void getMyCohorts() {
		try{
		
			preConditionSet("HOST1","PORT1","QA1API","");
			
			given().
					header("Content-Type","application/json").
					cookie(cookie).
			when().
					get("/cohort/my_cohorts").

			then().assertThat().
					statusCode(STATUS_OK).and().contentType(ContentType.JSON).and().
					body(STATUS, equalTo("success")).and().
					body("bodyContent[0].upd_by", equalTo(prp.getProperty("USERNAME"))).and().
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
					cookie(cookie).
			when().
					get("/cohort/cohort_lib").

			then().assertThat().
					statusCode(STATUS_OK).and().contentType(ContentType.JSON).and().
					body(STATUS, equalTo(SUCCESS)).and().
					body("bodyContent[0].shared_typ", equalTo("PUBLISH")).and().
					body(MESSAGE,equalTo(SUCCESS)).log().all();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail("Execution failed due to exception in try block!");
		}
	}
	@Test
	public void postCohortApproveRequest() {
		try{
			
			preConditionSet("HOST1","PORT1","QA1API","./Resources/cohortApproverReq.json");
			given().
					header("Content-Type","application/json").
					cookie(cookie).
			when().
					post("/cohort/apprvr_req").

			then().assertThat().
					statusCode(STATUS_OK).and().contentType(ContentType.JSON).and().
					body("bodyContent[0].submit_by", equalTo(prp.getProperty("USERNAME"))).and().
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
					cookie(cookie).
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
