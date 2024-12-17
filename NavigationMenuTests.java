package QA_Task;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavigationMenuTests {

    WebDriver driver;

    // Set up the WebDriver and ChromeOptions
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
    }

    // Test: Navigation Menu Link Validation
    public void testNavigationLinks() {
        driver.get("https://jobins:g4vrh5vo5hqogd0dprfj@recruit.release.jobins.net/");

        // List of navigation menu links to test
        String[] menuLinks = {
            "ホーム", "JoBinsについて", "メンバー紹介", "働き方"
        };

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Wait up to 10 seconds for elements

        for (String linkText : menuLinks) {
            try {
                // Find the navigation link and click it
                WebElement menuItem = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(linkText)));
                String linkHref = menuItem.getAttribute("href");  // Get the href attribute of the link
                System.out.println("Link for '" + linkText + "' has href: " + linkHref);
                
                // Click the link
                menuItem.click();
                
                // Wait for the page to load and validate the URL after clicking the link
                wait.until(ExpectedConditions.urlToBe(linkHref)); // Wait until the URL matches the href attribute
                String currentUrl = driver.getCurrentUrl();
                System.out.println("Successfully navigated to: " + currentUrl);

              

                // Wait for the home page to load after navigating back
                wait.until(ExpectedConditions.urlContains("recruit.release.jobins.net"));
                
            } catch (NoSuchElementException e) {
                System.out.println("Navigation link '" + linkText + "' not found.");
            }
        }
    }

    // Test: Button Presence and Click Action Testing
    public void testEntryButton() {
        driver.get("https://jobins:g4vrh5vo5hqogd0dprfj@recruit.release.jobins.net/");

        try {
            // Find the "Entry" button by its XPath 
            WebElement entryButton = driver.findElement(By.xpath("/html/body/header/div/div/div/a/span[2]"));

            // Check if the button is visible
            if (entryButton.isDisplayed()) {
                System.out.println("Entry button is visible.");
                
                // Click the "Entry" button
                entryButton.click();
                
                // Wait for the page to load after clicking the button
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.urlContains("entry"));  // Adjust according to the page you expect after clicking
                
                // Validate the action after clicking the button (e.g., check if the page URL changed or check for a specific element)
                String currentUrl = driver.getCurrentUrl();
                System.out.println("Navigated to: " + currentUrl);
            } else {
                System.out.println("Entry button is not visible.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Entry button not found.");
        }
    }

    // Clean up and close the browser
    public void tearDown() {
        driver.quit();
    }

    // Main method to run the tests
    public static void main(String[] args) {
        NavigationMenuTests test = new NavigationMenuTests();
        
        test.setUp();
        
        // Test Navigation Links
        test.testNavigationLinks();
        
       
        
        // Clean up after the tests
        test.tearDown();
    }
}
