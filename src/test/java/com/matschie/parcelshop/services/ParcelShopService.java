package com.matschie.parcelshop.services;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.matschie.api.design.ResponseAPI;
import com.matschie.api.rest.assured.api.client.RestAssuredApiClientImpl;

import io.restassured.builder.RequestSpecBuilder;

/**
 * Service layer for ParcelShop API operations.
 * Encapsulates API calls and provides validation methods.
 * Stateless design - returns response instead of storing it.
 */
public class ParcelShopService {
	
	// Error messages
	private static final String ERROR_NULL_REQUEST = "RequestSpecBuilder cannot be null";
	private static final String ERROR_NULL_RESPONSE = "Response is null";
	private static final String ERROR_NULL_PREFIX = "Postcode prefix cannot be null or empty";
	private static final String ERROR_EMPTY_RESPONSE = "Response body is empty - expected at least one parcelshop";
	
	// JSON field names
	private static final String FIELD_PARCEL_SHOPS = "parcelShops";
	private static final String FIELD_DATA = "data";
	private static final String FIELD_RESULTS = "results";
	private static final String FIELD_ADDRESS = "address";
	private static final String FIELD_POST_CODE = "postCode";
	
	private final RestAssuredApiClientImpl apiClient = new RestAssuredApiClientImpl();
	
	/**
	 * Execute GET request to retrieve parcelshops.
	 * @param requestBuilder configured request specification
	 * @return ResponseAPI object containing the API response
	 * @throws IllegalArgumentException if requestBuilder is null
	 */
	public ResponseAPI getParcelShops(final RequestSpecBuilder requestBuilder) {
		if (requestBuilder == null) {
			throw new IllegalArgumentException(ERROR_NULL_REQUEST);
		}
		return apiClient.get(requestBuilder, "");
	}
	
	/**
	 * Validate HTTP response status code, message, and content type.
	 * @param response the API response to validate
	 * @param statusCode expected HTTP status code
	 * @param statusLine expected status message (empty string to skip validation)
	 * @param contentType expected content type substring
	 * @throws IllegalArgumentException if response is null
	 */
	public void validateResponse(final ResponseAPI response, final int statusCode, final String statusLine, final String contentType) {
		if (response == null) {
			throw new IllegalArgumentException(ERROR_NULL_RESPONSE);
		}
		
		assertThat(
			"HTTP status code mismatch", 
			response.getStatusCode(), 
			equalTo(statusCode)
		);
		
		// Status message can be empty for 200 responses
		if (statusLine != null && !statusLine.isEmpty() && 
			response.getStatusMessage() != null && !response.getStatusMessage().isEmpty()) {
			assertThat(
				"HTTP status message mismatch", 
				response.getStatusMessage(), 
				equalTo(statusLine)
			);
		}
		
		if (contentType != null && !contentType.isEmpty()) {
			assertThat(
				"Content-Type mismatch", 
				response.getContentType(), 
				containsString(contentType)
			);
		}
	}
	
	/**
	 * Extract and return the count of parcelshops in the response.
	 * @param response the API response to extract count from
	 * @return number of parcelshops in response
	 * @throws IllegalArgumentException if response is null
	 */
	public int getParcelShopCount(final ResponseAPI response) {
		if (response == null) {
			throw new IllegalArgumentException(ERROR_NULL_RESPONSE);
		}
		
		final String responseBody = response.getBody();
		
		// Handle empty response
		if (responseBody == null || responseBody.trim().isEmpty()) {
			return 0;
		}
		
		// API returns a JSON array directly
		if (responseBody.trim().startsWith("[")) {
			final JSONArray jsonArray = new JSONArray(responseBody);
			return jsonArray.length();
		}
		
		// Fallback: Handle if response is a JSON object with array inside
		final JSONObject jsonObject = new JSONObject(responseBody);
		if (jsonObject.has(FIELD_PARCEL_SHOPS)) {
			return jsonObject.getJSONArray(FIELD_PARCEL_SHOPS).length();
		} else if (jsonObject.has(FIELD_DATA)) {
			return jsonObject.getJSONArray(FIELD_DATA).length();
		} else if (jsonObject.has(FIELD_RESULTS)) {
			return jsonObject.getJSONArray(FIELD_RESULTS).length();
		}
		
		return 0;
	}
	
	/**
	 * Validate that ALL parcelshops have postcodes starting with the given prefix.
	 * @param response the API response to validate
	 * @param prefix expected postcode prefix (e.g., "SW1", "EH")
	 * @throws IllegalArgumentException if response is null or prefix is null or empty
	 * @throws AssertionError if any postcode doesn't match the prefix
	 */
	public void validateAllPostCodesStartWith(final ResponseAPI response, final String prefix) {
		if (response == null) {
			throw new IllegalArgumentException(ERROR_NULL_RESPONSE);
		}
		
		if (prefix == null || prefix.trim().isEmpty()) {
			throw new IllegalArgumentException(ERROR_NULL_PREFIX);
		}
		
		final String responseBody = response.getBody();
		
		// Handle empty response
		if (responseBody == null || responseBody.trim().isEmpty()) {
			throw new AssertionError(ERROR_EMPTY_RESPONSE);
		}
		
		final JSONArray parcelShops = getParcelShopsArray(responseBody);
		
		assertThat(
			"Response should contain at least one parcelshop", 
			parcelShops.length(), 
			greaterThan(0)
		);
		
		// Validate that ALL postcodes start with the prefix - fail fast on first mismatch
		for (int i = 0; i < parcelShops.length(); i++) {
			final JSONObject parcelShop = parcelShops.getJSONObject(i);
			final String postCode = parcelShop.getJSONObject(FIELD_ADDRESS).getString(FIELD_POST_CODE);
			if (!postCode.startsWith(prefix)) {
				throw new AssertionError(
					"Postcode at index " + i + " ('" + postCode + "') does not start with '" + prefix + "'"
				);
			}
		}
	}
	
	/**
	 * Extract parcelshops array from response body.
	 * Handles both direct JSON array and object-wrapped array formats.
	 * @param responseBody raw JSON response
	 * @return JSONArray of parcelshops (empty array if not found)
	 */
	private JSONArray getParcelShopsArray(final String responseBody) {
		// Handle if response is a JSON array
		if (responseBody.trim().startsWith("[")) {
			return new JSONArray(responseBody);
		}
		
		// Handle if response is a JSON object with array inside
		final JSONObject jsonObject = new JSONObject(responseBody);
		if (jsonObject.has(FIELD_PARCEL_SHOPS)) {
			return jsonObject.getJSONArray(FIELD_PARCEL_SHOPS);
		} else if (jsonObject.has(FIELD_DATA)) {
			return jsonObject.getJSONArray(FIELD_DATA);
		} else if (jsonObject.has(FIELD_RESULTS)) {
			return jsonObject.getJSONArray(FIELD_RESULTS);
		}
		
		return new JSONArray();
	}

}
