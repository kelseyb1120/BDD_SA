package com.perfectomobile.quantum.java.tests.audio;

import com.perfectomobile.quantum.runners.JavaTestsRunner;
import com.perfectomobile.quantum.steps.ApplicationUtils;
import com.perfectomobile.quantum.steps.DeviceUtils;
import com.qmetry.qaf.automation.util.StringMatcher;
import com.thoughtworks.selenium.webdriven.commands.Click;

import org.testng.annotations.Test;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

public class AudioTest extends JavaTestsRunner {

	@Test(groups = "dont")
	public void data() {
		String tags = getBundle().getString("Perfecto.execute.tag");
		System.out.println("******" + tags);
	}

	@Test(groups = "run")
	public void BLETest() {
		DeviceUtils.goToHomeScreen(getDriver());
		ApplicationUtils.startApp(getDriver(), "com.clearchannel.iheartradio", "identifier");
		ApplicationUtils.switchToContext(getDriver(), "NATIVE");
 
	}



} 