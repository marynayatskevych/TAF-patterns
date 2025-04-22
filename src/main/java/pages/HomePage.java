package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

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

    private By getBrandLink(String brandName) {
        return By.xpath("//ul[contains(@class, 'checkbox-filter')]//a[contains(text(), '" + brandName + "')]");
    }

    public void searchFor(String text) {
        wait.until(ExpectedConditions.visibilityOf(searchField));
        searchField.sendKeys(text);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
        wait.until(ExpectedConditions.urlContains("search"));
    }

    public boolean isSearchResultVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(productTitles));
            return productTitles.size() > 0;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean firstProductContains(String expectedText) {
        if (!productTitles.isEmpty()) {
            return productTitles.get(0).getText().toLowerCase().contains(expectedText.toLowerCase());
        }
        return false;
    }

    public void clickFirstProduct() {
        wait.until(ExpectedConditions.visibilityOfAllElements(productLinks));
        if (productLinks.isEmpty()) {
            throw new RuntimeException("List of products is empty");
        }
        productLinks.get(0).click();
    }

    public void clickFirstFilterProduct() {
        wait.until(ExpectedConditions.visibilityOfAllElements(productFilterLinks));
        if (productFilterLinks.isEmpty()) {
            throw new RuntimeException("List of products is empty");
        }
        productFilterLinks.get(0).click();
    }

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

    public void selectBrand(String brandName) {
        wait.until(ExpectedConditions.visibilityOf(brandSection));
        org.openqa.selenium.By brandLink = getBrandLink(brandName);
        scrollTo(brandLink);
        waitForClickable(brandLink);
        click(brandLink);
        wait.until(ExpectedConditions.urlContains("producer=" + brandName.toLowerCase()));
        waitForProductsReload();
    }

    private void waitForProductsReload() {
        wait.until(driver -> {
            int size = filteredTitles.size();
            System.out.println("Items after filter: " + size);
            return size > 0;
        });
    }

    public boolean areFilteredResultsCorrect(String brand) {
        try {
            return wait.until(driver -> {
                if (filteredTitles.isEmpty()) return false;
                for (WebElement title : filteredTitles) {
                    if (!title.getText().toLowerCase().contains(brand.toLowerCase())) {
                        return false;
                    }
                }
                return true;
            });
        } catch (TimeoutException e) {
            return false;
        }
    }
}
