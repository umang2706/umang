package App ;


import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.equalTo;

public class App {
  @Test
  public void Test() {
	  
	 String environment ="http://dummy.restapiexample.com/";
	 RestAssured. given()
      .when().get(environment +"/api/v1/employees")
      .then().assertThat().statusCode(200);
  }
}
