package com.stockanalysis.tests;

import com.stockanalysis.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;
import java.net.URL;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.Parameters;
import org.testng.annotations.Optional;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.net.MalformedURLException;

public class BaseTest {

    // Protected so child test classes can access the driver
    protected WebDriver driver;
    @Parameters("browser")
    @BeforeClass
    public void setUp(@Optional("chrome") String browser) throws MalformedURLException {
        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            if (Boolean.parseBoolean(ConfigReader.getProperty("headless"))) {
                firefoxOptions.addArguments("--headless");
            }
            driver = new RemoteWebDriver(new URL("http://selenium-grid:4444/wd/hub"), firefoxOptions);
        } else {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--window-size=1920,1080");
            chromeOptions.addArguments("--incognito");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            if (Boolean.parseBoolean(ConfigReader.getProperty("headless"))) {
                chromeOptions.addArguments("--headless=new");
            }
            driver = new RemoteWebDriver(new URL("http://selenium-grid:4444/wd/hub"), chromeOptions);
        }
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