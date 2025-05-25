package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static utils.LoggerMarkers.TEST;

public class CartModalPage extends BasePage {
    private static final Logger log = LoggerFactory.getLogger(CartModalPage.class);

    @FindBy(css = "rz-modal-layout.modal-layout")
    private WebElement cartModal;

    @FindBy(css = "li.cart-list__item")
    private List<WebElement> cartItems;

    @Step("Check if cart modal is visible")
    public boolean isModalVisible() {
        try {
            waitForVisibility(cartModal);
            log.info(TEST, "Cart modal is visible");
            return true;
        } catch (TimeoutException e) {
            log.warn(TEST, "Cart modal did not appear");
            return false;
        }
    }

    @Step("Check if cart contains items")
    public boolean hasItemsInCart() {
        try {
            wait.until(driver -> !cartItems.isEmpty());
            log.debug(TEST, "Items in cart: {}", cartItems.size());
            return true;
        } catch (TimeoutException e) {
            log.warn(TEST, "No items found in cart within the timeout");
            return false;
        }
    }

    @Step("Get first cart item title")
    public String getFirstCartItemTitle() {
        if (cartItems.isEmpty()) {
            log.warn(TEST, "No items in cart to get title from");
            return "";
        }
        return cartItems.get(0).getText();
    }
}
