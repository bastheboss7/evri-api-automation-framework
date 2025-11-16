package com.matschie.parcelshop.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReports manager for generating HTML test reports.
 * Provides centralized configuration and test instance management.
 */
public class ExtentReportManager {
	
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static String reportPath;
	
	/**
	 * Initialize ExtentReports with configuration.
	 * @return configured ExtentReports instance
	 */
	public static ExtentReports createInstance() {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		reportPath = "target/extent-reports/ExtentReport_" + timestamp + ".html";
		
		// Create reports directory if not exists
		new File("target/extent-reports").mkdirs();
		
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
		
		// Configure report appearance
		sparkReporter.config().setDocumentTitle("ParcelShop API Test Report");
		sparkReporter.config().setReportName("ParcelShop API Automation Results");
		sparkReporter.config().setTheme(Theme.STANDARD);
		sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
		sparkReporter.config().setEncoding("UTF-8");
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		// Add system information
		extent.setSystemInfo("Application", "Hermes ParcelShop API");
		extent.setSystemInfo("API Base URL", "https://api.hermesworld.co.uk/enterprise-parcelshop-api/v1");
		extent.setSystemInfo("Environment", "Test");
		extent.setSystemInfo("Framework", "REST Assured + TestNG + Cucumber");
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("OS", System.getProperty("os.name") + " " + System.getProperty("os.version"));
		extent.setSystemInfo("User", System.getProperty("user.name"));
		extent.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
		
		return extent;
	}
	
	/**
	 * Get the ExtentReports instance, creating if necessary.
	 * @return ExtentReports instance
	 */
	public static ExtentReports getExtent() {
		if (extent == null) {
			createInstance();
		}
		return extent;
	}
	
	/**
	 * Create a new test in the report.
	 * @param testName test name
	 * @param description test description
	 */
	public static void createTest(String testName, String description) {
		ExtentTest extentTest = getExtent().createTest(testName, description);
		test.set(extentTest);
	}
	
	/**
	 * Get the current test instance.
	 * @return current ExtentTest
	 */
	public static ExtentTest getTest() {
		return test.get();
	}
	
	/**
	 * Flush and finalize the report.
	 * Automatically opens the report in default browser.
	 */
	public static void flush() {
		if (extent != null) {
			extent.flush();
			openReport();
		}
	}
	
	/**
	 * Automatically open the generated ExtentReport in the default browser.
	 */
	private static void openReport() {
		try {
			if (reportPath != null) {
				File reportFile = new File(reportPath);
				if (reportFile.exists() && Desktop.isDesktopSupported()) {
					Desktop.getDesktop().browse(reportFile.toURI());
					System.out.println("\n✅ ExtentReport opened automatically: " + reportPath);
				}
			}
		} catch (Exception e) {
			System.err.println("⚠️  Could not open ExtentReport automatically: " + e.getMessage());
			System.out.println("📁 Report location: " + reportPath);
		}
	}

}
