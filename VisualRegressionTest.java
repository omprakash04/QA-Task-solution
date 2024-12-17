package QA_Task;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class VisualRegressionTest {

    public static void main(String[] args) {
        // Define file paths
        String baselineImage = "baseline_hero_section.png";
        String currentImage = "current_hero_section.png";
        String diffImage = "diff_hero_section.png";

        // Set up WebDriver
        ChromeOptions options = new ChromeOptions();
       // options.addArguments("--headless");
       // options.addArguments("--window-size=1920,1080");
        WebDriver driver = new ChromeDriver(options);

        try {
            // Navigate to the page
            driver.get("https://jobins:g4vrh5vo5hqogd0dprfj@recruit.release.jobins.net/");
            Thread.sleep(5000); // Wait for the page to load fully

            // Locate the hero section
            WebElement heroSection = driver.findElement(By.xpath("/html/body/main/div/section/div[2]")); // Replace with the correct selector

            // Capture current screenshot
            captureScreenshot(driver, heroSection, currentImage);

            // Compare with the baseline
            File baselineFile = new File(baselineImage);
            if (!baselineFile.exists()) {
                System.out.println("Baseline image not found. Saving current image as baseline.");
                new File(currentImage).renameTo(baselineFile);
            } else {
                boolean result = compareImages(baselineImage, currentImage, diffImage);
                if (result) {
                    System.out.println("No visual changes detected in the hero section.");
                } else {
                    System.out.println("Visual changes detected! Differences saved to " + diffImage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static void captureScreenshot(WebDriver driver, WebElement element, String filePath) throws IOException {
        // Take full-page screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);

        // Get element dimensions
        Point point = element.getLocation();
        int eleWidth = element.getSize().getWidth();
        int eleHeight = element.getSize().getHeight();

        // Ensure the cropping bounds are within the image dimensions
        int imgWidth = fullImg.getWidth();
        int imgHeight = fullImg.getHeight();

        int x = Math.max(0, point.getX());
        int y = Math.max(0, point.getY());
        int width = Math.min(eleWidth, imgWidth - x);
        int height = Math.min(eleHeight, imgHeight - y);

        // Crop to the element bounds
        BufferedImage eleScreenshot = fullImg.getSubimage(x, y, width, height);
        ImageIO.write(eleScreenshot, "png", new File(filePath));
    }


    private static boolean compareImages(String baselinePath, String currentPath, String diffPath) throws IOException {
        BufferedImage baselineImage = ImageIO.read(new File(baselinePath));
        BufferedImage currentImage = ImageIO.read(new File(currentPath));

        if (baselineImage.getWidth() != currentImage.getWidth() || baselineImage.getHeight() != currentImage.getHeight()) {
            throw new IllegalArgumentException("Image dimensions do not match!");
        }

        BufferedImage diffImage = new BufferedImage(baselineImage.getWidth(), baselineImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        boolean hasDifferences = false;

        for (int y = 0; y < baselineImage.getHeight(); y++) {
            for (int x = 0; x < baselineImage.getWidth(); x++) {
                int baselinePixel = baselineImage.getRGB(x, y);
                int currentPixel = currentImage.getRGB(x, y);

                if (baselinePixel != currentPixel) {
                    diffImage.setRGB(x, y, 0xFF0000); // Highlight differences in red
                    hasDifferences = true;
                } else {
                    diffImage.setRGB(x, y, baselinePixel);
                }
            }
        }

        // Save the diff image
        ImageIO.write(diffImage, "png", new File(diffPath));

        return !hasDifferences;
    }
}
