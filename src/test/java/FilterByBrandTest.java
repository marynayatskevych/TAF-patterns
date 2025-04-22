import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class FilterByBrandTest extends BaseTest{
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void testFilterByBrandHoco() {
        homePage.searchFor("headphones");
        softAssert.assertTrue(homePage.areCategoryItemsPresent(), "List of goods doesn't appear");
        homePage.selectBrand("Hoco");
        softAssert.assertTrue(homePage.areFilteredResultsCorrect("HOCO"), "Some goods don't match filter 'HOCO'");
        softAssert.assertAll();
    }
}
