# WebDriver-TestNG

The test cases are located in the resources folder.

In this project was tested:

Automated 3 user scenarios on the website Rozetka (https://rozetka.com.ua/) using Selenium WebDriver and TestNG:

- Search for a product by keyword and verify that the first result matches the search query.

- Add a product to the cart and check that the modal window appears with the item added.

- Filter products by the brand HOCO and verify that all displayed items belong to that brand.
  
Tools & Technologies Used

-  Selenium WebDriver API – browser navigation, actions, JavaScript execution, Actions, element interaction

-  Locator Strategies – By.name, By.cssSelector, By.xpath, @FindBy, dynamic locators (getBrandLink())

-  Waits – explicit waits via WebDriverWait and ExpectedConditions, custom wait methods, timeout handling

-  Page Object Model + Page Factory – all pages follow POM and utilize @FindBy with PageFactory.initElements


| Acceptance Criteria                                                          | Met |
|------------------------------------------------------------------------------|-----|
 Scenarios are linear (3 total)                                               | ✅   |
| Different locator strategies are used                                        | ✅   |        
| Auto-generated locators are avoided                                          |      ✅  |       
 WebDriver API is widely used                                                 | ✅   |
| Both implicit and explicit waits are used                                    |  ✅   |
 Test scenarios are clear, stable, and well-structured                        |   ✅  |
| Each test method includes assertions                                         |    ✅ |
| Page Objects follow consistent structure and proper decomposition            |    ✅     |
| At least one level of inheritance exists between pages (BasePage abstraction) |     ✅         |



