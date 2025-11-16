package com.matschie.parcelshop.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		          features = {"src/test/java/com/matschie/parcelshop/features/ParcelShop.feature"},
		          glue = {"com.matschie.parcelshop.step.defs"},
		          dryRun = false,
		        //    tags = "@smoke and @postcode",
		          plugin = {
		        		  "pretty",
		        		  "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
		        		  "com.matschie.parcelshop.reporting.ExtentCucumberAdapter"
		          }
		        )
public class ParcelShopCucumberRunner extends AbstractTestNGCucumberTests {

}
