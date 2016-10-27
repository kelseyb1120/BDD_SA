package com.perfectomobile.quantum.runners;

import static com.perfectomobile.quantum.utils.ConfigurationUtils.getBaseBundle;
import static com.perfectomobile.quantum.utils.ConfigurationUtils.setExecutionIdCapability;
import static com.perfectomobile.quantum.utils.ConfigurationUtils.setMavenCapabilities;
import gherkin.formatter.model.Step;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.perfectomobile.quantum.utils.AnnotationsUtils;
import com.qmetry.qaf.automation.testng.dataprovider.DataProviderFactory;
import com.qmetry.qaf.automation.util.StringUtil;

import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;


public class CucumberTestsRunner extends QuantumRunnerBase implements ITest {
    public static final String CUCUMBER_FEATURE = "cucumber.feature";

    private TestNGCucumberRunner testNGCucumberRunner = null;
    private CucumberFeatureWrapper cucumberFeatureWrapper;
    private HashMap<String,String> updatedSteps = new HashMap<>();

    @Override
    public String getTestName() {
        return cucumberFeatureWrapper.toString();
    }

    @Factory(dataProvider = "featureFactory")
    public CucumberTestsRunner(CucumberFeatureWrapper cucumberFeatureWrapper) {
        this.cucumberFeatureWrapper = cucumberFeatureWrapper;
    }

    @DataProvider(name = "featureFactory", parallel = true)
    public static Object[][] features(ITestContext testContext) throws ClassNotFoundException {
    	System.out.println("@FeaturesFactory");
    	Map<String, String> params = testContext.getCurrentXmlTest().getAllParameters();
    	String tags = getParamValue(params, "tags", "");
    	String glue = getParamValue(params, "glue", "");
    	String features = getParamValue(params, "features", "");
    	String plugin = getParamValue(params, "plugin", "");
        String dryRun = getParamValue(params, "dryRun", "");
       	

    	Class<?> targetClass = CucumberTestsRunner.class;
		AnnotationsUtils.setClassCucumberOptions(targetClass, tags, glue, features, plugin, "true".equals(dryRun));
        return new TestNGCucumberRunner(targetClass).provideFeatures();
    }

    private static String getParamValue(Map<String, String> params,
			String key, String defaultValue) {
		return params.containsKey(key)? params.get(key) : defaultValue;
	}

	@DataProvider(name = "dataDrivenProvider", parallel = false)
    public Object[][] dataDrivenProvider(Method m) {
        Map<String, String> param = null;
        try {
            param = StringUtil.toMap(StringUtil.parseCSV(
                        cucumberFeatureWrapper.getCucumberFeature().getGherkinFeature().getDescription()
                        , getBaseBundle().getListDelimiter())
                    , true);
        } catch (Exception e) {
            System.err.println("Error parsing feature: " + cucumberFeatureWrapper);
            e.printStackTrace();
        }

        if (param == null || param.isEmpty()) {
            Map<String,String> emptyMap = new HashMap<>();
            emptyMap.put("DATAFILE", "N/A");
            Object[][] noData = {{emptyMap}};
            return noData;
        }

        try {
            return DataProviderFactory.getData(param);
        } catch (Exception e) {
            System.err.println("Error extracting data with details:\n\t" + param);
            e.printStackTrace();
            throw e;
        }
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        setExecutionIdCapability();
        setMavenCapabilities();
    }

    @BeforeClass(alwaysRun = true)
    @Parameters({"tags", "glue", "features", "plugin", "dryRun"})
    public void beforeClass(ITestContext testContext,
                            @Optional("") String tags,
                            @Optional(AnnotationsUtils.DEFAULT_GLUE_PACKAGE) String glue,
                            @Optional(AnnotationsUtils.DEFAULT_FEATURES_FOLDER) String features,
                            @Optional(AnnotationsUtils.DEFAULT_PLUGINS) String plugin,
                            @Optional(AnnotationsUtils.DEFAULT_DRYRUN) boolean dryRun) throws ClassNotFoundException {
    	Class<?> targetClass = CucumberTestsRunner.class;
    	AnnotationsUtils.setClassCucumberOptions(targetClass, tags, glue, features, plugin, dryRun);
    	testNGCucumberRunner = new TestNGCucumberRunner(targetClass);
        getBaseBundle().addAll(testContext.getCurrentXmlTest().getAllParameters());
    }

    @Test(groups = "Data Driven Group", description = "Data Driven Runner Test Description", dataProvider = "dataDrivenProvider")
    public void feature(Map<String, String> testData) throws InterruptedException {
        testNGCucumberRunner.runCucumber(updateFeatureStepsWithData(testData));
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestContext testContext) throws InterruptedException {
        if ("true".equals(testContext.getCurrentXmlTest().getAllParameters().get("dryRun")))
            Thread.sleep(RandomUtils.nextLong(100, 1000));
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
        if (!StringUtils.isEmpty(getTestBase().getBrowser()))
            getDriver().close();
    }

    private CucumberFeature updateFeatureStepsWithData(Map<String, String> testData) {
        if (testData != null)
            cucumberFeatureWrapper.getCucumberFeature().getFeatureElements().forEach(tag -> tag.getSteps().replaceAll(step -> {
                String stepName = step.getName();
                if (updatedSteps.containsKey(stepName))
                    stepName = updatedSteps.get(stepName);
                for (String key : testData.keySet())
                    if (stepName.contains(dataKeyword(key)))
                        stepName = stepName.replace(dataKeyword(key), testData.get(key));
                if (!stepName.equals(step.getName()))
                    updatedSteps.put(stepName, step.getName());
                return new Step(step.getComments(), step.getKeyword(), stepName, step.getLine(), step.getRows(), step.getDocString());
            }));
        return cucumberFeatureWrapper.getCucumberFeature();
    }

    private String dataKeyword(String key) {
        return String.format("<%s>", key);
    }

}
