package pages;

import core.BasePage;
import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import io.qameta.allure.Step;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {
    private static final Logger log = LoggerFactory.getLogger(HomePage.class);
    private static final Marker TEST = MarkerFactory.getMarker("TEST");
    private static final Marker ERROR = MarkerFactory.getMarker("ERROR");
    private static final Marker ACTION = MarkerFactory.getMarker("ACTION");

    @FindBy(name = "search")
    private WebElement searchField;

    @FindBy(css = "button.search-form__submit")
    private WebElement searchButton;

    @FindBy(css = "span.goods-tile__title")
    private List<WebElement> productTitles;

    @FindBy(css = "a.tile-image-host")
    private List<WebElement> productLinks;

    @FindBy(css = "[data-testid='category_goods'] div.item")
    private List<WebElement> categoryItems;

    @FindBy(css = "ul.checkbox-filter")
    private WebElement brandSection;

    @FindBy(css = "a.tile-title")
    private List<WebElement> filteredTitles;

    @FindBy(css = "div.goods-tile")
    private List<WebElement> productFilterLinks;

    @Step("Get locator for brand link: {brandName}")
    private By getBrandLink(String brandName) {
        return By.xpath("//ul[contains(@class, 'checkbox-filter')]//a[contains(text(), '" + brandName + "')]");
    }

    @Step("Searching for product: {text}")
    public void searchFor(String text) {
        log.info(TEST, "Searching for product with text: '{}'", text);
        wait.until(ExpectedConditions.visibilityOf(searchField));
        searchField.sendKeys(text);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
        wait.until(ExpectedConditions.urlContains("search"));
    }

    @Step("Get list of filtered products")
    public List<Product> getFilteredProductList() {
        List<Product> products = new ArrayList<>();
        for (WebElement title : filteredTitles) {
            String name = title.getText();
            products.add(new Product(name));
        }
        return products;
    }

    @Step("Check if search results are visible")
    public boolean isSearchResultVisible() {
        try {
            log.debug(TEST, "Checking visibility of search results");
            wait.until(ExpectedConditions.visibilityOfAllElements(productTitles));
            return productTitles.size() > 0;
        } catch (TimeoutException e) {
            log.warn(ERROR, "Search results not visible within timeout");
            return false;
        }
    }

    @Step("Checking if first product contains: {expected}")
    public boolean firstProductContains(String expectedText) {
        if (!productTitles.isEmpty()) {
            log.error(ERROR, "No products found to click!");
            return productTitles.get(0).getText().toLowerCase().contains(expectedText.toLowerCase());
        }
        log.info(TEST, "Clicking on first product...");
        return false;
    }

    @Step("Click on the first product in the list")
    public void clickFirstProduct() {
        wait.until(ExpectedConditions.visibilityOfAllElements(productLinks));
        if (productLinks.isEmpty()) {
            throw new RuntimeException("List of products is empty");
        }
        productLinks.get(0).click();
    }


    @Step("Clicking first product in the result list")
    public void clickFirstFilteredProduct() {
        wait.until(ExpectedConditions.visibilityOfAllElements(productFilterLinks));
        if (productFilterLinks.isEmpty()) {
            throw new RuntimeException("List of products is empty");
        }
        productFilterLinks.get(0).click();
    }

    @Step("Check if category items are present")
    public boolean areCategoryItemsPresent() {
        try {
            List<WebElement> items = wait.until(driver -> {
                return !categoryItems.isEmpty() ? categoryItems : null;
            });
            return items != null && !items.isEmpty();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Select brand filter: {brand}")
    public void selectBrand(String brandName) {
        log.info(ACTION, "Selecting brand filter: {}", brandName);
        wait.until(ExpectedConditions.visibilityOf(brandSection));
        org.openqa.selenium.By brandLink = getBrandLink(brandName);
        scrollTo(brandLink);
        waitForClickable(brandLink);
        click(brandLink);
        wait.until(ExpectedConditions.urlContains("producer=" + brandName.toLowerCase()));
        waitForProductsReload();
    }

    @Step("Wait for products to reload")
    private void waitForProductsReload() {
        wait.until(driver -> {
            int size = filteredTitles.size();
            return size > 0;
        });
    }

    @Step("Check if filtered results match brand: {expectedBrand}")
    public boolean areFilteredResultsCorrect(String brand) {
        try {
            log.debug(TEST, "Validating that all filtered results contain brand: {}", brand);
            return wait.until(driver -> {
                if (filteredTitles.isEmpty()) return false;
                for (WebElement title : filteredTitles) {
                    if (!title.getText().toLowerCase().contains(brand.toLowerCase())) {
                        log.warn(ERROR, "Found product that does not match brand filter: {}", title.getText());
                        return false;
                    }
                }
                return true;
            });
        } catch (TimeoutException e) {
            log.warn(TEST,"Filtered results did not load correctly");
            return false;
        }
    }
}
