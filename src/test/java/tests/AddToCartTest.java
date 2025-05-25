package tests;

import core.PageFactoryManager;
import model.Product;
import model.ProductBuilder;
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
        HomePage homePage = factory.get(HomePage.class);
        ProductPage productPage = factory.get(ProductPage.class);
        CartModalPage cartModalPage = factory.get(CartModalPage.class);

        log.info(ACTION, "Searching for 'notebook'");
        homePage.searchFor("ноутбук");

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

        String actualProductTitle = cartModalPage.getFirstCartItemTitle();

        Product expected = ProductBuilder.builder()
                .withName("ноутбук")
                .build();

        Product actual = ProductBuilder.builder()
                .withName(actualProductTitle)
                .build();

        log.debug(DEBUG, "Expected product: {}", expected);
        log.debug(DEBUG, "Actual product in cart: {}", actual);

        log.info(TEST, "Actual product title in cart: '{}'", actualProductTitle);
        log.info(TEST, "Expected to contain: '{}'", expected.getName());


        softAssert.assertTrue(
                actual.getName().toLowerCase().contains(expected.getName().toLowerCase()),
                "Product in cart does not match expected search term"
        );

        log.info(TEST, "Asserting all verifications");
        softAssert.assertAll();
    }
}