import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.CartModalPage;
import pages.HomePage;
import pages.ProductPage;

public class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;
    protected ProductPage productPage;
    CartModalPage cartModalPage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://rozetka.com.ua/");
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        cartModalPage = new CartModalPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

