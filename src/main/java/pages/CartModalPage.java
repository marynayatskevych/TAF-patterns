package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartModalPage extends BasePage{
    public CartModalPage(WebDriver driver) {
        super(driver);
    }

    private final By cartModal = By.cssSelector("rz-modal-layout.modal-layout");
    private final By cartItems = By.cssSelector("li.cart-list__item");

    public boolean isModalVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartModal));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean hasItemsInCart() {
        List<WebElement> items = driver.findElements(cartItems);
        return items.size() > 0;
    }
}
