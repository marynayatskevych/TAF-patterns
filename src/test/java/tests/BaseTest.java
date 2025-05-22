package tests;

import core.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import static utils.LoggerMarkers.*;

public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        System.setProperty("browser", browser);
        log.debug(ACTION, "Setting system property for browser: {}", browser);
        log.info(ACTION, "Initializing WebDriver for browser: {}", browser);
        driver = DriverFactory.getDriver();
        driver.get("https://rozetka.com.ua/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info(ACTION, "Closing WebDriver");
        DriverFactory.closeDriver();
    }
}
