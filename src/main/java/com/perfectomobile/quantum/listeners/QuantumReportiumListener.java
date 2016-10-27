package com.perfectomobile.quantum.listeners;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.testng.ReportiumTestNgListener;
import com.qmetry.qaf.automation.core.TestBaseProvider;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.ITestResult;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by mitchellw on 9/27/2016.
 */
public class QuantumReportiumListener extends ReportiumTestNgListener {

    public static final String PERFECTO_REPORT_CLIENT = "perfecto.report.client";

    @Override
    protected String getTestName(ITestResult result) {
        return result.getName();
    }

    @Override
    protected ReportiumClient createReportiumClient(ITestResult testResult) {
        ReportiumClient reportiumClient = new ReportiumClientFactory().createLoggerClient();
        if (!getCucumberOptions(testResult).isDryRun())
            reportiumClient = super.createReportiumClient(testResult);

        setReporter(reportiumClient);
        return reportiumClient;
    }

    @Override
    protected String[] getTags(ITestResult testResult) {

        RuntimeOptions cucumberOptions = getCucumberOptions(testResult);
        List<String> optionsList =  cucumberOptions.getFilters().stream().map(object -> Objects.toString(object, null)).collect(Collectors.toList());
        optionsList.addAll(cucumberOptions.getFeaturePaths());
        optionsList.addAll(cucumberOptions.getGlue());

        return ArrayUtils.addAll(super.getTags(testResult), optionsList.toArray(new String[optionsList.size()]));
    }

    private void setReporter(ReportiumClient reporter) {
        TestBaseProvider.instance().get().getContext().setProperty(PERFECTO_REPORT_CLIENT, reporter);
    }

    private RuntimeOptions getCucumberOptions(ITestResult testResult) {
        try {
            return new RuntimeOptionsFactory(Class.forName(testResult.getTestClass().getName())).create();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
