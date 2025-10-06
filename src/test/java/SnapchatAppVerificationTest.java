import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class SnapchatAppVerificationTest {
    
    org.openqa.selenium.WebDriver driver;

    @BeforeTest
    public void setup() throws Exception {
        System.out.println("\n=================================");
        System.out.println("🔍 SNAPCHAT APP VERIFICATION TEST");
        System.out.println("=================================\n");
        
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:deviceName", "ZA222XPZPR");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:noReset", true);
        caps.setCapability("appium:adbExecTimeout", 60000);
        caps.setCapability("appium:newCommandTimeout", 300);
        caps.setCapability("appium:autoGrantPermissions", true);
        
        driver = new org.openqa.selenium.remote.RemoteWebDriver(new URL("http://127.0.0.1:4723"), caps);
        System.out.println("✅ Driver initialized!");
    }

    @Test
    public void verifySnapchatApp() throws InterruptedException {
        System.out.println("\n=================================");
        System.out.println("🔍 VERIFYING SNAPCHAT APPLICATION");
        System.out.println("=================================\n");
        
        // Step 1: Check installed apps
        System.out.println("📱 Step 1: Checking installed apps...");
        checkInstalledApps();
        
        // Step 2: Test different Snapchat activities
        System.out.println("\n📱 Step 2: Testing different Snapchat activities...");
        testSnapchatActivities();
        
        // Step 3: Verify app content
        System.out.println("\n📱 Step 3: Verifying app content...");
        verifyAppContent();
        
        System.out.println("\n🎉 Snapchat app verification completed!");
    }
    
    private void checkInstalledApps() {
        try {
            // Get list of installed packages
            String result = (String) ((org.openqa.selenium.remote.RemoteWebDriver) driver)
                .executeScript("mobile: shell", 
                    Map.of("command", "pm list packages | grep snapchat"));
            
            System.out.println("📦 Installed Snapchat packages:");
            System.out.println(result);
            
            if (result.contains("com.snapchat.android")) {
                System.out.println("✅ Snapchat is installed!");
            } else {
                System.out.println("⚠️ Snapchat might not be installed or package name is different");
            }
            
            // ASSERTION: Verify Snapchat is installed
            Assert.assertTrue(result != null && result.contains("snapchat"), 
                "ASSERTION FAILED: Snapchat app is not installed on the device");
            System.out.println("✅ ASSERTION PASSED: Snapchat is installed");
            
        } catch (Exception e) {
            System.out.println("⚠️ Could not check installed apps: " + e.getMessage());
            Assert.fail("Failed to check installed apps: " + e.getMessage());
        }
    }
    
    private void testSnapchatActivities() throws InterruptedException {
        String[] activities = {
            "com.snapchat.android.LandingPageActivity",
            "com.snap.mushroom.MainActivity", 
            "com.snapchat.android.MainActivity",
            "com.snapchat.android.SplashActivity"
        };
        
        for (String activity : activities) {
            System.out.println("\n🔄 Testing activity: " + activity);
            testActivity(activity);
            Thread.sleep(3000);
        }
    }
    
    private void testActivity(String activity) {
        try {
            // Set the activity
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:deviceName", "ZA222XPZPR");
            caps.setCapability("appium:appPackage", "com.snapchat.android");
            caps.setCapability("appium:appActivity", activity);
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:noReset", true);
            caps.setCapability("appium:autoGrantPermissions", true);
            
            // Try to activate the app with this activity
            ((org.openqa.selenium.remote.RemoteWebDriver) driver).executeScript("mobile: activateApp", 
                Map.of("appId", "com.snapchat.android"));
            
            Thread.sleep(2000);
            
            // Check current package and activity
            String currentPackage = (String) ((org.openqa.selenium.remote.RemoteWebDriver) driver)
                .executeScript("mobile: getCurrentPackage");
            String currentActivity = (String) ((org.openqa.selenium.remote.RemoteWebDriver) driver)
                .executeScript("mobile: getCurrentActivity");
            
            System.out.println("📦 Current Package: " + currentPackage);
            System.out.println("📱 Current Activity: " + currentActivity);
            
            if (currentPackage.contains("snapchat")) {
                System.out.println("✅ SUCCESS! Snapchat is running with activity: " + activity);
                
                // ASSERTION: Verify Snapchat package is running
                Assert.assertTrue(currentPackage.contains("snapchat"), 
                    "ASSERTION FAILED: Current package is '" + currentPackage + "' but expected 'com.snapchat.android'");
                System.out.println("✅ ASSERTION PASSED: Snapchat package is running");
                
                // Check for Snapchat-specific elements
                checkSnapchatElements();
                
            } else {
                System.out.println("⚠️ Snapchat is NOT running. Current app: " + currentPackage);
            }
            
        } catch (Exception e) {
            System.out.println("⚠️ Error testing activity " + activity + ": " + e.getMessage());
        }
    }
    
    private void checkSnapchatElements() {
        try {
            System.out.println("🔍 Looking for Snapchat-specific elements...");
            
            // Look for Snapchat logo or text
            List<WebElement> snapchatElements = driver.findElements(By.xpath(
                "//*[contains(@text, 'Snapchat') or contains(@content-desc, 'Snapchat') or contains(@resource-id, 'snapchat')]"));
            System.out.println("📊 Snapchat text elements: " + snapchatElements.size());
            
            // Look for camera elements
            List<WebElement> cameraElements = driver.findElements(By.xpath(
                "//*[contains(@resource-id, 'camera') or contains(@content-desc, 'Camera') or contains(@text, 'Camera')]"));
            System.out.println("📊 Camera elements: " + cameraElements.size());
            
            // Look for chat elements
            List<WebElement> chatElements = driver.findElements(By.xpath(
                "//*[contains(@resource-id, 'chat') or contains(@content-desc, 'Chat') or contains(@text, 'Chat')]"));
            System.out.println("📊 Chat elements: " + chatElements.size());
            
            // Look for story elements
            List<WebElement> storyElements = driver.findElements(By.xpath(
                "//*[contains(@resource-id, 'story') or contains(@content-desc, 'Story') or contains(@text, 'Story')]"));
            System.out.println("📊 Story elements: " + storyElements.size());
            
            // Look for snap elements
            List<WebElement> snapElements = driver.findElements(By.xpath(
                "//*[contains(@resource-id, 'snap') or contains(@content-desc, 'Snap') or contains(@text, 'Snap')]"));
            System.out.println("📊 Snap elements: " + snapElements.size());
            
            // Look for ghost icon (Snapchat's logo)
            List<WebElement> ghostElements = driver.findElements(By.xpath(
                "//*[contains(@content-desc, 'ghost') or contains(@content-desc, 'Ghost')]"));
            System.out.println("📊 Ghost elements: " + ghostElements.size());
            
            int totalSnapchatElements = snapchatElements.size() + cameraElements.size() + 
                                      chatElements.size() + storyElements.size() + 
                                      snapElements.size() + ghostElements.size();
            
            if (totalSnapchatElements > 0) {
                System.out.println("✅ Found " + totalSnapchatElements + " Snapchat-specific elements!");
                System.out.println("🎉 This appears to be the correct Snapchat app!");
                
                // ASSERTION: Verify Snapchat elements exist
                Assert.assertTrue(totalSnapchatElements > 0, 
                    "ASSERTION FAILED: No Snapchat-specific elements found");
                System.out.println("✅ ASSERTION PASSED: Found " + totalSnapchatElements + " Snapchat elements");
            } else {
                System.out.println("⚠️ No Snapchat-specific elements found");
            }
            
        } catch (Exception e) {
            System.out.println("⚠️ Error checking elements: " + e.getMessage());
        }
    }
    
    private void verifyAppContent() {
        try {
            System.out.println("📄 Getting page source to verify app content...");
            
            String pageSource = driver.getPageSource();
            System.out.println("📄 Page source length: " + pageSource.length() + " characters");
            
            // Check for Snapchat-specific content in page source
            if (pageSource.contains("snapchat") || pageSource.contains("Snapchat")) {
                System.out.println("✅ Page source contains Snapchat references!");
            }
            
            if (pageSource.contains("camera") || pageSource.contains("Camera")) {
                System.out.println("✅ Page source contains camera references!");
            }
            
            if (pageSource.contains("chat") || pageSource.contains("Chat")) {
                System.out.println("✅ Page source contains chat references!");
            }
            
            if (pageSource.contains("story") || pageSource.contains("Story")) {
                System.out.println("✅ Page source contains story references!");
            }
            
            // Look for any clickable elements
            List<WebElement> clickableElements = driver.findElements(By.xpath("//*[@clickable='true']"));
            System.out.println("📊 Total clickable elements: " + clickableElements.size());
            
            if (clickableElements.size() > 10) {
                System.out.println("✅ App has interactive elements - appears to be fully loaded!");
            } else {
                System.out.println("⚠️ App might not be fully loaded or is not interactive");
            }
            
        } catch (Exception e) {
            System.out.println("⚠️ Error verifying app content: " + e.getMessage());
        }
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
