package QA_Task;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FAQSectionTest {

    private WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        FAQSectionTest test = new FAQSectionTest();
        test.setUp();
        test.testFAQExpandCollapse();
        test.testFAQContentStructure();
        test.tearDown();
    }

    public void setUp() {
        // Set up the WebDriver (using ChromeDriver for this example)
         driver = new ChromeDriver();
    }

    public void testFAQExpandCollapse() throws InterruptedException {
        // Navigate to the FAQ page
        driver.get("https://jobins:g4vrh5vo5hqogd0dprfj@recruit.release.jobins.net/");
        
        // Step 2: Scroll to the "Our Team" section
        WebElement FAQSection = driver.findElement(By.xpath("/html/body/main/section[3]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", FAQSection);

        
        
        Thread.sleep(3000);

        // Locate the FAQ question (example CSS selector, adjust as needed)
        WebElement faqQuestion = driver.findElement(By.xpath("//*[@id=\"radix-:R397qja:\"]/span"));
        System.out.println("FAQ question is present.");
        System.out.println("FAQ Question:" + faqQuestion.getText());    


        // Programmatically trigger the expand action (click to expand the answer)
        faqQuestion.click();
        
        Thread.sleep(3000);

        // Wait for the answer to become visible
        WebElement faqAnswer = driver.findElement(By.id("radix-:Rj97qja:"));
        if (faqAnswer.isDisplayed()) {
            System.out.println("FAQ answer is visible after expanding.");
            System.out.println("FAQ Answer: " + faqAnswer.getText()); 

        } else {
            System.out.println("FAIL: FAQ answer is NOT visible after expanding.");
        }

        // Now collapse the answer programmatically by clicking the question again
        faqQuestion.click();

        Thread.sleep(3000);
        
        // Ensure that the answer is no longer visible (collapsed)
        if (!faqAnswer.isDisplayed()) {
            System.out.println("FAQ answer is successfully collapsed and hide the answer");
        } else {
            System.out.println("FAIL: FAQ answer is STILL visible after collapsing.");
        }
    }

    public void testFAQContentStructure() throws InterruptedException {
        // Navigate to the FAQ page
    	 driver.get("https://jobins:g4vrh5vo5hqogd0dprfj@recruit.release.jobins.net/");
         
         // Step 2: Scroll to the "FAQ" section
         WebElement FAQSection = driver.findElement(By.xpath("/html/body/main/section[3]"));
         JavascriptExecutor js = (JavascriptExecutor) driver;
         js.executeScript("arguments[0].scrollIntoView(true);", FAQSection);

        // Verify that there is at least one FAQ question
         WebElement faqQuestion = driver.findElement(By.xpath("//*[@id=\"radix-:R397qja:\"]/span"));
        if (faqQuestion != null && !faqQuestion.getText().isEmpty()) {
           // System.out.println("FAQ question is present.");
            //System.out.println("FAQ Question:" + faqQuestion.getText());    

        } else {
            System.out.println("FAIL: FAQ question is missing or empty.");
        }
        
        faqQuestion.click();
        
        Thread.sleep(3000);
        WebElement faqAnswer = driver.findElement(By.id("radix-:Rj97qja:"));
        if (faqAnswer.isDisplayed()) {
           // System.out.println("FAQ answer is visible after expanding.");
           // System.out.println("FAQ Answer: " + faqAnswer.getText()); 
        } else {
            System.out.println("FAIL: FAQ answer is NOT visible after expanding.");
        }
    }

      

    public void tearDown() {
        // Close the browser after the test
        if (driver != null) {
            driver.quit();
        }
    }
}
