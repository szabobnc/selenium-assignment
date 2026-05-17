package com.stockanalysis.tests;

import com.stockanalysis.config.ConfigReader;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class NavigationTests extends BaseTest {
    // DataProvider acts as the array of URLs for our test
    @DataProvider(name = "stockTickers")
    public Object[][] getStockData() {
        return new Object[][] {
                { "aapl", "Apple" },
                { "msft", "Microsoft" },
                { "tsla", "Tesla" }
        };
    }

    @Test(dataProvider = "stockTickers")
    public void shouldLoadIndividualStockPages(String ticker, String expectedCompanyName) {
        // Iterate through the stock tickers, search for each one, click the result, and
        // verify the correct page loads
        driver.get(ConfigReader.getProperty("baseUrl") + "/stocks/" + ticker + "/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.titleContains(expectedCompanyName));

        String actualTitle = driver.getTitle();

        // Verifying something on each page
        Assert.assertTrue(actualTitle.contains(expectedCompanyName),
                "Page title did not contain the company name for " + ticker);
    }

    // Static page test
    @Test
    public void shouldLoadStaticPageSuccessfully() {
        driver.get(ConfigReader.getProperty("baseUrl"));

        Assert.assertTrue(driver.getTitle().contains("Stock Analysis"), "Static page title is incorrect.");

        boolean isHeadingPresent = driver.findElements(
                By.xpath("//main//h1[contains(text(), 'Search for a stock')]")).size() > 0;

        Assert.assertTrue(isHeadingPresent, "A 'Search for a stock' heading is not present on the static page.");

        boolean isTrendingNvdaPresent = driver.findElements(
                By.xpath("//div[contains(text(), 'Trending')]//a[contains(@href, '/stocks/nvda')]")).size() > 0;

        Assert.assertTrue(isTrendingNvdaPresent, "The 'Trending' section's NVDA link is not found on the static page!");
    }
}