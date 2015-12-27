package instaevent.abhinandankothari.com.instaevent;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class FunctionalTest {

    String userName = "username_or_email";
    String password = "password";
    String signIn = "instaevent.abhinandankothari.com.instaevent:id/facebook_login";
    String fab = "instaevent.abhinandankothari.com.instaevent:id/fab";

    public AppiumDriver<MobileElement> driver;
    public WebDriverWait wait;

    @Before
    public void setUp() throws Exception {
        // set up appium for android instance
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Moto G");
        capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, "instaevent.abhinandankothari.com.instaevent");
        capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, ".views.DispatchLoginActivity");
        capabilities.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/build/InstaEvent.apk");
        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, 30);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void logIn() throws InterruptedException {
        // Login to the Wordpress Application
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(signIn)));
        driver.findElementById(signIn).click();

        //Wait for the new post button
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(fab)));
        assertEquals(driver.findElementById("instaevent.abhinandankothari.com.instaevent:id/txt_name").getText(),"Abhinandan Kothari");

    }
}