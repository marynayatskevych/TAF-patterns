package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class ProductPage extends BasePage {
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    private final By addToCartButton = By.xpath("//button[contains(@class, 'buy-button')]");
    private final By productTitle = By.cssSelector("h1.title__font");
    private final By cartIcon = By.cssSelector("button.header-cart__button");

    public void addToCart() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }

    public void openCart() {
        WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        Actions actions = new Actions(driver);
        actions.moveToElement(cart).pause(Duration.ofMillis(500)).click().perform();
    }

    public String getProductTitle() {
        waitForVisible(productTitle);
        return getText(productTitle);
    }

}
