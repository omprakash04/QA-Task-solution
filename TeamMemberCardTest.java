package QA_Task;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TeamMemberCardTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        try {
            // Step 1: Navigate to the website and maximize the window
            driver.get("https://jobins:g4vrh5vo5hqogd0dprfj@recruit.release.jobins.net/");
            driver.manage().window().maximize();

            // Step 2: Scroll to the "Our Team" section
            WebElement ourTeamSection = driver.findElement(By.xpath("/html/body/main/section[2]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", ourTeamSection);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(ourTeamSection));

            // Step 3: Locate all team member cards
            List<WebElement> teamMemberCards = driver.findElements(By.xpath("/html/body/main/section[2]/div/div[2]"));

            // Proceed only if there are cards
            if (!teamMemberCards.isEmpty()) {
                // Step 4: Verify only the first card
                WebElement firstCard = teamMemberCards.get(0);
                System.out.println("Verifying first card...");

                // Verify card structure
                boolean isStructureValid = verifyCardStructure(firstCard);
                if (isStructureValid) {
                    System.out.println("Card structure verified successfully.");
                    
                    // Print the card details
                    printCardDetails(firstCard);
                } else {
                    System.out.println("Card structure verification failed!");
                }

                // Verify hover effect for the first card
                boolean isHoverValid = verifyHoverEffect(driver, firstCard, wait);
                if (isHoverValid) {
                    System.out.println("Hover effect verified successfully.");
                } else {
                    System.out.println("Hover effect verification failed!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close the browser
            driver.quit();
        }
    }

    // Method to verify card structure
    private static boolean verifyCardStructure(WebElement card) {
        try {
            WebElement image = card.findElement(By.cssSelector(".object-contain img"));
            WebElement name = card.findElement(By.cssSelector(".relative.z-10 h3:nth-of-type(1)"));
            WebElement role = card.findElement(By.cssSelector(".relative.z-10 p"));

            return image.isDisplayed() && name.isDisplayed() && role.isDisplayed();
        } catch (Exception e) {
            System.out.println("Error verifying card structure: " + e.getMessage());
            return false;
        }
    }

    // Method to print the card details
    private static void printCardDetails(WebElement card) {
        try {
            WebElement name = card.findElement(By.cssSelector(".relative.z-10 h3:nth-of-type(1)"));
            WebElement role = card.findElement(By.cssSelector(".relative.z-10 p"));
            WebElement image = card.findElement(By.cssSelector(".object-contain img"));
            
            System.out.println("Card Details:");
            System.out.println("Name: " + name.getText());
            System.out.println("Role: " + role.getText());
            System.out.println("Image URL: " + image.getAttribute("src"));
        } catch (Exception e) {
            System.out.println("Error printing card details: " + e.getMessage());
        }
    }

    // Method to verify hover effect
    private static boolean verifyHoverEffect(WebDriver driver, WebElement card, WebDriverWait wait) {
        try {
            // Trigger hover effect on the parent card element
            Actions actions = new Actions(driver);
            actions.moveToElement(card).perform();

            // Use JavaScript to check if the hover element's opacity or visibility changes
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String opacity = (String) js.executeScript(
                    "return window.getComputedStyle(arguments[0]).getPropertyValue('opacity');", card);

            String visibility = (String) js.executeScript(
                    "return window.getComputedStyle(arguments[0]).getPropertyValue('visibility');", card);

            // Verify if opacity changes to 1 and visibility becomes 'visible'
            if (Float.parseFloat(opacity) == 1.0f && visibility.equals("visible")) {
                System.out.println("Hover effect verified successfully: Element is now visible with opacity 1.");
                return true;
            } else {
                System.out.println("Hover effect not verified: Element's opacity is " + opacity + " and visibility is " + visibility);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error verifying hover effect: " + e.getMessage());
            return false;
        }
    }
}
