package core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static utils.LoggerMarkers.*;
import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final Logger log = LoggerFactory.getLogger(BasePage.class);

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        log.debug(ACTION, "Page initialized: {}", this.getClass().getSimpleName());
    }

    protected void click(By locator) {
        WebElement element = driver.findElement(locator);
        highlightElement(element);
        log.info(ACTION, "Clicking on element by locator: {}", locator);
        element.click();
    }

    private void highlightElement(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
            utils.HighlightingListener.setLastInteractedElement(element);
            log.debug(ACTION, "Highlighted element: {}", element);
        } catch (Exception e) {
            log.warn(ACTION, "Failed to highlight element: {}", e.getMessage());
        }
    }

    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).clear();
        log.info(ACTION, "Typing text '{}' into element: {}", text, element);
        element.sendKeys(text);
    }

    protected void waitForVisibility(WebElement element) {
        log.debug(ACTION, "Waiting for visibility of element: {}", element);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForClickability(WebElement element) {
        log.debug(ACTION, "Waiting for clickability of element: {}", element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            log.debug(ACTION, "Element is displayed: {}", element);
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            log.warn(ACTION, "Element is not displayed: {}", element);
            return false;
        }
    }
    protected void scrollTo(By locator) {
        WebElement element = driver.findElement(locator);
        log.info(ACTION, "Scrolling to element by locator: {}", locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollTo(WebElement element) {
        log.info(ACTION, "Scrolling to element: {}", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected WebElement waitForClickable(WebElement element) {
        log.debug(ACTION, "Waiting for element to be clickable: {}", element);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitForClickable(By locator) {
        log.debug(ACTION, "Waiting for element to be clickable by locator: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
