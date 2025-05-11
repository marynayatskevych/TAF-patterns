# TAF
test automation framework
## Test Automation Framework (TAF) for UI Testing

This project is a Selenium-based Test Automation Framework implemented in Java, using TestNG and Page Object Model architecture. The framework is designed for testing a web application similar to Rozetka with support for filtering, searching, and working with product data. It provides flexible configuration, logging, reporting, and browser selection features.

---

### Project Requirements & Implementation

1. **WebDriverManager for managing drivers for different browsers**

    * Implemented via `io.github.bonigarcia.WebDriverManager` in `DriverFactory.java`
    * Supports flexible browser selection via `config.properties` or `testng.xml` parameter

2. **PageObject / PageFactory for abstract pages**

    * All pages inherit from `BasePage.java`
    * Uses `PageFactory.initElements` for element initialization

3. **Business model (dedicated entities)**

    * `Product.java` is introduced as a business object to encapsulate product name, price, etc.
    * Used in filter validation and test assertions for more meaningful verification

4. **XML suites for Smoke and Regression tests**

    * `smoke.xml` and `regression.xml` are created in `src/test/resources/`
    * Allow easy control over which tests to run

5. **Screenshot on test failure with log output**

    * Implemented via `ScreenshotSaver.java`
    * Automatically triggered from `TestListener.java`
    * Captures screenshot and logs path when test fails

6. **Flexible parameters (browser, suite, environment)**

    * Browser can be set in `config.properties` or overridden via `testng.xml`
    * Tests are grouped and managed via suite files
    * Configurable base URL and other environment-specific settings

7. **Logging with log4j**

    * Uses `log4j2.xml` configuration
    * Supports multiple levels: `debug`, `info`, `error`, `warn`
    * Logs output to both console and rotating daily log files in `logs/`
    * Markers such as `[TEST]` and `[ACTION]` are used for structured log messages

8. **Allure Reporting Integration**

    * Allure annotations added (e.g. `@Step`) in Page Object methods
    * Tests generate results to `allure-results`
    * Reports viewed via `allure serve` or `allure generate`

9. **Highlighting Elements with WebDriverEventListener**

    * `HighlightingListener.java` tracks last interacted element
    * Red border is applied on interaction and during screenshot capture if test fails

---

###  Commands

Run tests with Maven:

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/smoke.xml
```

Generate Allure report:

```bash
allure serve allure-results
```

---

###  Project Structure

```
TAF/
├── src/
│   ├── main/
│   │   └── java/core/         # BasePage, DriverFactory
│   │   └── java/pages/        # Page Objects (HomePage, ProductPage, CartModelPage)
│   │   └── java/utils/        # HighlightingListener, ScreenshotSaver, TestListener
        └── java/model/        # Product
│   └── test/
│       └── java/tests/        # Test classes
│
├── resources/
│   └── config.properties      # Browser & environment config
│   └── smoke.xml              # Smoke test suite
│   └── regression.xml         # Regression test suite
│   └── log4j2.xml             # Logging configuration
│
├── target/logs/              # Logs output
├── target/screenshots/       # Screenshots on test failure
├── allure-results/           # Allure result files
```
