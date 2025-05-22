package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static utils.LoggerMarkers.*;

public class HighlightingListener implements WebDriverListener {

    private static final Logger log = LoggerFactory.getLogger(HighlightingListener.class);
    private static final ThreadLocal<WebElement> lastInteractedElement = new ThreadLocal<>();

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            highlight(driver, element);
        } catch (Exception e) {
            log.warn(ERROR,"Element not found for highlighting: {}", locator);
        }
    }

    @Override
    public void beforeClick(WebElement element) {
        WebDriver driver = getDriverFromElement(element);
        highlight(driver, element);
        lastInteractedElement.set(element);
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        WebDriver driver = getDriverFromElement(element);
        highlight(driver, element);
        lastInteractedElement.set(element);
    }

    public static WebElement getLastInteractedElement() {
        return lastInteractedElement.get();
    }

    public static void setLastInteractedElement(WebElement element) {
        lastInteractedElement.set(element);
    }

    private void highlight(WebDriver driver, WebElement element) {
        try {
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].style.border='3px solid red'", element);
            }
        } catch (Exception e) {
            log.warn(ERROR,"Failed to highlight element: {}", e.getMessage());
        }
    }

    private WebDriver getDriverFromElement(WebElement element) {
        return core.DriverFactory.getDriver();
    }
}
