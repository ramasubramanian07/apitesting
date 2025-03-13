import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserAPITest {
    private String baseURI = "http://localhost:8080";
    private static String userId;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseURI;
    }

    @Test(priority = 1)
    public void createUser() {
        String requestBody = "{ \"username\": \"testUser\", \"email\": \"testuser@example.com\", \"password\": \"password123\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/users");

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("message"), "User created successfully.");
        userId = response.jsonPath().getString("id");
    }

    @Test(priority = 2, dependsOnMethods = "createUser")
    public void getUserDetails() {
        Response response = given()
                .get("/users/" + userId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("username"), "testUser");
        Assert.assertEquals(response.jsonPath().getString("email"), "testuser@example.com");
    }

    @Test(priority = 3, dependsOnMethods = "getUserDetails")
    public void updateUser() {
        String updateBody = "{ \"email\": \"newemail@example.com\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(updateBody)
                .put("/users/" + userId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("message"), "User updated successfully.");
    }

    @Test(priority = 4, dependsOnMethods = "updateUser")
    public void deleteUser() {
        Response deleteResponse = given().delete("/users/" + userId);
        Assert.assertEquals(deleteResponse.getStatusCode(), 200);
        Assert.assertEquals(deleteResponse.jsonPath().getString("message"), "User deleted successfully.");

        Response getResponse = given().get("/users/" + userId);
        Assert.assertEquals(getResponse.getStatusCode(), 404);
    }
}
