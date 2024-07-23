package assessment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class AssessmentTest {

    static {
        RestAssured.baseURI = "http://localhost:80";
    }

    @Test(description = "Send /get request and validate response code")
    void getRequestTest(){
        RestAssured.given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/get")
                .then()
                .assertThat().statusCode(Matchers.equalTo(200))
                .assertThat().time(Matchers.lessThan(400L), TimeUnit.MILLISECONDS)
                .assertThat().contentType(ContentType.JSON)
                .log().all();
    }

    @Test(description = "Simulate Unauthorized Access")
    void unAuthorizedUserTest(){
        RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .basic("user", "xyz")
                .pathParam("statusCode", 401)
                .when()
                .post("/status/{statusCode}")
                .then()
                .assertThat().statusCode(401)
                .assertThat().time(Matchers.lessThan(400L), TimeUnit.MILLISECONDS)
                .log().all();
    }

    @Test(description = "Send /post request with Json body and validate response contains relevant data")
    void postRequestTest(){
        String jsonPayload = "{\n" +
                "  \"name\": \"Nithesh\",\n" +
                "  \"email\": \"nithesh@example.com\",\n" +
                "  \"age\": 30,\n" +
                "  \"city\": \"Bangalore\"\n" +
                "}";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .when()
                .post("/post")
                .then()
                .assertThat().time(Matchers.lessThan(400L), TimeUnit.MILLISECONDS)
                .assertThat().body("json.name", Matchers.equalTo("Nithesh"))
                .assertThat().body("json.email", Matchers.equalTo("nithesh@example.com"))
                .assertThat().body("json.age", Matchers.equalTo(30))
                .assertThat().body("json.city", Matchers.equalTo("Bangalore"))
                .log().all();

    }

}
