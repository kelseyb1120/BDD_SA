package com.perfectomobile.quantum.java.tests;

import com.perfectomobile.quantum.runners.JavaTestsRunner;
import com.perfectomobile.quantum.steps.ApplicationUtils;
import com.perfectomobile.quantum.steps.DeviceUtils;
import com.qmetry.qaf.automation.util.StringMatcher;
import org.testng.annotations.Test;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

public class MyJavaTest extends JavaTestsRunner {

	@Test(groups = "run")
	public void data() {
		String tags = getBundle().getString("Perfecto.execute.tag");
		System.out.println("******" + tags);
	}

	@Test
	public void check10() {
		//CommonStep.startTransaction("open app", 20);
		ApplicationUtils.startApp(getDriver(), "calR", "name");
		//CommonStep.stopTransaction();
		ApplicationUtils.switchToContext(getDriver(), "NATIVE");
		getDriver().findElement("percentage").sendKeys("100");
		getDriver().findElement("num").sendKeys("10");
		DeviceUtils.hideKeyboard(getDriver());
		getDriver().findElement("button.submit").click();
		;
		getDriver().findElement("val").assertText(StringMatcher.exactIgnoringCase("10.0"));
	}

	@Test
	public void checkZero() {
		ApplicationUtils.startApp(getDriver(), "calR", "name");
		ApplicationUtils.switchToContext(getDriver(), "NATIVE");
		getDriver().findElement("percentage").sendKeys("0");
		getDriver().findElement("num").sendKeys("10");
		DeviceUtils.hideKeyboard(getDriver());

		getDriver().findElement("button.submit").click();
		getDriver().findElement("val").assertText(StringMatcher.exactIgnoringCase("0.0"));
	}

} 