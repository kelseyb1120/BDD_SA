package com.perfectomobile.quantum.steps;

import cucumber.api.java.en.Then;

import static com.qmetry.qaf.automation.step.CommonStep.assertEnabled;
import static com.qmetry.qaf.automation.step.CommonStep.click;
import static com.qmetry.qaf.automation.step.CommonStep.sendKeys;

/**
 * Created by shanil on 9/24/2016.
 */
public class MyCommunityTestSteps {

    @Then("^I fill user \"(.*?)\" and password \"(.*?)\"$")
    public void IFill(String user, String password) {
        assertEnabled("login.user");
        sendKeys(user, "login.user");
        sendKeys(password, "login.password");

    }

    @Then("^I go to login page$")
    public void goToLogin() {
        click("button.menu");
        click("button.login");
    }
}
