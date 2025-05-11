package utils;

import core.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotSaver {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotSaver.class);
    private static final String SCREENSHOT_DIR = "target/screenshots";
    private static final Marker ACTION = MarkerFactory.getMarker("ACTION");
    private static final Marker ERROR = MarkerFactory.getMarker("ERROR");

    public static void saveScreenshotWithHighlight(By locator, String actionName) {
        highlightElement(locator);
        saveScreenshot(actionName);
    }

    public static void saveScreenshot(String actionName) {
        WebDriver driver = DriverFactory.getDriver();
        if (actionName == null || actionName.isBlank()) {
            actionName = "unknown_action";
        }
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = "screenshot_" + actionName + "_" + timestamp + ".png";

            File destDir = new File(SCREENSHOT_DIR);
            if (!destDir.exists()) {
                Files.createDirectories(Paths.get(SCREENSHOT_DIR));
            }

            File destFile = new File(destDir, filename);
            FileUtils.copyFile(srcFile, destFile);
            logger.info(ACTION,"[SCREENSHOT] Saved to {}", destFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error(ERROR, "Failed to save screenshot: ", e);
        }
    }

    public static void highlightElement(By locator) {
        WebDriver driver = DriverFactory.getDriver();
        try {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='3px solid red'", element);
        } catch (NoSuchElementException e) {
            logger.warn(ERROR, "Element not found for highlighting: {}", locator);
        }
    }
}
