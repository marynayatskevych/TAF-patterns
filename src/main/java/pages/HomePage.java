package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;


public class HomePage extends BasePage{

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private final By searchField = By.name("search");
    private final By searchButton = By.cssSelector("button.search-form__submit");
    private final By productTitles = By.cssSelector("span.goods-tile__title");
    private final By productLinks = By.cssSelector("a.tile-image-host");
    private final By categoryItems = By.cssSelector("[data-testid='category_goods'] div.item");
    private final By brandSection = By.cssSelector("ul.checkbox-filter");
    private final By filteredTitles = By.cssSelector("a.tile-title");
    private final By productFilterLink = By.cssSelector("div.goods-tile");

    private By getBrandLink(String brandName) {
        return By.xpath("//ul[contains(@class, 'checkbox-filter')]//a[contains(text(), '" + brandName + "')]");
    }

    public void searchFor(String text) {
        waitForVisible(searchField);
        type(searchField, text);
        waitForClickable(searchButton);
        click(searchButton);
        wait.until(ExpectedConditions.urlContains("search"));
    }

    public boolean isSearchResultVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productTitles));
            return driver.findElements(productTitles).size() > 0;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean firstProductContains(String expectedText) {
        List<WebElement> titles = driver.findElements(productTitles);
        if (!titles.isEmpty()) {
            return titles.get(0).getText().toLowerCase().contains(expectedText.toLowerCase());
        }
        return false;
    }

    public void clickFirstProduct() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productLinks));
        List<WebElement> products = driver.findElements(productLinks);
        if (products.isEmpty()) {
            throw new RuntimeException("List of products is empty");
        }
        products.get(0).click();
    }

    public void clickFirstFilterProduct() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productFilterLink));
        List<WebElement> products = driver.findElements(productFilterLink);
        if (products.isEmpty()) {
            throw new RuntimeException("List of products is empty");
        }
        products.get(0).click();
    }

    public boolean areCategoryItemsPresent() {
        try {
            List<WebElement> items = wait.until(driver -> {
                List<WebElement> found = driver.findElements(categoryItems);
                return found.size() > 0 ? found : null;
            });
            return items != null && !items.isEmpty();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void selectBrand(String brandName) {
        wait.until(ExpectedConditions.presenceOfElementLocated(brandSection));
        By brandLink = getBrandLink(brandName);
        scrollTo(brandLink);
        waitForClickable(brandLink);
        click(brandLink);
        wait.until(ExpectedConditions.urlContains("producer=" + brandName.toLowerCase()));
        waitForProductsReload();
    }

    private void waitForProductsReload() {
        wait.until(driver -> {
            List<WebElement> items = driver.findElements(filteredTitles);
            System.out.println("Items after filter: " + items.size());
            return items.size() > 0;
        });
    }

    public boolean areFilteredResultsCorrect(String brand) {
        try {
            wait.until(driver -> {
                List<WebElement> titles = driver.findElements(filteredTitles);
                if (titles.isEmpty()) return false;
                for (WebElement title : titles) {
                    if (!title.getText().toLowerCase().contains(brand.toLowerCase())) {
                        return false;
                    }
                }
                return true;
            });
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

}
