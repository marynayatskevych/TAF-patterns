package tests;

import model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import utils.TestListener;
import java.util.List;
import static utils.LoggerMarkers.*;

@Listeners(TestListener.class)

public class FilterByBrandTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(FilterByBrandTest.class);
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void testFilterByBrandSamsung() {
        log.info("Test: Filter by brand 'Samsung' started");
        HomePage homePage = new HomePage();

        log.info(ACTION,"Searching for 'headphones'");
        homePage.searchFor("headphones");

        log.info(DEBUG,"Checking if category items are present");
        softAssert.assertTrue(homePage.areCategoryItemsPresent(), "List of goods doesn't appear");

        log.info(ACTION,"Selecting brand 'Samsung'");
        homePage.selectBrand("Samsung");

        log.info(DEBUG,"Verifying filtered results match brand 'Samsung'");
        List<Product> products = homePage.getFilteredProductList();
        for (Product product : products) {
            log.debug("Checking product: {}", product);
            softAssert.assertTrue(
                    product.getName().toLowerCase().contains("samsung"),
                    "Filtered product does not match brand: " + product
            );
        }
        log.info(TEST, "Asserting all");
        softAssert.assertAll();
    }
}
