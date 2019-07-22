package AppTest ;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.Matchers.lessThan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.hamcrest.Matcher;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class AppTest {
	
    public static Response response;

  @Test (dataProvider = "API_Data")
  public void API_Status_Code(String URI,String Content__Type,String Value1,String header2, String Value2,String Parameter,String number) throws Exception {
	String environment =URI;
	 System.out.println(URI +Content__Type+Value1+header2+Value2+Parameter);
	 RestAssured. given().header(Content__Type,Value1)
     .header(header2,Value2)
      .when().get(environment+Parameter)
      .then().assertThat().statusCode(200);
  }
  
  @Test (dataProvider = "API_Data")
  public void API_Response_Time(String URI,String Content__Type,String Value1,String header2, String Value2,String Parameter,String number) throws Exception {

	  Long a= new Long(1000);//first way.
	  																														
	  String environment =URI;
      response = RestAssured. given().header(Content__Type,Value1)
	     .header(header2,Value2)
	      .when().get(environment+Parameter)
	      .then().log().all().assertThat().time(lessThan(a), TimeUnit.SECONDS).extract().response();

	 // System.out.println(response.asString());
  }
  
 //@Test (dataProvider = "API_Data")
  public void API_ResponseBody_All_Values(String URI,String Content__Type,String Value1,String header2, String Value2,String Parameter) throws IOException
  {
	  
	  String environment =URI;
    
     
     // String sizeOfList = response.getBody().jsonPath().get("size()");
   //   System.out.println(sizeOfList);
	  FileInputStream fileInput = new FileInputStream("C:\\Users\\Umang Shah\\eclipse-workspace\\1\\src\\test\\java\\AppTest\\Test.properties");
		Properties prop = new Properties();
		prop.load(fileInput);	
      
    
     
      String[] Array1= new String[]{prop.getProperty("a1"),prop.getProperty("a2"), prop.getProperty("a3")};
      String[] Array2= new String[]{prop.getProperty("b1"),prop.getProperty("b2"),prop.getProperty("b3")};
      String[] Array3= new String[]{prop.getProperty("c1"),prop.getProperty("c2"),prop.getProperty("c3")};
      System.out.println(Array1.length);
     for(int i =0;i<Array1.length;i++)
      {
    	 RestAssured. given().header(Content__Type,Value1)
	     .header(header2,Value2)
	      .when().get(environment+Parameter)
 	      .then().assertThat().body(prop.getProperty("key1"),hasItems(Array1[i]))
 	      .and().assertThat().body(prop.getProperty("key2"),hasItems(Array2[i]))
 	      .and().assertThat().body(prop.getProperty("key3"),hasItems(Array3[i]));
    	  
    	  //System.out.println(response.asString());
      }
     
  
  }
  
 
 //@Test (dataProvider = "API_Data")
 public void API_Responsebody_Single_Value(String URI,String Content__Type,String Value1,String header2, String Value2,String Parameter) throws IOException
 {
	 
	 FileInputStream fileInput = new FileInputStream("C:\\Users\\Umang Shah\\eclipse-workspace\\1\\src\\test\\java\\AppTest\\Test.properties");
		Properties prop = new Properties();
		prop.load(fileInput);
	 String environment =URI;
 RestAssured. given().header(Content__Type,Value1)
	     .header(header2,Value2)
	      .when().get(environment+Parameter)
	      .then().assertThat().body("code[0]",equalTo("DF") );
 }
 
 //@Test (dataProvider = "API_Data")
 public void API_Responsebody_Correct_Key_Values(String URI,String Content__Type,String Value1,String header2, String Value2,String Parameter) throws Exception
 {
	 String environment =URI;
     RestAssured. given().header(Content__Type,Value1)
	     .header(header2,Value2)
	      .when().get(environment+Parameter)
	      .then().assertThat().body("any{it.containsKey('code')}", is(true))
	      .and().assertThat().body("any{it.containsKey('name')}", is(true))
	      .and().assertThat().body("any{it.containsKey('id')}", is(true));

 }
 
 @Test (dataProvider = "API_Data")
 public void API_Responsebody_Correct_Content_Header(String URI,String Content__Type,String Value1,String header2, String Value2,String Parameter,String number) throws Exception
 {
	 String environment =URI;
     RestAssured. given().header(Content__Type,Value1)
	     .header(header2,Value2)
	      .when().get(environment+Parameter)
	      .then().assertThat().header("Content-Type","application/json; charset=utf-8").extract().response();
		Headers allHeaders = response.headers();
		System.out.println(allHeaders);
		
 }
 
 //@Test (dataProvider = "API_Data")
 
 public void API_Responsebody_Not_Null_Value(String URI,String Content__Type,String Value1,String header2, String Value2,String Parameter,String number) throws Exception
 {
	 String environment =URI;
     response = RestAssured. given().header(Content__Type,Value1)
	     .header(header2,Value2)
	      .when().get(environment+Parameter)

	
	      .then().assertThat().body("code", is(notNullValue()))
	      .and().assertThat().body("name", is(notNullValue()))
	      .and().assertThat().body("id", is(notNullValue())).extract().response();

	 
	 }
	      
 @Test (dataProvider = "API_Data")
 
 public void API_Responsebody_Array_Length(String URI,String Content__Type,String Value1,String header2, String Value2,String Parameter,String number) throws JSONException
 {
	 String environment =URI;
     response = RestAssured. given().header(Content__Type,Value1)
	     .header(header2,Value2)
	      .when().get(environment+Parameter)
	      .then().assertThat().body("code", is(notNullValue())).extract().response();
	  String a1=response.body().asString();
	  String   output = a1.replace("[", "").replace("]", "");

      JSONObject jResponse = new JSONObject(output);
      JSONArray jsonArray = new JSONArray(a1); 

      System.out.println(jResponse);
      Assert.assertEquals(Integer.toString(jsonArray.length()), number);


	 
	 }
 
 @Test (dataProvider = "API_Data")
 
 public void API_Responsebody_Array_Length1(String URI,String Content__Type,String Value1,String header2, String Value2,String Parameter, String number) throws JSONException
 {
	 String environment =URI;
     RestAssured. given().header(Content__Type,Value1)
	     .header(header2,Value2)
	      .when().get(environment+Parameter)
	     .then().assertThat().body(matchesJsonSchemaInClasspath("/1/src/test/java/Rest_test/Test.json"));	      
 }	 
 
 
 
 																																																																																																																																																										
 @DataProvider(name = "API_Data")
 public String[][] createZipCodeTestData() {
         
	
	return DataProvider1.dataset(); 
 }
 }
 

 

