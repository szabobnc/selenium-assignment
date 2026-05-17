package com.stockanalysis.tests;

import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CookieTests extends BaseTest {
    @Test
    public void shouldSuccessfullyAddReadAndDeleteCustomCookie() {
        // Add a custom cookie to the browser, read it back to verify it was set
        // correctly, and then delete it
        Cookie myCustomCookie = new Cookie("Automation_Test_Cookie", "Grade_5_Student");
        driver.manage().addCookie(myCustomCookie);

        // Read the cookie back and verify its value
        Cookie retrievedCookie = driver.manage().getCookieNamed("Automation_Test_Cookie");
        Assert.assertNotNull(retrievedCookie, "Cookie was not found!");
        Assert.assertEquals(retrievedCookie.getValue(), "Grade_5_Student", "Cookie value does not match!");

        // Delete the cookie and verify it has been removed
        driver.manage().deleteCookieNamed("Automation_Test_Cookie");

        // Check that the cookie has been deleted
        Cookie deletedCookie = driver.manage().getCookieNamed("Automation_Test_Cookie");
        Assert.assertNull(deletedCookie, "Cookie was not deleted!");
        System.out.println("Cookie was successfully added, verified, and deleted.");
    }
}