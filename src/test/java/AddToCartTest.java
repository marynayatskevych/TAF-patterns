import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;


public class AddToCartTest extends BaseTest {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void testAddProductTo() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();

        homePage.searchFor("notebook");
        softAssert.assertTrue(homePage.areCategoryItemsPresent(), "List of items are not displayed");
        homePage.clickFirstProduct();
        productPage.addToCart();
        productPage.openCart();
        softAssert.assertTrue(cartModalPage.isModalVisible(), "The cart modal window did not appear");
        softAssert.assertTrue(cartModalPage.hasItemsInCart(), "Cart is empty - no product added");
        softAssert.assertAll();
    }
}