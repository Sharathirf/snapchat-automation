# Snapchat Automation Framework

A robust Java Appium automation framework for mobile testing with comprehensive features and best practices.

## 🚀 Features

- **Page Object Model (POM)** - Clean, maintainable test structure
- **Multi-platform Support** - Android and iOS compatibility
- **Comprehensive Reporting** - ExtentReports and Allure integration
- **Advanced Logging** - Log4j2 with file and console output
- **Screenshot Management** - Automatic screenshots on failures
- **Test Data Management** - JSON, Properties, and Environment variable support
- **Configuration Management** - Flexible configuration system
- **Utility Classes** - Reusable methods for common operations
- **CI/CD Ready** - Maven integration with profiles

## 📁 Project Structure

```
src/
├── main/java/com/automation/
│   ├── config/          # Configuration management
│   ├── driver/          # Driver management
│   ├── utils/           # Utility classes
│   ├── pages/           # Base page class
│   ├── reports/         # Reporting utilities
│   ├── data/            # Test data management
│   └── tests/           # Base test class
└── test/java/com/automation/
    ├── pages/           # Page object classes
    └── tests/           # Test classes
└── test/resources/
    ├── config/          # Configuration files
    ├── testdata/        # Test data files
    ├── screenshots/     # Screenshot storage
    └── videos/          # Video recording storage
```

## 🛠️ Prerequisites

- Java 11 or higher
- Maven 3.6+
- Android SDK (for Android testing)
- Xcode (for iOS testing)
- Appium Server
- Node.js and npm

## ⚙️ Setup

### 1. Install Dependencies

```bash
# Install Appium
npm install -g appium

# Install UiAutomator2 driver (Android)
appium driver install uiautomator2

# Install XCUITest driver (iOS)
appium driver install xcuitest
```

### 2. Configure Environment

Update the configuration files:

- `src/test/resources/config/config.properties`
- `src/test/resources/config/config.json`

### 3. Device Setup

#### Android:
```bash
# Enable USB Debugging
# Connect device via USB
adb devices
```

#### iOS:
```bash
# Install WebDriverAgent
# Configure signing certificates
```

## 🚀 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=SnapchatAutomationTest
```

### Run with Specific Profile
```bash
# Android
mvn test -Pandroid

# iOS
mvn test -Pios
```

### Run with Custom Configuration
```bash
mvn test -Dplatform.name=android -Ddevice.name=YourDeviceName
```

## 📊 Reports

### ExtentReports
- Location: `target/reports/TestReport_*.html`
- Features: Interactive HTML reports with screenshots

### Allure Reports
```bash
# Generate Allure report
mvn allure:serve
```

## 🔧 Configuration

### System Properties
```bash
-Dplatform.name=android
-Ddevice.name=YourDevice
-Dapp.package=com.snapchat.android
-Dapp.activity=com.snapchat.android.LandingPageActivity
-Dappium.server.url=http://127.0.0.1:4723
```

### Environment Variables
```bash
export TEST_USERNAME=your_username
export TEST_PASSWORD=your_password
export API_BASE_URL=https://api.example.com
```

## 📝 Writing Tests

### 1. Create Page Object
```java
public class MyPage extends BasePage {
    private static final By ELEMENT_LOCATOR = AppiumBy.id("element.id");
    
    @Override
    public boolean isPageLoaded() {
        return isElementPresent(ELEMENT_LOCATOR);
    }
    
    public void clickElement() {
        click(ELEMENT_LOCATOR);
    }
}
```

### 2. Create Test Class
```java
public class MyTest extends BaseTest {
    private MyPage myPage;
    
    @Test
    public void testSomething() {
        myPage = new MyPage();
        myPage.clickElement();
        takeScreenshot("Element clicked");
        logPass("Test completed successfully");
    }
}
```

## 🛠️ Framework Components

### BaseTest
- Test lifecycle management
- Screenshot capture
- Logging and reporting
- Assertion helpers

### BasePage
- Common element interactions
- Mobile-specific methods
- Gesture support
- Wait utilities

### DriverManager
- Multi-platform driver initialization
- Capability management
- Driver lifecycle

### ConfigManager
- Configuration loading
- Property management
- Environment support

### TestDataManager
- Test data loading
- Data generation
- Environment variables

## 📱 Mobile-Specific Features

### Gestures
```java
// Swipe actions
actionUtils.swipeUp();
actionUtils.swipeDown();
actionUtils.swipeLeft();
actionUtils.swipeRight();

// Tap actions
actionUtils.tap(element);
actionUtils.longPress(element);
actionUtils.doubleTap(element);
```

### App Management
```java
// App lifecycle
driverManager.launchApp();
driverManager.closeApp();
driverManager.resetApp();
```

## 🔍 Debugging

### Logs
- Console output with timestamps
- File logs in `target/logs/`
- Rolling file appender

### Screenshots
- Automatic on test failures
- Manual capture with `takeScreenshot()`
- Stored in `target/screenshots/`

### Video Recording
```java
// Enable in configuration
video.recording=true
```

## 🚀 CI/CD Integration

### Jenkins Pipeline
```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }
}
```

### GitHub Actions
```yaml
name: Mobile Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '11'
      - run: mvn clean test
      - uses: simple-elf/allure-report-action@master
        with:
          allure_results: target/allure-results
```

## 📚 Best Practices

1. **Page Object Model** - Keep UI logic in page classes
2. **Data-Driven Testing** - Use external test data files
3. **Screenshot Strategy** - Capture screenshots at key points
4. **Wait Strategies** - Use explicit waits over implicit waits
5. **Error Handling** - Implement proper exception handling
6. **Test Independence** - Ensure tests can run independently
7. **Configuration Management** - Use external configuration files
8. **Logging** - Implement comprehensive logging

## 🐛 Troubleshooting

### Common Issues

1. **Driver Initialization Failed**
   - Check Appium server is running
   - Verify device connection
   - Check capabilities configuration

2. **Element Not Found**
   - Verify element locators
   - Check wait conditions
   - Ensure app is in correct state

3. **Test Failures**
   - Check logs in `target/logs/`
   - Review screenshots in `target/screenshots/`
   - Verify test data

### Debug Mode
```bash
mvn test -Dtest=YourTest -Ddebug=true
```

## 📞 Support

For issues and questions:
1. Check the logs in `target/logs/`
2. Review screenshots in `target/screenshots/`
3. Check configuration files
4. Verify device and Appium setup

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

---

**Happy Testing! 🎉**
