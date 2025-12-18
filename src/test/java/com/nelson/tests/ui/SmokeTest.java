package com.nelson.tests.ui;

import com.nelson.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {

    @Test
    public void homePageTitleShouldNotBeEmpty() {
        String title = driver.getTitle();
        Assert.assertNotNull(title);
        Assert.assertFalse(title.trim().isEmpty(), "Title is empty");
    }
}
