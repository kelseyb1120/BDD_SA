package com.perfectomobile.quantum.hooks;

import com.perfecto.reportium.client.ReportiumClient;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import static com.perfectomobile.quantum.listeners.QuantumReportiumListener.PERFECTO_REPORT_CLIENT;
import static com.perfectomobile.quantum.utils.ConfigurationUtils.getBaseBundle;

public class CucumberHooks {

    public static final String CURRENT_FEATURE = "current.feature";

    private void reportTestStep(String description) {
        System.out.println(description);
        // add step to the report
        Object reportiumClient = getBaseBundle().getObject(PERFECTO_REPORT_CLIENT);
        if (reportiumClient != null && reportiumClient instanceof ReportiumClient)
            ((ReportiumClient)reportiumClient).testStep(description);
    }

    @Before
    public void setup(Scenario scenario) {
        reportTestStep("Starting Scenario: " + scenario.getName());
    }

    /*@BeforeStep
    public void beforeStep(Step step) {
        reportTestStep("Starting Step: " + step.getName());
    }

    @AfterStep
    public void afterStep(Step step) {
        reportTestStep("Finished Step: " + step.getName());
    }*/

    @After
    public void after(Scenario scenario) {
        reportTestStep("Finished Scenario: " + scenario.getName());
    }
}
