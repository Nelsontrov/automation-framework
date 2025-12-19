package com.nelson.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PostPage extends BasePage {

    private final By bodyPre = By.tagName("pre"); // JSONPlaceholder renders JSON in <pre>

    public PostPage(WebDriver driver) {
        super(driver);
    }

    public void open(String baseUrl, String postId) {
        driver.get(baseUrl + "/posts/" + postId);
    }


    public String rawJson() {
        return driver.getPageSource();
    }

}

