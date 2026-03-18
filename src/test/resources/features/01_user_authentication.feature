Feature: User Authentication
  As a citizen of Viman Nagar
  I want to sign in using my Google account via Firebase
  So that I can report issues securely and transparently

  Background:
    Given the system is running and database is connected

  Scenario: Successful login for a new user
    Given a new user wants to log in using Google
    And the user has a valid Firebase Auth token with UID "fire123" and email "citizen@pune.kar"
    When the frontend sends the token to POST "/api/auth/verify"
    Then the system should verify the token successfully
    And create a new user record in the database
    And return a 200 OK status
    And the response should contain the user's profile information mapped to the database ID

  Scenario: Successful login for an existing user
    Given an existing user with UID "fire123" exists in the system
    And the user has a valid Firebase Auth token
    When the frontend sends the token to POST "/api/auth/verify"
    Then the system should verify the token successfully
    And return a 200 OK status
    And the response should contain the existing user's profile information

  Scenario: Failed login with invalid token
    Given a user provides an invalid or expired Firebase Auth token
    When the frontend sends the token to POST "/api/auth/verify"
    Then the system should fail to verify the token
    And return a 401 Unauthorized status
