package com.nelson.tests.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.nelson.framework.driver.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestListener implements ITestListener {

    private static final ExtentReports EXTENT = ExtentManager.getExtent();
    private static final ThreadLocal<ExtentTest> TL_TEST = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        TL_TEST.set(EXTENT.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        TL_TEST.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        TL_TEST.get().fail(result.getThrowable());

        try {
            WebDriver driver = DriverFactory.getDriver();
            String screenshot = captureScreenshot(driver, result.getName());
            TL_TEST.get().addScreenCaptureFromPath(screenshot);
        } catch (Exception e) {
            TL_TEST.get().warning("Screenshot failed: " + e.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flush();
    }

    private String captureScreenshot(WebDriver driver, String testName) throws Exception {
        Path dir = Paths.get("test-output", "screenshots");
        Files.createDirectories(dir);

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path dest = dir.resolve(testName + ".png");
        Files.copy(src.toPath(), dest);

        return dest.toString();
    }
}
