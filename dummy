import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserManagementAPITest {
    private static String userId;

    @Test(priority = 1)
    public void testCreateUser() {
        RestAssured.baseURI = "https://your-api-url.com"; // Replace with actual API URL

        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"username\": \"testUser\", \"email\": \"testuser@example.com\", \"password\": \"password123\" }")
                .post("/users");

        userId = response.jsonPath().getString("id");
        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test(priority = 2)
    public void testGetUserDetails() {
        Response response = given().get("/users/" + userId);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void testUpdateUser() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"newemail@example.com\" }")
                .put("/users/" + userId);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4)
    public void testDeleteUser() {
        given().delete("/users/" + userId).then().statusCode(200);
    }
}
