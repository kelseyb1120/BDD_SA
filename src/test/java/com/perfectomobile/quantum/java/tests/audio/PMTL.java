package com.perfectomobile.quantum.java.tests.audio;

import com.perfectomobile.quantum.runners.JavaTestsRunner;
import com.perfectomobile.quantum.steps.ApplicationUtils;
import com.perfectomobile.quantum.steps.BLEUtils;
import com.qmetry.qaf.automation.util.StringMatcher;

import bsh.util.Util;

import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.Test;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

import java.io.IOException;

public class PMTL extends JavaTestsRunner {



	@Test(groups = "run")
	public void ebay() {
		try {
			ApplicationUtils.closeApp(getDriver(), "eBay", "name");

		} catch (Exception e) {
			//Do nothing 
		}
		
		BLEUtils.PMTLStart(getDriver());
 		ApplicationUtils.startApp(getDriver(), "eBay", "name");
		ApplicationUtils.switchToContext(getDriver(), "NATIVE");

		try {
			getDriver().findElement("home").click();
		} catch (Exception e) {
			// TODO: handle exception
		}
 		getDriver().findElement("signin").click();
 		 
 		 
 		
		//getDriver().findElement("user").sendKeys("perfemobil");
		getDriver().findElement("password").sendKeys("PM256!");
		getDriver().findElement("signINBT").click();
		

  		getDriver().findElement("gotoSearchBT").click();
 		getDriver().findElement("searcgFeild").sendKeys("bike"); 
 		getDriver().findElement("searcgBT").click();
 	
 		ApplicationUtils.clickOnVisual(getDriver(),"Mountain Bike");

 		getDriver().findElement("rating").getText();
  		getDriver().findElement("back").click();
	
 		// log out 
		getDriver().findElement("myebay").click();
		getDriver().findElement("setting").click();
		getDriver().findElement("signout").click();
	 
		BLEUtils.PMTLStop(getDriver());
		getDriver().close();
		try {
			BLEUtils.downloadAttachment(getDriver(), "/Users/uzie/Documents/har/eBay", "zip");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
 	}

	private void Sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




} 