package com.perfectomobile.quantum.java.tests;

import com.perfectomobile.quantum.runners.JavaTestsRunner;
import com.perfectomobile.quantum.steps.ApplicationUtils;
import com.perfectomobile.quantum.steps.DeviceUtils;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;

import org.testng.annotations.Test;

/**
 * Created by shanil on 9/28/2016.
 */
public class CommunityExampleTest extends JavaTestsRunner {

    public static String communityURL = "https://community.perfectomobile.com/";
    private QAFExtendedWebDriver driver = getDriver();

    @Test
    public void communityLogin(){
        driver.get(communityURL);
        driver.findElement("button.menu").click();
        driver.findElement("button.login").click();
        driver.findElement("login.user").sendKeys("beton.yatsuk@gmail.com");
        driver.findElement("login.password").sendKeys("ABC123");
        driver.findElement("button.submit").click();
        driver.findElement("button.menu").click();
        ApplicationUtils.verifyVisualText(driver, "Hi, Beton!");
        DeviceUtils.takeScreenshot(driver, null, false);
    }

}
