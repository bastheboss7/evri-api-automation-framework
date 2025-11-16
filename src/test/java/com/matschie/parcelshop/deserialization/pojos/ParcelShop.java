package com.matschie.parcelshop.deserialization.pojos;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * POJO for ParcelShop API response deserialization.
 * Fields are used by Gson for JSON to Java object mapping.
 */
@SuppressWarnings("unused") // Fields populated by Gson deserialization
public class ParcelShop {

	@SerializedName("parcelShopId")
	private String parcelShopId;
	
	private String description;
	private Double latitude;
	private Double longitude;
	private Double distance;
	private String mapUrl;
	private String photoUrl;
	private String phoneNumber;
	private Address address;
	private List<String> facilities;
	private List<String> services;
	private List<BusinessHour> businessHours;
	private String nextDay;
	private String locationType;
	private String nodeType;
	
	public String getParcelShopId() {
		return parcelShopId;
	}
	
	public void setParcelShopId(String parcelShopId) {
		this.parcelShopId = parcelShopId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public static class Address {
		private String line1;
		private String line2;
		
		@SerializedName("postCode")
		private String postCode;
		
		private String countryCode;
		
		public String getLine1() {
			return line1;
		}
		
		public void setLine1(String line1) {
			this.line1 = line1;
		}
		
		public String getLine2() {
			return line2;
		}
		
		public void setLine2(String line2) {
			this.line2 = line2;
		}
		
		public String getPostCode() {
			return postCode;
		}
		
		public void setPostCode(String postCode) {
			this.postCode = postCode;
		}
		
		public String getCountryCode() {
			return countryCode;
		}
		
		public void setCountryCode(String countryCode) {
			this.countryCode = countryCode;
		}
	}
	
	public static class BusinessHour {
		private String dayOfWeek;
		private String openTime;
		private String closeTime;
		
		public String getDayOfWeek() {
			return dayOfWeek;
		}
		
		public void setDayOfWeek(String dayOfWeek) {
			this.dayOfWeek = dayOfWeek;
		}
		
		public String getOpenTime() {
			return openTime;
		}
		
		public void setOpenTime(String openTime) {
			this.openTime = openTime;
		}
		
		public String getCloseTime() {
			return closeTime;
		}
		
		public void setCloseTime(String closeTime) {
			this.closeTime = closeTime;
		}
	}

}
