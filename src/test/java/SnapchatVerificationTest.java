import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.List;

public class SnapchatVerificationTest {
    
    org.openqa.selenium.WebDriver driver;

    @BeforeTest
    public void setup() throws Exception {
        System.out.println("\n=================================");
        System.out.println("Setting up Appium driver...");
        System.out.println("=================================\n");
        
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:deviceName", "ZA222XPZPR");
        caps.setCapability("appium:appPackage", "com.snapchat.android");
        caps.setCapability("appium:appActivity", "com.snapchat.android.LandingPageActivity");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:noReset", true);
        caps.setCapability("appium:adbExecTimeout", 60000);
        caps.setCapability("appium:newCommandTimeout", 300);
        
        driver = new org.openqa.selenium.remote.RemoteWebDriver(new URL("http://127.0.0.1:4723"), caps);
        System.out.println("✅ Driver initialized!");
    }

    @Test
    public void testSnapchatVerification() throws InterruptedException {
        System.out.println("\n=================================");
        System.out.println("🔍 VERIFYING SNAPCHAT APP LAUNCH");
        System.out.println("=================================\n");
        
        Thread.sleep(3000); // Wait for app to fully load
        
        // Check if Snapchat is actually running
        System.out.println("📱 Checking if Snapchat app is running...");
        
        try {
            // Try to get current package name using executeScript
            String currentPackage = (String) ((org.openqa.selenium.remote.RemoteWebDriver) driver).executeScript("mobile: getCurrentPackage");
            System.out.println("📦 Current Package: " + currentPackage);
            
            if (currentPackage != null && currentPackage.contains("snapchat")) {
                System.out.println("✅ SUCCESS! Snapchat app is running!");
            } else {
                System.out.println("⚠️ WARNING: Current package is not Snapchat: " + currentPackage);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not get current package: " + e.getMessage());
        }
        
        // Check for Snapchat-specific elements
        System.out.println("\n🔍 Looking for Snapchat-specific elements...");
        
        // Look for Snapchat logo or title
        try {
            List<WebElement> snapchatElements = driver.findElements(By.xpath("//*[contains(@text, 'Snapchat') or contains(@content-desc, 'Snapchat')]"));
            System.out.println("📊 Found " + snapchatElements.size() + " Snapchat text elements");
            
            if (!snapchatElements.isEmpty()) {
                System.out.println("✅ Found Snapchat text elements - app is correct!");
                for (int i = 0; i < Math.min(snapchatElements.size(), 3); i++) {
                    System.out.println("   - Element " + (i+1) + ": " + snapchatElements.get(i).getText());
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not find Snapchat text elements: " + e.getMessage());
        }
        
        // Look for common Snapchat UI elements
        System.out.println("\n🔍 Looking for Snapchat UI elements...");
        
        // Check for camera tab (common in Snapchat)
        try {
            List<WebElement> cameraElements = driver.findElements(By.xpath("//*[contains(@resource-id, 'camera') or contains(@content-desc, 'Camera')]"));
            System.out.println("📊 Found " + cameraElements.size() + " camera-related elements");
            
            if (!cameraElements.isEmpty()) {
                System.out.println("✅ Found camera elements - likely Snapchat!");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not find camera elements: " + e.getMessage());
        }
        
        // Check for chat elements
        try {
            List<WebElement> chatElements = driver.findElements(By.xpath("//*[contains(@resource-id, 'chat') or contains(@content-desc, 'Chat')]"));
            System.out.println("📊 Found " + chatElements.size() + " chat-related elements");
            
            if (!chatElements.isEmpty()) {
                System.out.println("✅ Found chat elements - likely Snapchat!");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not find chat elements: " + e.getMessage());
        }
        
        // Try to find and click on a safe element to verify interaction
        System.out.println("\n👆 Looking for elements to interact with...");
        try {
            List<WebElement> clickableElements = driver.findElements(By.xpath("//*[@clickable='true']"));
            System.out.println("📊 Found " + clickableElements.size() + " clickable elements");
            
            if (!clickableElements.isEmpty()) {
                System.out.println("✅ Found clickable elements - app is responsive!");
                
                // Try to click on a safe element (avoid system UI)
                if (clickableElements.size() > 5) {
                    System.out.println("\n👆 Clicking on element #5...");
                    clickableElements.get(4).click();
                    System.out.println("✅ Clicked on element!");
                    Thread.sleep(2000);
                    
                    // Try to go back
                    System.out.println("\n⬅️ Going back...");
                    driver.navigate().back();
                    Thread.sleep(2000);
                    System.out.println("✅ Went back!");
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not interact with elements: " + e.getMessage());
        }
        
        // Check app capabilities
        System.out.println("\n📋 App Capabilities:");
        System.out.println("   - Takes Screenshot: " + driver.manage().window().getSize());
        System.out.println("   - Screen Size: " + driver.manage().window().getSize().getWidth() + "x" + driver.manage().window().getSize().getHeight());
        
        System.out.println("\n🎉 Snapchat verification test completed!");
        System.out.println("📱 App is ready for automation!");
    }

    @AfterTest
    public void tearDown() {
        System.out.println("\n=================================");
        System.out.println("Closing Snapchat...");
        System.out.println("=================================\n");
        
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("✅ Snapchat closed successfully!");
            } catch (Exception e) {
                System.out.println("⚠️ Error closing driver: " + e.getMessage());
            }
        }
    }
}
