import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ProductPage;


public class ProductSearchTest extends BaseTest {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void testVerifyThatItemWithCurrentNameIsExistInList() {
        homePage.searchFor("asus");
        softAssert.assertTrue(homePage.isSearchResultVisible(), "Item list with items doesn't appear");
        softAssert.assertTrue(homePage.firstProductContains("asus"), "First item doesn't contain current word");
        homePage.clickFirstFilterProduct();
        String actualProductTitle = productPage.getProductTitle().toLowerCase();
        softAssert.assertTrue(actualProductTitle.contains("asus"), "Title of the card does not consist the expected name");
        softAssert.assertAll();
    }
}

