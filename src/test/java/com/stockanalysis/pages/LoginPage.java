package com.stockanalysis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private final By emailInput = By.id("email");
    private final By passwordInput = By.id("password");
    
    private final By loginBtn = By.xpath("//button[@type='submit' and contains(., 'Log In')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        // Wait until the email field is fully interactable, then click and type
        wait.until(ExpectedConditions.elementToBeClickable(emailInput)).click();
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
        
        // Wait for password field, click and type
        wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).click();
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
        
        //Wait for the login button to be clickable before clicking
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }
}