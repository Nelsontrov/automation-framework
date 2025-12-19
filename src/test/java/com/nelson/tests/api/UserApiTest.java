package com.nelson.tests.api;

import com.nelson.tests.api.client.ApiClient;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UserApiTest {

    @Test
    public void getPost_shouldReturn200AndHaveFields() {

        String payload = """
            {
              "title": "seed post",
              "body": "seed body",
              "userId": 1
            }
            """;

        // 1. CREATE a post first
        Object idObj = ApiClient.req()
                .body(payload)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // 2. Convert id safely to String
        String id = String.valueOf(idObj).trim();

        // 3. GET the post using that id
        ApiClient.req()
                .when()
                .get("/posts/" + id)
                .then()
                .statusCode(200)
                .body("id", org.hamcrest.Matchers.notNullValue())
                .body("title", org.hamcrest.Matchers.notNullValue())
                .body("body", org.hamcrest.Matchers.notNullValue());
    }



    @Test
    public void createPost_shouldReturn201AndEchoFields() {
        String payload = """
                {
                  "title": "Nelson portfolio API test",
                  "body": "creating a post via Rest-Assured",
                  "userId": 1
                }
                """;

        ApiClient.req()
                .body(payload)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("title", equalTo("Nelson portfolio API test"))
                .body("userId", equalTo(1))
                .body("id", notNullValue());
    }

    @Test
    public void getNotFound_shouldReturn404() {
        ApiClient.req()
                .when()
                .get("/posts/999999999")
                .then()
                .statusCode(404);
    }
}
