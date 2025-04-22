package pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartModalPage extends BasePage {

    public CartModalPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "rz-modal-layout.modal-layout")
    private WebElement cartModal;

    @FindBy(css = "li.cart-list__item")
    private List<WebElement> cartItems;

    public boolean isModalVisible() {
        try {
            waitForVisible(cartModal);
            return true;
        } catch (TimeoutException e) {
            System.out.println("Cart modal did not appear");
            return false;
        }
    }

    public boolean hasItemsInCart() {
        int size = cartItems.size();
        System.out.println("Items in cart: " + size);
        return size > 0;
    }
}
