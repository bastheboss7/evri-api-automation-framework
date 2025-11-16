Feature: Enterprise ParcelShop API validation

  @smoke @count
  Scenario: Validate response returns only the requested count of parcelshops
    Given I have the enterprise-parcelshop-api
    When I submit a request with query parameter 'count' as '3'
    Then I receive a response with only '3' parcelshops

  @smoke @postcode
  Scenario: Validate response returns parcelshops for Edinburgh postcode
    Given I have the enterprise-parcelshop-api
    When I submit a request with query parameter 'city' as 'Edinburgh'
    Then I receive a response with parcelshops whose address.postCode field starts with 'EH'

  @regression @postcode @count
  Scenario: Validate response returns limited parcelshops for Edinburgh with count
    Given I have the enterprise-parcelshop-api
    When I submit a request with query parameter 'city' as 'Edinburgh'
    And query parameter 'count' as '5'
    Then I receive a response with parcelshops whose address.postCode field starts with 'EH'
    And the size of the response array is '5'
