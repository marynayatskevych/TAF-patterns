### Design Patterns and S.O.L.I.D. principles

This project demonstrates the practical implementation of design patterns and code improvements based on SOLID principles

---

####  Implemented Design Patterns

1. **Singleton**

**Used in:** `DriverFactory` class  
**Purpose:** Implements Singleton pattern using ThreadLocal<WebDriver>

2. **Factory Method**

**Used in:** `PageFactoryManager` class  
**Purpose:** Creates Page Objects dynamically. Eliminates the need to instantiate pages manually in each test, improves flexibility and reusability.

3. **Decorator**

**Used in:** `HighlightingListener` + `EventFiringDecorator`  
**Purpose:** Enhances standard WebDriver behavior by visually highlighting interacted elements and logging actions. Integrated directly in the driver setup.

All of these classes are actively used and invoked during test execution.


####  Applied SOLID Principles

The framework was reviewed and refactored according to key SOLID design principles:

| Class           | Problem                                                                 | Solution                                                                 |
|-----------------|-------------------------------------------------------------------------|--------------------------------------------------------------------------|
| `DriverFactory` | The class has more than one responsibility: driver setup and unrelated logic. | Extracted utility logic into other classes. Only WebDriver-related methods remain. |
| `HomePage`      | Class is open for modification (not extension), fields were public, some methods redundant. | Fields were set to private, unused logic removed, shared behavior moved to `BasePage`. |
| `Product`       | Direct constructor requires multiple parameters; not extendable.         | Introduced `ProductBuilder` to construct Product instances with optional fields. |
| `AddToCartTest` | Direct instantiation of Page Objects repeated in every test.             | Page creation centralized via `PageFactoryManager`, reducing duplication and increasing modularity. |


####  Summary: SOLID Principles Implemented in the Framework

| Principle                        | Meaning                                                                                      | Implementation Example                                                                                   |
|----------------------------------|----------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|
| **Single Responsibility Principle** | Each class should have only one responsibility.                                              | `DriverFactory` is responsible only for driver creation; logging logic is in separate listener classes.   |
| **Open/Closed Principle**          | Classes should be open for extension but closed for modification.                            | `ProductBuilder` allows creating different product configurations without changing the `Product` class.  |
| **Liskov Substitution Principle**  | Subtypes should be substitutable for their base types.                                       | All Page Object classes extend from `BasePage`, allowing interchangeable use in tests.                    |
| **Interface Segregation Principle**| Clients should not depend on interfaces they do not use.                                     | Utility logic (e.g., logging, screenshots) is extracted into single-responsibility classes.              |
| **Dependency Inversion Principle** | High-level modules should not depend on low-level modules, but both depend on abstractions. | Tests rely on `PageFactoryManager` and `DriverFactory` rather than creating objects manually.           |

####  Commands

Run tests with Maven:

```bash
# Run tests on Windows
mvn clean test -DsuiteXmlFile="src\\test\\resources\\smoke.xml"

# Run tests on Unix/Mac
mvn clean test -DsuiteXmlFile=src/test/resources/smoke.xml
```

Generate Allure report:

```bash
allure serve allure-results
```

---

###  Project Structure

```
TAF-patterns/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── core/                                
│   │       │   ├── BasePage.java              # Common reusable methods for all Page Objects
│   │       │   ├── DriverFactory.java         # Singleton: creates and provides access to WebDriver
│   │       │   └── PageFactoryManager.java    # Factory Method: centralizes Page Object creation
│   │       ├── model/
│   │       │   ├── Product.java               # Data model representing a product
│   │       │   └── ProductBuilder.java        # Builder: for flexible creation of Product instances
│   │       ├── pages/
│   │       │   ├── CartModalPage.java         # Page Object: modal cart interactions
│   │       │   ├── HomePage.java              # Page Object: handles search and filtering actions
│   │       │   └── ProductPage.java           # Page Object: handles product details and actions
│   │       └── utils/
│   │           ├── HighlightingListener.java  # Decorator: highlights elements during interaction
│   │           ├── LoggerMarkers.java         # Defines logging categories (e.g. TEST, DEBUG)
│   │           ├── ScreenshotSaver.java       # Takes screenshot on test failure
│   │           └── TestListener.java          # Allure TestNG integration and failure handling
│
├── src/
│   └── test/
│       ├── java/
│       │   └── tests/
│       │       ├── BaseTest.java              # Test base: driver setup and teardown
│       │       ├── AddToCartTest.java         # UI test: search and add product to cart
│       │       ├── FilterByBrandTest.java     # UI test: filtering products by brand
│       │       └── ProductSearchTest.java     # UI test: search and open product detail page
│       └── resources/
│           ├── config.properties              # Configuration for the test environment
│           ├── log4j2.xml                     # Logging configuration for Log4j2
│           ├── regression.xml                 # TestNG suite for regression test group
│           └── smoke.xml                      # TestNG suite for smoke test group
│
├── pom.xml                                     # Maven configuration file with dependencies
├── README.md                                   # Project documentation
└── logs/                                       # Folder containing test execution logs
```