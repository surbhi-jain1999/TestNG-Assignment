package com.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class ScenarioTests {

    private WebDriver driver;
    private String username = "surbhi_jain1";
    private String accessKey = "iB3K2vQQsQiimn1wKPOVNrHW14xD5p0WSzPXfRGwGvqedGxdvg";
    private String hub = "https://hub.lambdatest.com/wd/hub";

    @Parameters({"Browser", "Version", "Platform", "URL"})
    @BeforeMethod
    public void setUp(String browser, String version, String platform, String baseURL) {
        // Setting ChromeOptions with LambdaTest capabilities
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName(platform);
        browserOptions.setBrowserVersion(version);

        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", username);
        ltOptions.put("accessKey", accessKey);
        ltOptions.put("project", "Cross Browser Testing");
        ltOptions.put("build", "1.7");
        ltOptions.put("name", "ScenarioTests");
        ltOptions.put("selenium_version", "4.0.0");
        ltOptions.put("w3c", true);
        ltOptions.put("browserName", browser);
        ltOptions.put("browserVersion", version);
        ltOptions.put("platform", platform);

        browserOptions.setCapability("LT:Options", ltOptions);

        try {
            driver = new RemoteWebDriver(new URL(hub), browserOptions);
            driver.manage().window().maximize();
            driver.get(baseURL);
            System.out.println("navigated to base url successfully");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void scenarioOne() {
        System.out.println("Beginning execution of: Test Scenario 1");
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        // Wait for the page to load completely
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

        SoftAssert softAssert = new SoftAssert();
        String pageTitle = driver.getTitle();
        softAssert.assertEquals(pageTitle, "LambdaTest");
        softAssert.assertAll();
        System.out.println("Execution Complete: Test Scenario 1");
    }

    @Test
    public void scenarioTwo() {
        System.out.println("Beginning execution of: Test Scenario 2");
        WebElement checkboxDemoPageLink = driver.findElement(By.linkText("Checkbox Demo"));
        checkboxDemoPageLink.click();
        System.out.println("Checkbox Demo page link clicked");
        WebElement singleCheckboxDemoCheckbox = driver.findElement(By.xpath("//input[@id='isAgeSelected']"));
        singleCheckboxDemoCheckbox.click();
        System.out.println("Single Checkbox clicked");
        Assert.assertTrue(singleCheckboxDemoCheckbox.isSelected(), "Checkbox is not selected");
        singleCheckboxDemoCheckbox.click();
        Assert.assertFalse(singleCheckboxDemoCheckbox.isSelected(), "Checkbox is not deselected");
        System.out.println("Execution Complete: Test Scenario 2");
    }

    @Test(timeOut = 40000)
    public void scenarioThree() {
        System.out.println("Beginning execution of: Test Scenario 3");
        WebElement javascriptAlertsPageLink = driver.findElement(By.linkText("Javascript Alerts"));
        javascriptAlertsPageLink.click();
        System.out.println("Javascript Alerts page link clicked");
        WebElement clickButton = driver.findElement(By.xpath("(//button[@type='button'])[1]"));
        clickButton.click();
        System.out.println("Click Me button has been clicked");
        try {
            Alert alert = driver.switchTo().alert();
            String alertMessage = alert.getText();
            Assert.assertEquals(alertMessage, "I am an alert box!", "Alert message should be 'I am an alert box!'");
            alert.accept();
            System.out.println("Execution Complete: Test Scenario 3");
        }catch (NoAlertPresentException e){
            Assert.fail("Alert was not present: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
