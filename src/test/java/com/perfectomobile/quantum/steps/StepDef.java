package com.perfectomobile.quantum.steps;

import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

import static com.qmetry.qaf.automation.step.CommonStep.assertEnabled;
import static com.qmetry.qaf.automation.step.CommonStep.sendKeys;


/**
 * @author ilanm
 * If you need to extend your Gherkin language, Here you can define your cucumber glue code.
 *
 */

public class StepDef {
    private static QAFExtendedWebDriver getDriver() {
        return new WebDriverTestBase().getDriver();
    }

    @Then("^I login with user \"(.*?)\" and password \"(.*?)\"$")
    public void I_login(String user, String password) throws Throwable {
        assertEnabled("input.user");
        sendKeys(user, "input.user");
        sendKeys(password, "input.password");
        getDriver().findElement("button.submit").click();
    }

    @Then("^I validate an Error$")
    public void validat_error() throws Throwable {
        String context = ApplicationUtils.getCurrentContext(getDriver());
        ApplicationUtils.switchToContext(getDriver(), "VISUAL");
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getDriver().findElement(By.linkText("unsuccessful"));
        ApplicationUtils.switchToContext(getDriver(), context);
    }

    @QAFTestStep(description = "close app")
    @Then("^I close app$")
    public void closeapp() throws Throwable {
        //getDriver().close();
    }

    @When("^I click on button with text \"(.*?)\"$")
    public void click_visual_text(String text) {
        String context = ApplicationUtils.getCurrentContext(getDriver());
        ApplicationUtils.switchToContext(getDriver(), "VISUAL");
        getDriver().findElementByLinkText(text).click();
        ApplicationUtils.switchToContext(getDriver(), context);
    }

}
