package QA_Task;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class JobFilterAndLinkValidation {

    public static void main(String[] args) throws InterruptedException {
        // Set up WebDriver (Assuming ChromeDriver is set in system path)
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to the job listing page
            driver.get("https://jobins:g4vrh5vo5hqogd0dprfj@recruit.release.jobins.net/");

            // Maximize window
            driver.manage().window().maximize();

            // Wait until the page is loaded using WebDriverWait with Duration
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Use Duration instead of int

            Thread.sleep(5000);

            // Test job filtering functionality - 3 filter buttons
            WebElement careerSection = driver.findElement(By.xpath("/html/body/main/section[1]"));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", careerSection);

            Thread.sleep(3000);  // 1. Filter by Career Recruitment
            // driver.findElement(By.id("radix-:r8:-trigger-キャリア採用")).click();
            // wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"radix-:r8:-content-キャリア採用\"]/div/a[1]")));

            // 2. Filter by New Graduate Recruitment
            driver.findElement(By.id("radix-:R157qja:-trigger-新卒採用")).click();
            /*id="radix-:R157qja:-trigger-新卒採用"
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"radix-:r8:-content-新卒採用\"]/div/a")));*/

            // 3. Filter by Long-term Intern Recruitment
            // WebElement internRecruitmentButton = driver.findElement(By.xpath("//*[@id=\"radix-:r8:-trigger-長期インターン採用\"]"));
            // internRecruitmentButton.click();  // Click the Long-term Intern Recruitment filter button
            // WebElement updateButtonIntern = driver.findElement(By.id("filter-update-long-term-intern"));
            // updateButtonIntern.click(); // Click the update button for intern recruitment filter

            // Wait for results to load after applying the filter
            // wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"radix-:r8:-content-長期インターン採用\"]/div/a")));

            List<WebElement> jobListings = driver.findElements(By.id("radix-:R157qja:-content-新卒採用"));
            for (WebElement listing : jobListings) {
                String jobTitle = listing.findElement(By.xpath("//*[@id=\"radix-:R157qja:-content-新卒採用\"]/div/a/div/div[2]/div/div/h4")).getText();
                String jobType = listing.findElement(By.xpath("//*[@id=\"radix-:R157qja:-content-新卒採用\"]/div/a/div/div[2]/div/p")).getText();

                // Print job type to debug if needed
                System.out.println("Job Type: " + jobType);

                if (jobType.contains("New Graduate Recruitment")) {
                    System.out.println("Test failed: Found a job not matching the filter.");
                    break;
                }
            }
            System.out.println("Job filtering test passed.");

            // Validate job listing links
            System.out.println("Validating job listing links...");
            for (WebElement listing : jobListings) {
                WebElement jobLink = listing.findElement(By.tagName("a"));
                String jobLinkHref = jobLink.getAttribute("href");

                if (jobLinkHref == null || jobLinkHref.isEmpty()) {
                    System.out.println("Invalid or empty link: " + jobLinkHref);
                    continue;
                }

                try {
                    // Open job link in a new tab (this is just an example; actual test may need better handling)
                    driver.get(jobLinkHref);

                    // Validate the job page (e.g., ensure the title or a unique element is present)
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/main/div/div[3]/div[1]/div/div/div/div[1]")));  // Replace with correct element for job page title
                    System.out.println("Job listing link is valid: " + jobLinkHref);

                    // Navigate back to the main job listing page
                    driver.get("https://jobins:g4vrh5vo5hqogd0dprfj@recruit.release.jobins.net/");
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("radix-:R157qja:-content-新卒採用")));  // Ensure the listings are visible again
                } catch (Exception e) {
                    System.out.println("Error opening job listing link: " + jobLinkHref);
                    e.printStackTrace();
                }
            }

            System.out.println("Job listing link validation completed.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            // driver.quit();
        }
    }
}
