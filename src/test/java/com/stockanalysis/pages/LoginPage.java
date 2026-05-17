package com.stockanalysis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

// Task: Page object pattern (3 pts) - Class 1
public class LoginPage extends BasePage {
    
    // Locators
    private final By emailInput = By.id("email");
    private final By passwordInput = By.id("password");
    
    // Task: Complex XPath (1/4) - Finding a specific button by type and descendant text
    private final By loginBtn = By.xpath("//button[@type='submit' and contains(., 'Log In')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        // 1. Wait until the email field is fully interactable, then click and type
        wait.until(ExpectedConditions.elementToBeClickable(emailInput)).click();
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email); // Task: Fill input 1
        
        // 2. Wait for password field, click and type
        wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).click();
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password); // Task: Fill input 2
        
        // 3. Wait for the login button to be clickable before clicking
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click(); // Task: Send form 1 (Login)
    }
}