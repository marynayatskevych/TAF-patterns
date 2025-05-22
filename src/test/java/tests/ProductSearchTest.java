package tests;

import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.ProductPage;
import utils.TestListener;
import model.Product;
import static utils.LoggerMarkers.*;

@Test(description = "Verify item appears in search results")
@Listeners(TestListener.class)
public class ProductSearchTest extends BaseTest {
    SoftAssert softAssert = new SoftAssert();
    private static final Logger log = LoggerFactory.getLogger(ProductSearchTest.class);


    @Test
    public void testVerifyThatItemWithCurrentNameIsExistInList() {
        log.info(ACTION, "Starting test: Verify item appears in search results");
        HomePage homePage = new HomePage();
        ProductPage productPage = new ProductPage();

        String searchTerm = "asus";
        log.info(ACTION, "Searching for item: {}", searchTerm);
        homePage.searchFor("asus");

        log.debug(DEBUG,"Checking if search result list is visible...");
        softAssert.assertTrue(homePage.isSearchResultVisible(), "Item list with items doesn't appear");

        log.debug(DEBUG,"Verifying that first item contains search term...");
        softAssert.assertTrue(homePage.firstProductContains("asus"), "First item doesn't contain current word");

        log.info(ACTION,"Clicking first filtered product...");
        homePage.clickFirstFilteredProduct();

        log.info(ACTION,"Getting product title from product page...");
        String actualProductTitle = productPage.getProductTitle();
        Product product = new Product(actualProductTitle);
        log.debug(DEBUG,"Created product from UI: {}", product);

        log.info(TEST, "Asserting all");
        softAssert.assertTrue(
                product.getName().toLowerCase().contains(searchTerm),
                "Product name does not contain search term"
        );
    }
}


