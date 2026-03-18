Feature: Basic Verification
  As a citizen of Viman Nagar
  I want to verify that an issue has been resolved
  So that the community can close issues transparently

  Background:
    Given an authenticated user exists with ID 20
    And an issue exists with ID 200 and status "reported"

  Scenario: Submitting a valid verification photo
    Given the user is on the issue 200 detail page
    And the user uploads an "after" photo showing the fix
    And the user provides an optional note "Pothole filled by PMC today"
    When the frontend sends a POST request to "/api/issues/200/verify"
    Then the system should save the verification record linked to user 20 and issue 200
    And return a 201 Created status
    And if the issue reaches 2 verifications, its status should change to "resolved"

  Scenario: Attempting to verify without a photo
    Given the user is on the issue 200 detail page
    When the frontend sends a POST request to "/api/issues/200/verify" without a photo URL
    Then the system should reject the submission
    And return a 400 Bad Request status

  Scenario: Attempting to verify an already resolved issue
    Given an issue exists with ID 201 and status "resolved"
    When the frontend sends a POST request to "/api/issues/201/verify"
    Then the system may accept additional verification documentation
    And return the appropriate status code
