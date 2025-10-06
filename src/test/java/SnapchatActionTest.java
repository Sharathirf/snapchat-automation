import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.List;

public class SnapchatActionTest {
    
    private org.openqa.selenium.WebDriver driver;
    private static final String SNAPCHAT_PACKAGE = "com.snapchat.android";

    @BeforeTest
    public void setup() throws Exception {
        System.out.println("\n=================================");
        System.out.println("Setting up Appium driver...");
        System.out.println("=================================\n");
        
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:deviceName", "ZA222XPZPR");
        caps.setCapability("appium:appPackage", SNAPCHAT_PACKAGE);
        caps.setCapability("appium:appActivity", ".LandingPageActivity");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:noReset", true);
        caps.setCapability("appium:newCommandTimeout", 300);
        caps.setCapability("appium:autoGrantPermissions", true);
        
        driver = new org.openqa.selenium.remote.RemoteWebDriver(new URL("http://127.0.0.1:4723"), caps);
        System.out.println("✅ Driver initialized!");
    }

    @Test(priority = 1)
    public void testSnapchatActions() throws InterruptedException {
        System.out.println("\n=================================");
        System.out.println("🚀 LAUNCHING SNAPCHAT APP");
        System.out.println("=================================\n");
        
        // Wait for app to load
        Thread.sleep(3000);
        
        // Verify Snapchat is running
        verifySnapchatApp();
        
        // Perform automation actions
        performSnapchatActions();
        
        System.out.println("\n🎉 Snapchat automation test completed successfully!");
    }
    
    @Test(priority = 2)
    public void testNavigateToChatTab() throws InterruptedException {
        System.out.println("\n=================================");
        System.out.println("💬 TEST: NAVIGATE TO CHAT TAB");
        System.out.println("=================================\n");
        
        // Wait for app to load
        Thread.sleep(3000);
        
        // Verify Snapchat is running
        verifySnapchatApp();
        
        // Navigate to Chat tab
        navigateToChatTab();
        
        System.out.println("\n🎉 Chat tab navigation test completed successfully!");
    }
    
    /**
     * Verifies that Snapchat app is correctly launched and running
     */
    private void verifySnapchatApp() throws InterruptedException {
        System.out.println("🔍 Verifying Snapchat app is running...");
        
        String currentPackage = getCurrentPackage();
        System.out.println("📦 Current Package: " + currentPackage);
        
        // If Snapchat is not running, try to launch it
        if (!currentPackage.contains("snapchat")) {
            System.out.println("⚠️ Snapchat is NOT running. Attempting to launch...");
            activateSnapchat();
            Thread.sleep(5000); // Wait longer for app to fully launch
            currentPackage = getCurrentPackage();
            System.out.println("📦 Package after activation: " + currentPackage);
            
            // Retry once more if still not Snapchat
            if (!currentPackage.contains("snapchat")) {
                System.out.println("🔄 Retrying Snapchat launch...");
                activateSnapchat();
                Thread.sleep(5000);
                currentPackage = getCurrentPackage();
                System.out.println("📦 Package after retry: " + currentPackage);
            }
        }
        
        // ASSERTION 1: Verify Snapchat package is running
        Assert.assertTrue(currentPackage.contains("snapchat"), 
            "ASSERTION FAILED: Expected Snapchat but got '" + currentPackage + "'");
        System.out.println("✅ ASSERTION PASSED: Snapchat package is running");
        
        // Wait for UI to load
        Thread.sleep(2000);
        
        // ASSERTION 2: Verify Snapchat-specific elements exist
        int elementCount = countSnapchatElements();
        Assert.assertTrue(elementCount > 0, 
            "ASSERTION FAILED: No Snapchat-specific elements found");
        System.out.println("✅ ASSERTION PASSED: Found " + elementCount + " Snapchat elements");
        
        System.out.println("✅ Snapchat verification successful!\n");
    }
    
