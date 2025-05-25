package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageFactoryManager {

    private final WebDriver driver;

    public PageFactoryManager(WebDriver driver) {
        this.driver = driver;
    }

    public <T> T get(Class<T> pageClass) {
        try {
            T pageInstance = pageClass.getDeclaredConstructor().newInstance();
            PageFactory.initElements(driver, pageInstance);
            return pageInstance;
        } catch (Exception e) {
            throw new RuntimeException("Cannot create page: " + pageClass.getSimpleName(), e);
        }
    }
}