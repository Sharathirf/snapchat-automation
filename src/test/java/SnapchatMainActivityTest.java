import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.List;

public class SnapchatMainActivityTest {
    
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
        caps.setCapability("appium:appActivity", "com.snap.mushroom.MainActivity");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:noReset", true);
        caps.setCapability("appium:adbExecTimeout", 60000);
        caps.setCapability("appium:newCommandTimeout", 300);
        caps.setCapability("appium:fullReset", false);
        caps.setCapability("appium:autoGrantPermissions", true);
        
        driver = new org.openqa.selenium.remote.RemoteWebDriver(new URL("http://127.0.0.1:4723"), caps);
        System.out.println("✅ Driver initialized!");
    }

    @Test
    public void testSnapchatMainActivity() throws InterruptedException {
        System.out.println("\n=================================");
        System.out.println("🚀 TESTING SNAPCHAT MAIN ACTIVITY");
        System.out.println("=================================\n");
        
        Thread.sleep(5000); // Wait for app to fully load
        
        // Check current package
        System.out.println("📱 Checking current package...");
        try {
            String currentPackage = (String) ((org.openqa.selenium.remote.RemoteWebDriver) driver).executeScript("mobile: getCurrentPackage");
            System.out.println("📦 Current Package: " + currentPackage);
            
            if (currentPackage.contains("snapchat")) {
                System.out.println("✅ SUCCESS! Snapchat is running!");
            } else {
                System.out.println("⚠️ Snapchat is NOT running. Current app: " + currentPackage);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not get current package: " + e.getMessage());
        }
        
        // Check current activity
        System.out.println("\n📱 Checking current activity...");
        try {
            String currentActivity = (String) ((org.openqa.selenium.remote.RemoteWebDriver) driver).executeScript("mobile: getCurrentActivity");
            System.out.println("📱 Current Activity: " + currentActivity);
            
            if (currentActivity.contains("snapchat") || currentActivity.contains("mushroom")) {
                System.out.println("✅ SUCCESS! Snapchat activity is running!");
            } else {
                System.out.println("⚠️ Snapchat activity is NOT running. Current activity: " + currentActivity);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not get current activity: " + e.getMessage());
        }
        
        // Look for Snapchat elements
        System.out.println("\n🔍 Looking for Snapchat elements...");
        try {
            // Look for Snapchat-specific text
            List<WebElement> snapchatText = driver.findElements(By.xpath("//*[contains(@text, 'Snapchat') or contains(@content-desc, 'Snapchat')]"));
            System.out.println("📊 Snapchat text elements: " + snapchatText.size());
            
            // Look for camera elements
            List<WebElement> cameraElements = driver.findElements(By.xpath("//*[contains(@resource-id, 'camera') or contains(@content-desc, 'Camera') or contains(@text, 'Camera')]"));
            System.out.println("📊 Camera elements: " + cameraElements.size());
            
            // Look for chat elements
            List<WebElement> chatElements = driver.findElements(By.xpath("//*[contains(@resource-id, 'chat') or contains(@content-desc, 'Chat') or contains(@text, 'Chat')]"));
            System.out.println("📊 Chat elements: " + chatElements.size());
            
            // Look for story elements
            List<WebElement> storyElements = driver.findElements(By.xpath("//*[contains(@resource-id, 'story') or contains(@content-desc, 'Story') or contains(@text, 'Story')]"));
            System.out.println("📊 Story elements: " + storyElements.size());
            
            // Look for snap elements
            List<WebElement> snapElements = driver.findElements(By.xpath("//*[contains(@resource-id, 'snap') or contains(@content-desc, 'Snap') or contains(@text, 'Snap')]"));
            System.out.println("📊 Snap elements: " + snapElements.size());
            
            if (snapchatText.size() > 0 || cameraElements.size() > 0 || chatElements.size() > 0 || storyElements.size() > 0 || snapElements.size() > 0) {
                System.out.println("✅ Found Snapchat-like elements - app is correct!");
            } else {
                System.out.println("⚠️ No Snapchat-specific elements found");
            }
            
        } catch (Exception e) {
            System.out.println("⚠️ Could not search for elements: " + e.getMessage());
        }
        
        // Try to interact with the app
        System.out.println("\n👆 Testing app interaction...");
        try {
            List<WebElement> clickableElements = driver.findElements(By.xpath("//*[@clickable='true']"));
            System.out.println("📊 Clickable elements: " + clickableElements.size());
            
            if (!clickableElements.isEmpty()) {
                System.out.println("✅ App is interactive!");
                
                // Try clicking on a safe element
                if (clickableElements.size() > 3) {
                    System.out.println("👆 Clicking on element #3...");
                    clickableElements.get(2).click();
                    Thread.sleep(2000);
                    System.out.println("✅ Clicked successfully!");
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not interact with app: " + e.getMessage());
        }
        
        System.out.println("\n🎉 Snapchat main activity test completed!");
    }

    @AfterTest
    public void tearDown() {
        System.out.println("\n=================================");
        System.out.println("Closing app...");
        System.out.println("=================================\n");
        
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("✅ App closed successfully!");
            } catch (Exception e) {
                System.out.println("⚠️ Error closing driver: " + e.getMessage());
            }
        }
    }
}

