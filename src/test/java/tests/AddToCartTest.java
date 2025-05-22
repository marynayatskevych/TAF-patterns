package tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CartModalPage;
import pages.HomePage;
import pages.ProductPage;
import static utils.LoggerMarkers.*;


public class AddToCartTest extends BaseTest {
    SoftAssert softAssert = new SoftAssert();
    private static final Logger log = LoggerFactory.getLogger(AddToCartTest.class);

    @Test
    public void testAddProductTo() throws InterruptedException {
        log.info(TEST, "Test: Add item to cart started");
        HomePage homePage = new HomePage();
        ProductPage productPage = new ProductPage();
        CartModalPage cartModalPage = new CartModalPage();

        log.info(ACTION, "Searching for 'notebook'");
        homePage.searchFor("notebook");

        log.info(ACTION, "Checking that search results are visible");
        softAssert.assertTrue(homePage.areCategoryItemsPresent(), "List of items are not displayed");

        log.info(ACTION, "Clicking the first product from filtered list");
        homePage.clickFirstProduct();

        log.info(ACTION, "Adding product to cart");
        productPage.addToCart();

        log.info(ACTION, "Opening cart");
        productPage.openCart();

        log.info(ACTION, "Checking if modal with cart is visible");
        softAssert.assertTrue(cartModalPage.isModalVisible(), "The cart modal window did not appear");

        log.info(ACTION, "Checking if cart has at least one item");
        softAssert.assertTrue(cartModalPage.hasItemsInCart(), "Cart is empty - no product added");

        log.info(TEST, "Asserting all verifications");
        softAssert.assertAll();
    }
}