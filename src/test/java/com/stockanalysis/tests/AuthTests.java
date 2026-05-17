package com.stockanalysis.tests;

import com.stockanalysis.pages.HomePage;
import com.stockanalysis.pages.LoginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

public class AuthTests extends BaseTest {

    @Test
    public void invalidLoginShowsErrorMessage() {
    driver.get("https://stockanalysis.com/login/");

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    LoginPage loginPage = new LoginPage(driver);
    loginPage.login("invaliduser@invalid.com", "invalidpassword");

    // Verify error message is displayed
    By errorMessageLoc = By.xpath("//div[text()='Wrong or invalid email or password. Please try again.']");
    boolean isErrorMessageVisible =
    wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageLoc))
    .isDisplayed();
    Assert.assertTrue(isErrorMessageVisible, "Error message is not visible.");
    }

    @Test
    public void testSuccessfulLogin() {
        driver.get("https://stockanalysis.com/login/");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        // Login with valid credentials
        loginPage.login("szabomail00@gmail.com", "StrongPassword123!");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        homePage.closeWelcomeModal();

        // Verify login was successful by checking for the presence of "My Account" link
        By myAccountLoc = By.xpath("//a[text()='My Account']");
        boolean isMyAccountVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(myAccountLoc))
                .isDisplayed();

        Assert.assertTrue(isMyAccountVisible, "My Account link is not visible, login might have failed.");
    }

    @Test(dependsOnMethods = "testSuccessfulLogin")
    public void testRandomStockSearchAndHistoryNavigation() {
        HomePage homePage = new HomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Task: Random data (8 pts) - Randomly select a stock ticker from a predefined list and search for it
        String[] tickers = {"NVDA", "MSFT", "AMD", "MU", "CBRS"};
        String selectedTicker = tickers[new Random().nextInt(tickers.length)];
        
        System.out.println("Randomly selected stock: " + selectedTicker);

        // Search and Hover
        homePage.searchAndHoverStock(selectedTicker);

        // Wait for the new page to load (URL contains the ticker in lowercase)
        wait.until(ExpectedConditions.urlContains(selectedTicker.toLowerCase()));
        Assert.assertTrue(driver.getCurrentUrl().contains(selectedTicker.toLowerCase()), 
                "Not the correct stock page loaded!");

        // History test
        // Back to the main page
        driver.navigate().back();
        wait.until(ExpectedConditions.urlContains("#"));
        
        // Forward again to the stock page
        driver.navigate().forward();
        wait.until(ExpectedConditions.urlContains(selectedTicker.toLowerCase()));
    }
     
    @Test(dependsOnMethods = "testRandomStockSearchAndHistoryNavigation")
    public void testLogout() {
        HomePage homePage = new HomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        homePage.logout();

        By loginButtonLoc = By.xpath("//a[contains(text(), 'Log In')]");
        boolean isLoginVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(loginButtonLoc))
                .isDisplayed();

        Assert.assertTrue(isLoginVisible, "A Log In gomb nem jelent meg újra!");
    }
}