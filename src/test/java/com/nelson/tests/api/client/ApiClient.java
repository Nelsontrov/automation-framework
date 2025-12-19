package com.nelson.tests.api.client;

import com.nelson.framework.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public final class ApiClient {

    private ApiClient() {}

    public static RequestSpecification req() {
        RestAssured.baseURI = ConfigReader.get("apiBaseUrl");
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }
}

