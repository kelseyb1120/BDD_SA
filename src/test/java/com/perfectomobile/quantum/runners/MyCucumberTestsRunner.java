package com.perfectomobile.quantum.runners;


import org.testng.annotations.Factory;

import cucumber.api.testng.CucumberFeatureWrapper;

/**
 * @author mitchellw
 * This is an example of a data driven Cucumber runner class.
 * If you wan to run data driven cucumber tests, you can specify this runner in your testng xml file and modify 
 * the test data under src/test/resources/data folder.
 * If you wish to modify the features folder, the glue code package or the executed tags,
 * you can use the features, glue, tags and plugin parameters in your testng xml files.
 * 
 *
 */
public class MyCucumberTestsRunner extends CucumberTestsRunner {

	@Factory(dataProvider = "featureFactory")
	public MyCucumberTestsRunner(CucumberFeatureWrapper cucumberFeatureWrapper){
		super(cucumberFeatureWrapper);
	}

}