package com.matschie.parcelshop.reporting;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

/**
 * Cucumber plugin to integrate ExtentReports with Cucumber execution.
 * Listens to Cucumber events and logs them to ExtentReports.
 */
public class ExtentCucumberAdapter implements ConcurrentEventListener {

	private static ExtentTest featureTest;
	private ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();
	private static String currentFeatureName = "";
	
	public ExtentCucumberAdapter() {
		ExtentReportManager.createInstance();
	}

	@Override
	public void setEventPublisher(EventPublisher publisher) {
		publisher.registerHandlerFor(TestRunStarted.class, this::handleTestRunStarted);
		publisher.registerHandlerFor(TestRunFinished.class, this::handleTestRunFinished);
		publisher.registerHandlerFor(TestSourceRead.class, this::handleTestSourceRead);
		publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
		publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
		publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
		publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
	}

	private void handleTestRunStarted(TestRunStarted event) {
		// Test run started - report is already initialized
	}

	private void handleTestRunFinished(TestRunFinished event) {
		// Flush the report when all tests are finished
		ExtentReportManager.flush();
	}

	private void handleTestSourceRead(TestSourceRead event) {
		// Feature file read - can be used for additional info
	}

	private void handleTestCaseStarted(TestCaseStarted event) {
		TestCase testCase = event.getTestCase();
		String scenarioName = testCase.getName();
		String featureName = testCase.getUri().toString().substring(testCase.getUri().toString().lastIndexOf("/") + 1);
		
		// Create feature node only once for all scenarios in the same feature
		if (!featureName.equals(currentFeatureName)) {
			featureTest = ExtentReportManager.getExtent().createTest(featureName);
			currentFeatureName = featureName;
		}
		
		// Create scenario node under feature with tags
		ExtentTest scenario = featureTest.createNode(scenarioName);
		
		// Add tags to scenario
		testCase.getTags().forEach(tag -> scenario.assignCategory(tag));
		
		scenarioTest.set(scenario);
	}

	private void handleTestCaseFinished(TestCaseFinished event) {
		Result result = event.getResult();
		Status status = getStatus(result.getStatus());
		
		ExtentTest scenario = scenarioTest.get();
		if (scenario != null && result.getError() != null) {
			// Only log if there's an error, otherwise the status is already shown by step results
			scenario.log(status, result.getError());
		}
	}

	private void handleTestStepStarted(TestStepStarted event) {
		// Step started
	}

	private void handleTestStepFinished(TestStepFinished event) {
		TestStep testStep = event.getTestStep();
		Result result = event.getResult();
		
		if (testStep instanceof PickleStepTestStep) {
			PickleStepTestStep pickleStep = (PickleStepTestStep) testStep;
			String stepText = pickleStep.getStep().getText();
			Status status = getStatus(result.getStatus());
			
			ExtentTest scenario = scenarioTest.get();
			if (scenario != null) {
				if (status == Status.PASS) {
					scenario.log(status, stepText);
				} else if (status == Status.FAIL) {
					scenario.log(status, stepText);
					if (result.getError() != null) {
						scenario.fail(result.getError());
					}
				} else if (status == Status.SKIP) {
					scenario.log(status, stepText);
				}
			}
		}
	}

	private Status getStatus(io.cucumber.plugin.event.Status cucumberStatus) {
		switch (cucumberStatus) {
			case PASSED:
				return Status.PASS;
			case FAILED:
				return Status.FAIL;
			case SKIPPED:
			case PENDING:
				return Status.SKIP;
			case AMBIGUOUS:
			case UNDEFINED:
				return Status.WARNING;
			default:
				return Status.INFO;
		}
	}

}
