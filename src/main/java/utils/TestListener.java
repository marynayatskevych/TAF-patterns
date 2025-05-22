package utils;

import core.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import static utils.LoggerMarkers.*;

public class TestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        logger.debug(TEST, "[DEBUG] Preparing to start test: {}", testName);
        logger.info(TEST, "[ACTION] Starting test execution: {}", testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info(TEST, "[ACTION] Test passed: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();

        logger.error(TEST, "[TEST] Test failed: {}", testName);

        WebDriver driver = DriverFactory.getDriver();
        WebElement last = HighlightingListener.getLastInteractedElement();
        if (last != null) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", last);
            } catch (Exception e) {
                logger.warn(TEST, "[TEST] Failed to apply red border to element: {}", e.getMessage());
            }
        }

        ScreenshotSaver.saveScreenshot(testName);

        if (throwable != null) {
            logger.error(TEST, "[EXCEPTION]", throwable);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn(TEST, "[TEST] Test skipped: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info(TEST, "[ACTION] Test suite started: {}", context.getName());
        logger.debug(TEST, "Suite contains {} methods", context.getAllTestMethods().length);
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info(TEST, "[ACTION] Test suite finished: {}", context.getName());
        logger.debug(TEST, "All tests in suite '{}' completed.", context.getName());
    }
}