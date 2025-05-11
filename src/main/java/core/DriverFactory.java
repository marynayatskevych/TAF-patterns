package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import utils.HighlightingListener;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);
    private static final Marker DEBUG = MarkerFactory.getMarker("DEBUG");
    private static final Marker ACTION = MarkerFactory.getMarker("ACTION");

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            WebDriver webDriver;
            switch (System.getProperty("browser", "chrome")) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    webDriver = new FirefoxDriver();
                    log.debug(DEBUG, "Firefox driver initialized");
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    webDriver = new EdgeDriver();
                    log.debug(DEBUG, "Edge driver initialized");
                    break;
                case "safari":
                    WebDriverManager.safaridriver().setup();
                    webDriver = new SafariDriver();
                    log.debug(DEBUG, "Safari driver initialized");
                    break;
                case "ie":
                    WebDriverManager.iedriver().setup();
                    webDriver = new InternetExplorerDriver();
                    log.debug(DEBUG, "IE driver initialized");
                    break;
                default:
                    WebDriverManager.chromedriver().setup();
                    webDriver = new ChromeDriver();
                    log.debug(DEBUG, "Chrome driver initialized");
            }
            webDriver.manage().window().maximize();
            log.info(ACTION, "Browser window maximized");
            HighlightingListener listener = new HighlightingListener();
            WebDriver decoratedDriver = new EventFiringDecorator<>(listener).decorate(webDriver);
            driver.set(decoratedDriver);
        }
        log.debug(DEBUG, "Reusing existing driver instance");
        return driver.get();
    }

    public static void closeDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            log.info(ACTION, "Driver closed");
            driver.remove();
        }
    }
}