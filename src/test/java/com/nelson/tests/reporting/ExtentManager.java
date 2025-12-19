package com.nelson.tests.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ExtentManager {

    private static ExtentReports extent;

    private ExtentManager() {}

    public static ExtentReports getExtent() {
        if (extent == null) {
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            String reportPath = Paths.get(
                    "test-output",
                    "ExtentReport_" + timestamp + ".html"
            ).toString();

            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setReportName("Automation Test Results");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Framework", "Java Selenium TestNG");
        }
        return extent;
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