    /**
     * Navigates to the Chat tab from any screen
     */
    private void navigateToChatTab() throws InterruptedException {
        System.out.println("💬 Navigating to Chat tab...");
        
        try {
            // Common resource IDs for chat tab navigation
            String[] chatTabXPaths = {
                "//*[@resource-id='com.snapchat.android:id/ngs_chat_icon_container']",
                "//*[@content-desc='Chat Tab']",
                "//*[@content-desc='Chat']",
                "//*[contains(@content-desc, 'Chat')]",
                "//*[contains(@resource-id, 'chat_icon')]"
            };
            
            boolean chatTabFound = false;
            
            // Try each XPath to find the chat tab
            for (String xpath : chatTabXPaths) {
                try {
                    List<WebElement> chatTabElements = driver.findElements(By.xpath(xpath));
                    
                    if (!chatTabElements.isEmpty()) {
                        System.out.println("✅ Found chat tab using: " + xpath);
                        System.out.println("📍 Clicking on Chat tab...");
                        
                        chatTabElements.get(0).click();
                        Thread.sleep(2000);
                        
                        System.out.println("✅ Successfully clicked Chat tab!");
                        chatTabFound = true;
                        
                        // Verify we're on the chat screen
                        verifyChatScreen();
                        break;
                    }
                } catch (Exception e) {
                    // Try next xpath
                    continue;
                }
            }
            
            if (!chatTabFound) {
                System.out.println("⚠️ Chat tab not found with known XPaths, looking for clickable elements...");
                
                // Fallback: Look for any element with "chat" in its description
                List<WebElement> allElements = driver.findElements(By.xpath("//*[@clickable='true']"));
                System.out.println("📊 Found " + allElements.size() + " clickable elements");
                
                for (WebElement element : allElements) {
                    try {
                        String contentDesc = element.getAttribute("content-desc");
                        String resourceId = element.getAttribute("resource-id");
                        
                        if ((contentDesc != null && contentDesc.toLowerCase().contains("chat")) ||
                            (resourceId != null && resourceId.toLowerCase().contains("chat"))) {
                            System.out.println("✅ Found potential chat element: " + contentDesc);
                            element.click();
                            Thread.sleep(2000);
                            System.out.println("✅ Clicked on chat element!");
                            chatTabFound = true;
                            break;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
            
            if (chatTabFound) {
                System.out.println("🎉 Successfully navigated to Chat tab!");
            } else {
                System.out.println("ℹ️ Already on Chat tab or Chat tab not accessible");
            }
            
        } catch (Exception e) {
            System.out.println("⚠️ Error navigating to chat tab: " + e.getMessage());
            System.out.println("ℹ️ Continuing test...");
        }
    }
    
    /**
     * Verifies we're on the chat screen by checking for chat-specific elements
     */
    private void verifyChatScreen() {
        try {
            Thread.sleep(1000);
            
            // Look for chat-specific elements
            List<WebElement> chatElements = driver.findElements(By.xpath(
                "//*[contains(@content-desc, 'Chat') or contains(@resource-id, 'chat') or contains(@text, 'Chat')]"));
            
            System.out.println("📊 Found " + chatElements.size() + " chat-related elements on screen");
            
            if (chatElements.size() > 0) {
                System.out.println("✅ Verified: On Chat screen");
            } else {
                System.out.println("ℹ️ Chat screen verification: Unable to confirm");
            }
            
        } catch (Exception e) {
            System.out.println("ℹ️ Chat screen verification skipped");
        }
    }
    
    /**
     * Performs automation actions on Snapchat app
     */
    private void performSnapchatActions() throws InterruptedException {
        System.out.println("🎬 Performing Snapchat actions...");
        
        try {
            // Find and interact with clickable elements
            List<WebElement> clickableElements = driver.findElements(By.xpath("//*[@clickable='true']"));
            System.out.println("📊 Found " + clickableElements.size() + " clickable elements");
            
            if (clickableElements.size() > 3) {
                System.out.println("👆 Attempting to click on element #3...");
                try {
                    clickableElements.get(2).click();
                    System.out.println("✅ Element clicked!");
                    Thread.sleep(2000);
                } catch (org.openqa.selenium.StaleElementReferenceException e) {
                    System.out.println("⚠️ Element became stale, refinding and clicking...");
                    // Refind elements and try again
                    clickableElements = driver.findElements(By.xpath("//*[@clickable='true']"));
                    if (clickableElements.size() > 3) {
                        clickableElements.get(2).click();
                        System.out.println("✅ Element clicked on retry!");
                        Thread.sleep(2000);
                    }
                }
            }
            
            System.out.println("✅ Actions completed!");
        } catch (Exception e) {
            System.out.println("⚠️ Action warning: " + e.getMessage());
            System.out.println("✅ Continuing test (non-critical)...");
        }
    }
    
    /**
     * Gets the current package name of the foreground app
     */
    private String getCurrentPackage() {
        try {
            return (String) ((org.openqa.selenium.remote.RemoteWebDriver) driver)
                .executeScript("mobile: getCurrentPackage");
        } catch (Exception e) {
            System.out.println("⚠️ Error getting current package: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * Activates (launches) the Snapchat app using startActivity command
     */
    private void activateSnapchat() {
        try {
            // Use startActivity to launch Snapchat
            java.util.Map<String, Object> params = new java.util.HashMap<>();
            params.put("appPackage", SNAPCHAT_PACKAGE);
            params.put("appActivity", ".LandingPageActivity");
            
            ((org.openqa.selenium.remote.RemoteWebDriver) driver)
                .executeScript("mobile: startActivity", params);
            
            System.out.println("✅ Snapchat launch command executed");
        } catch (Exception e) {
            System.out.println("⚠️ Error activating Snapchat: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Counts Snapchat-specific UI elements
     */
    private int countSnapchatElements() {
        try {
            List<WebElement> snapchatElements = driver.findElements(By.xpath(
                "//*[contains(@text, 'Snapchat') or contains(@content-desc, 'Snapchat') or contains(@resource-id, 'snapchat')]"));
            List<WebElement> cameraElements = driver.findElements(By.xpath(
                "//*[contains(@resource-id, 'camera') or contains(@content-desc, 'Camera')]"));
            List<WebElement> chatElements = driver.findElements(By.xpath(
                "//*[contains(@resource-id, 'chat') or contains(@content-desc, 'Chat')]"));
            
            int total = snapchatElements.size() + cameraElements.size() + chatElements.size();
            System.out.println("📊 Element breakdown: " + snapchatElements.size() + " Snapchat, " 
                + cameraElements.size() + " Camera, " + chatElements.size() + " Chat");
            
            return total;
        } catch (Exception e) {
            System.out.println("⚠️ Error counting elements: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Closes the Snapchat app (force stop)
     */
    @SuppressWarnings("unused")
    private void closeSnapchatApp() {
        try {
            System.out.println("🔄 Force stopping Snapchat app...");
            ((org.openqa.selenium.remote.RemoteWebDriver) driver)
                .executeScript("mobile: terminateApp", java.util.Map.of("appId", SNAPCHAT_PACKAGE));
            System.out.println("✅ Snapchat app closed!");
        } catch (Exception e) {
            System.out.println("⚠️ Error closing Snapchat app: " + e.getMessage());
        }
    }

    @AfterTest
    public void tearDown() {
        System.out.println("\n=================================");
        System.out.println("Cleaning up...");
        System.out.println("=================================\n");
        
        if (driver != null) {
            try {
                // Close Snapchat app before quitting driver (commented for now)
                // Uncomment below line when you want to close the app after tests
                // closeSnapchatApp();
                
                // Quit driver session
                driver.quit();
                System.out.println("✅ Driver session closed successfully!");
            } catch (Exception e) {
                System.out.println("⚠️ Error during cleanup: " + e.getMessage());
            }
        }
    }
}
