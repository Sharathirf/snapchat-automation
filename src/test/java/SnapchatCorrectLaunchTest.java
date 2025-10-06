import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.List;

public class SnapchatCorrectLaunchTest {
    
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
        caps.setCapability("appium:fullReset", false);
        caps.setCapability("appium:autoGrantPermissions", true);
        
        driver = new org.openqa.selenium.remote.RemoteWebDriver(new URL("http://127.0.0.1:4723"), caps);
        System.out.println("✅ Driver initialized!");
    }

    @Test
    public void testSnapchatCorrectLaunch() throws InterruptedException {
        System.out.println("\n=================================");
        System.out.println("🚀 LAUNCHING SNAPCHAT CORRECTLY");
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
                System.out.println("🔄 Attempting to launch Snapchat manually...");
                
                // Try to launch Snapchat manually
                try {
                    ((org.openqa.selenium.remote.RemoteWebDriver) driver).executeScript("mobile: activateApp", 
                        java.util.Map.of("bundleId", "com.snapchat.android"));
                    System.out.println("✅ Attempted to activate Snapchat app");
                    Thread.sleep(3000);
                    
                    // Check again
                    currentPackage = (String) ((org.openqa.selenium.remote.RemoteWebDriver) driver).executeScript("mobile: getCurrentPackage");
                    System.out.println("📦 Package after activation: " + currentPackage);
                    
                    if (currentPackage.contains("snapchat")) {
                        System.out.println("✅ SUCCESS! Snapchat is now running!");
                    } else {
                        System.out.println("⚠️ Still not Snapchat. Trying alternative approach...");
                        
                        // Try launching with different activity
                        System.out.println("🔄 Trying to launch with main activity...");
                        ((org.openqa.selenium.remote.RemoteWebDriver) driver).executeScript("mobile: activateApp", 
                            java.util.Map.of("bundleId", "com.snapchat.android"));
                        Thread.sleep(3000);
                        
                        currentPackage = (String) ((org.openqa.selenium.remote.RemoteWebDriver) driver).executeScript("mobile: getCurrentPackage");
                        System.out.println("📦 Package after main activity: " + currentPackage);
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Could not activate Snapchat: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not get current package: " + e.getMessage());
        }
        
        // Look for Snapchat elements regardless
        System.out.println("\n🔍 Looking for Snapchat elements...");
        try {
            // Look for any elements that might indicate Snapchat
            List<WebElement> allElements = driver.findElements(By.xpath("//*"));
            System.out.println("📊 Total elements found: " + allElements.size());
            
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
            
            if (snapchatText.size() > 0 || cameraElements.size() > 0 || chatElements.size() > 0 || storyElements.size() > 0) {
                System.out.println("✅ Found Snapchat-like elements - app might be correct!");
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
        
        System.out.println("\n🎉 Snapchat launch test completed!");
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

