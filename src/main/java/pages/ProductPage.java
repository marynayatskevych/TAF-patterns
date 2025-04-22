package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(@class, 'buy-button')]")
    private WebElement addToCartButton;

    @FindBy(css = "h1.title__font")
    private WebElement productTitle;

    @FindBy(css = "button.header-cart__button")
    private WebElement cartIcon;

    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        Actions actions = new Actions(driver);
        actions.moveToElement(cartIcon).pause(Duration.ofMillis(500)).click().perform();
    }

    public String getProductTitle() {
        waitForVisible(productTitle);
        return productTitle.getText();
    }
}
