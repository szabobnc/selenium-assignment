package com.stockanalysis.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

// Task: Base page class (1 pt)
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        // Task: Explicit wait (3 pts) - Initialized here to be used across all pages
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Task: Page title (1 pt)
    public String getPageTitle() {
        return driver.getTitle();
    }
}