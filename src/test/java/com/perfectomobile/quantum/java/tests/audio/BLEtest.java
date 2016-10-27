package com.perfectomobile.quantum.java.tests.audio;

import com.perfectomobile.quantum.runners.JavaTestsRunner;
import com.perfectomobile.quantum.steps.ApplicationUtils;
import com.perfectomobile.quantum.steps.BLEUtils;
import com.qmetry.qaf.automation.util.StringMatcher;

import bsh.util.Util;

import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.Test;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

public class BLEtest extends JavaTestsRunner {

	@Test(groups = "dont")
	public void data() {
		String tags = getBundle().getString("Perfecto.execute.tag");
	}

	@Test(groups = "run")
	public void check10() {
		try {
	 		ApplicationUtils.closeApp(getDriver(), "mBIoT", "name");

		} catch (Exception e) {
			//Do nothing 
		}
		ApplicationUtils.startApp(getDriver(), "mBIoT", "name");

		ApplicationUtils.switchToContext(getDriver(), "NATIVE");
		getDriver().findElement("BLE.Health").click();
		getDriver().findElement("BLE.HB").click();

		
		// go to scan list (should be empty)
		getDriver().findElement("BLE.scan").click();
		Sleep(2000);
		
		// start new BLE , should be added to the scan list 
		String BLEDEv  = BLEUtils.BLEStartChar("2A37", "120",getDriver());
		System.out.println("******" + BLEDEv);

		
		getDriver().findElement("BLE.device").click();

		ApplicationUtils.assertVisualText(getDriver(), "120");

		BLEUtils.BLESetValue ("2A37", BLEDEv,"210",getDriver());
		ApplicationUtils.assertVisualText(getDriver(), "210");

		// disconnect 
		getDriver().findElement("BLE.disconnect").click();
		getDriver().findElement("BLE.back").click();
	
		BLEDEv  = BLEUtils.BLEStartChar("2A37", "120",getDriver());

		// after back should reconnect again but first set the value to 199
		BLEUtils.BLESetValue ("2A37", BLEDEv,"199",getDriver());
		getDriver().findElement("BLE.HB").click();

		getDriver().findElement("BLE.scan").click();
		getDriver().findElement("BLE.device").click();

		ApplicationUtils.assertVisualText(getDriver(), "199");
		// stop BLE service
		Sleep(2000);

		BLEUtils.BLEstop( BLEDEv, getDriver());



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