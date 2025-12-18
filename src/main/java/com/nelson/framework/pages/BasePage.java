package com.nelson.framework.pages;

import com.nelson.framework.config.ConfigReader;
import com.nelson.framework.utils.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    private final int timeout;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.timeout = ConfigReader.getInt("timeoutSeconds", 10);
    }

    protected WebElement $(By locator) {
        return Waits.wait(driver, timeout)
                .until(d -> d.findElement(locator));
    }

    protected void click(By locator) {
        Waits.wait(driver, timeout)
                .until(d -> d.findElement(locator))
                .click();
    }

    protected void type(By locator, String text) {
        WebElement el = $(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected String text(By locator) {
        return $(locator).getText();
    }

    protected boolean urlContains(String partial) {
        return Waits.wait(driver, timeout)
                .until(d -> d.getCurrentUrl().contains(partial));
    }
}
