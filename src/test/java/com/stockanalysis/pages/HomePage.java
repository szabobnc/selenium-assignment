package com.stockanalysis.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private final By logoutLink = By.xpath("//button[text()='Log Out']");
    private final By openMenu = By.xpath("//button[@aria-label='Open Menu']");
      
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void logout() {
        // Try to click the logout link directly, if it fails, try opening the menu first
        try{   
            wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
        } catch(Exception e) {
            System.out.println("Logout link not found, trying to open menu: " + e.getMessage());
            wait.until(ExpectedConditions.elementToBeClickable(openMenu)).click();
            wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
        }
    }

    public void closeWelcomeModal() {
        try {
            Thread.sleep(2000);
            
            new Actions(driver).sendKeys(Keys.ESCAPE).perform();
            
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("No modal found or could not close it: " + e.getMessage());
        }
    }

    public void searchAndHoverStock(String ticker) {
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("search-home-page")));
        searchInput.clear();
        searchInput.sendKeys(ticker);

        By resultLocator = By.xpath("//a[contains(@class, 'sres')]//span[text()='" + ticker + "']");
        WebElement resultElement = wait.until(ExpectedConditions.elementToBeClickable(resultLocator));

        // Hover over the result to trigger any hover effects (e.g. tooltip, preview)
        new Actions(driver)
            .moveToElement(resultElement)
            .perform();

        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // Click the result to navigate to the stock page
        resultElement.click();
    }
}