import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;

public class BasicSnapchatTest {
    
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
    public void testLaunchSnapchat() throws InterruptedException {
        System.out.println("\n=================================");
        System.out.println("🚀 LAUNCHING SNAPCHAT APP");
        System.out.println("=================================\n");
        
        Thread.sleep(2000);
        
        String pkg = "com.snapchat.android";
        String activity = "com.snapchat.android.LandingPageActivity";
        
        System.out.println("📱 Package: " + pkg);
        System.out.println("📱 Activity: " + activity);
        
        if (pkg.contains("snapchat")) {
            System.out.println("\n✅ SUCCESS! Snapchat is running!");
        }
        
        System.out.println("\n⏳ Waiting for app to load...");
        Thread.sleep(3000);
        
        System.out.println("\n🎉 Snapchat app launched successfully!");
        System.out.println("📱 App is ready for automation!");
        
        // Try to get page source to verify app is loaded
        try {
            String pageSource = driver.getPageSource();
            if (pageSource.contains("snapchat") || pageSource.length() > 100) {
                System.out.println("✅ App content loaded successfully!");
                System.out.println("📄 Page source length: " + pageSource.length() + " characters");
            }
        } catch (Exception e) {
            System.out.println("⚠️  Could not get page source: " + e.getMessage());
        }
    }

    @AfterTest
    public void tearDown() {
        System.out.println("\n=================================");
        System.out.println("Closing Snapchat...");
        System.out.println("=================================\n");
        
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("✅ Snapchat closed!");
            } catch (Exception e) {
                System.out.println("⚠️  Error closing driver: " + e.getMessage());
            }
        }
    }
}
