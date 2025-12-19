package com.nelson.tests.integration;

import com.nelson.framework.config.ConfigReader;
import com.nelson.framework.pages.PostPage;
import com.nelson.tests.api.client.ApiClient;
import com.nelson.tests.base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiToUiIntegrationTest extends BaseTest {

    @Test
    public void createPostViaApi_thenValidateInUi() {
        String title = "Nelson E2E Post";
        String body  = "Created via API, validated via UI";

        String payload = String.format("""
        {
          "title": "%s",
          "body": "%s",
          "userId": 1
        }
        """, title, body);


        Response res = ApiClient.req()
                .body(payload)
                .when()
                .post("/posts");

        res.then().statusCode(201);

        Object idObj = res.jsonPath().get("id");
        Assert.assertNotNull(idObj, "API should return an id");

        String postId = String.valueOf(idObj).trim();
        Assert.assertFalse(postId.isEmpty(), "API id should not be empty");


        String uiBase = ConfigReader.get("jsonUiBaseUrl");
        PostPage postPage = new PostPage(driver);
        postPage.open(uiBase, postId);

        String raw = postPage.rawJson();
        Assert.assertTrue(raw.contains(title), "UI JSON should contain title");
        Assert.assertTrue(raw.contains(body), "UI JSON should contain body");
    }
}

