package com.matschie.parcelshop.step.defs;

import static com.matschie.general.utils.PropertiesHandlers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.matschie.api.design.ResponseAPI;
import com.matschie.parcelshop.services.ParcelShopService;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;

/**
 * Step definitions for ParcelShop API BDD scenarios.
 * Implements lifecycle hooks to ensure clean state between scenarios.
 * Thread-safe for parallel execution using fresh instances per scenario.
 */
public class ParcelShopSteps {

	// Constants for query parameters
	private static final String PARAM_CITY = "city";
	private static final String PARAM_COUNT = "count";
	private static final String PARAM_POSTCODE = "postcode";
	
	// Default values for API calls
	private static final String DEFAULT_CITY = "Edinburgh";
	private static final int HTTP_OK = 200;
	private static final int HTTP_NO_CONTENT = 204;

	private RequestSpecBuilder requestBuilder;
	private ParcelShopService parcelShopService;
	private ResponseAPI response;
	
	/**
	 * Initialize resources before each scenario.
	 * Creates fresh instances to ensure thread safety in parallel execution.
	 */
	@Before
	public void setUp() {
		requestBuilder = new RequestSpecBuilder();
		parcelShopService = new ParcelShopService();
	}
	
	/**
	 * Clean up resources after each scenario.
	 * Ensures proper cleanup for parallel execution.
	 */
	@After
	public void tearDown() {
		// Clean up resources
		requestBuilder = null;
		parcelShopService = null;
		response = null;
	}

	/**
	 * Initialize API client with base configuration.
	 * Sets up base URI, path, headers, and authentication.
	 */
	@Given("I have the enterprise-parcelshop-api")
	public void i_have_the_enterprise_parcelshop_api() {
		if (requestBuilder == null) {
			throw new IllegalStateException("RequestBuilder is null - @Before hook may not have executed");
		}
		requestBuilder.setBaseUri(config("parcelshop.base.uri"));
		requestBuilder.setBasePath(config("parcelshop.base.path"));
		requestBuilder.addHeader("Content-Type", "application/json");
		requestBuilder.addHeader("apikey", config("parcelshop.api.key"));
	}

	/**
	 * Submit API request with specified query parameter.
	 * Automatically adds required location parameters for API calls.
	 * @param paramKey parameter name
	 * @param paramValue parameter value
	 */
	@When("I submit a request with query parameter {string} as {string}")
	public void i_submit_a_request_with_query_parameter_as(String paramKey, String paramValue) {
		if (PARAM_CITY.equals(paramKey)) {
			requestBuilder.addQueryParam(PARAM_POSTCODE, paramValue);
			requestBuilder.addQueryParam(PARAM_CITY, paramValue);
		} else if (PARAM_COUNT.equals(paramKey)) {
			requestBuilder.addQueryParam(PARAM_POSTCODE, DEFAULT_CITY);
			requestBuilder.addQueryParam(PARAM_CITY, DEFAULT_CITY);
			requestBuilder.addQueryParam(paramKey, paramValue);
		} else {
			requestBuilder.addQueryParam(paramKey, paramValue);
		}
		response = parcelShopService.getParcelShops(requestBuilder);
	}

	/**
	 * Add additional query parameter to existing request.
	 * Used for chaining multiple parameters in BDD scenarios.
	 * @param paramKey parameter name
	 * @param paramValue parameter value
	 */
	@When("query parameter {string} as {string}")
	public void query_parameter_as(String paramKey, String paramValue) {
		requestBuilder.addQueryParam(paramKey, paramValue);
		response = parcelShopService.getParcelShops(requestBuilder);
	}

	/**
	 * Validate response contains exact number of parcelshops.
	 * Handles 204 No Content response when no results found.
	 * @param expectedCount expected number of parcelshops as string
	 */
	@Then("I receive a response with only {string} parcelshops")
	public void i_receive_a_response_with_only_parcelshops(String expectedCount) {
		final int statusCode = response.getStatusCode();
		
		// Handle 204 No Content when no results found
		if (statusCode == HTTP_NO_CONTENT) {
			final int expected = Integer.parseInt(expectedCount);
			assertThat("Expected " + expected + " parcelshops but received 204 No Content (0 results)", 
				0, equalTo(expected));
			return;
		}
		
		parcelShopService.validateResponse(response, HTTP_OK, "", "application/json");
		final int actualCount = parcelShopService.getParcelShopCount(response);
		final int expected = Integer.parseInt(expectedCount);
		assertThat(actualCount, equalTo(expected));
	}

	/**
	 * Validate that all parcelshops have postcodes starting with given prefix.
	 * @param postCodePrefix expected postcode prefix (e.g., 'EH', 'SW1')
	 */
	@Then("I receive a response with parcelshops whose address.postCode field starts with {string}")
	public void i_receive_a_response_with_parcelshops_whose_address_post_code_field_starts_with(String postCodePrefix) {
		parcelShopService.validateResponse(response, HTTP_OK, "", "application/json");
		parcelShopService.validateAllPostCodesStartWith(response, postCodePrefix);
	}

	/**
	 * Validate response array contains exact number of elements.
	 * @param expectedSize expected array size as string
	 */
	@Then("the size of the response array is {string}")
	public void the_size_of_the_response_array_is(String expectedSize) {
		final int actualCount = parcelShopService.getParcelShopCount(response);
		final int expected = Integer.parseInt(expectedSize);
		assertThat(
			"Response array size mismatch", 
			actualCount, 
			equalTo(expected)
		);
	}

}
