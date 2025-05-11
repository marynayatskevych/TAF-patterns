package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;


import java.time.Duration;

public class ProductPage extends BasePage {
    private static final Logger log = LoggerFactory.getLogger(ProductPage.class);
    private static final Marker ACTION = MarkerFactory.getMarker("ACTION");

    @FindBy(xpath = "//button[contains(@class, 'buy-button')]")
    private WebElement addToCartButton;

    @FindBy(css = "h1.title__font")
    private WebElement productTitle;

    @FindBy(css = "button.header-cart__button")
    private WebElement cartIcon;

    @Step("Add product to cart")
    public void addToCart() {
        log.info(ACTION,"Adding product to cart...");
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
    }

    @Step("Open cart")
    public void openCart() {
        log.info(ACTION,"Opening cart...");
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        Actions actions = new Actions(driver);
        actions.moveToElement(cartIcon).pause(Duration.ofMillis(500)).click().perform();
    }

    @Step("Get product title")
    public String getProductTitle() {
        waitForVisibility(productTitle);
        String title = productTitle.getText();
        log.debug(ACTION,"Product title: {}", title);
        return title;
    }
}
