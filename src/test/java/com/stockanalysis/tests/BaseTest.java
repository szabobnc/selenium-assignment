package com.stockanalysis.tests;

import com.stockanalysis.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;
import java.net.URL;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

public class BaseTest {

    // Protected so child test classes can access the driver
    protected WebDriver driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        // We do NOT need WebDriverManager when using RemoteWebDriver,
        // but it doesn't hurt to leave it.
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--incognito");

        // Required for Linux/Docker environments
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        if (Boolean.parseBoolean(ConfigReader.getProperty("headless"))) {
            options.addArguments("--headless=new");
        }

        // IMPORTANT: Notice the URL is "selenium-grid" instead of "localhost"
        driver = new RemoteWebDriver(new URL("http://selenium-grid:4444/wd/hub"), options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(ConfigReader.getProperty("baseUrl"));

        handleCookieBanner();
    }

    @AfterClass
    public void tearDown() {
        // Clean up after the test completes
        if (driver != null) {
            driver.quit();
        }
    }

    private void handleCookieBanner() {
        try {
            WebDriverWait bannerWait = new WebDriverWait(driver, Duration.ofSeconds(5));

            By acceptButtonLoc = By.cssSelector("button.fc-cta-consent");

            WebElement acceptBtn = bannerWait.until(ExpectedConditions.elementToBeClickable(acceptButtonLoc));
            acceptBtn.click();

            Thread.sleep(1000);

        } catch (Exception e) {
            System.out.println("Cookie banner does not appear or could not be closed: " + e.getMessage());
        }
    }
}