Feature: Issue Submission
  As a verified citizen of Viman Nagar
  I want to submit a public issue with a photo and location
  So that the community and authorities can track and fix it

  Background:
    Given an authenticated user exists

  Scenario: Successfully reporting a new issue
    Given the user is on the submit issue screen
    And the user provides the title "Huge pothole on 1st Main"
    And the user selects the category "pothole"
    And the user provides a description "Near X cafe, damaging vehicles"
    And the user uploads 1 valid photo of the issue
    And the user provides valid GPS coordinates (latitude, longitude)
    When the frontend sends a POST request to "/api/issues" with the issue details
    Then the system should save the issue continuously with status "reported"
    And return a 201 Created status
    And the response should contain the newly created issue ID

  Scenario: Failing to report an issue with missing required fields
    Given the user is on the submit issue screen
    When the frontend sends a POST request to "/api/issues" missing the title or category
    Then the system should reject the submission
    And return a 400 Bad Request status
    And the response should contain a validation error message

  Scenario: Failing to report an issue unauthenticated
    Given an unauthenticated user attempts to submit an issue
    When the frontend sends a POST request to "/api/issues"
    Then the system should block the request
    And return a 401 Unauthorized status
