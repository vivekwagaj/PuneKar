Feature: Upvote Issues
  As a citizen of Viman Nagar
  I want to upvote issues that matter to me
  So that they surface to the top of the priority list

  Background:
    Given an authenticated user exists with ID 10
    And an issue exists with ID 100

  Scenario: Upvoting an issue successfully
    Given the user has not voted for issue 100
    When the frontend sends a POST request to "/api/issues/100/vote"
    Then the system should record the vote for user 10" on issue 100
    And the issue 100 upvote count should increase by 1
    And return a 200 OK status

  Scenario: Preventing duplicate upvotes
    Given the user has already voted for issue 100
    When the frontend sends a POST request to "/api/issues/100/vote"
    Then the system should ignore the duplicate vote (idempotent)
    And return a 200 OK status (or 409 Conflict based on preference)
    And the issue 100 upvote count should not increase

  Scenario: Removing an upvote
    Given the user has already voted for issue 100
    When the frontend sends a DELETE request to "/api/issues/100/vote"
    Then the system should remove the vote for user 10 on issue 100
    And the issue 100 upvote count should decrease by 1
    And return a 200 OK status

  Scenario: Attempting to upvote unauthenticated
    Given an unauthenticated user attempts to vote
    When the frontend sends a POST request to "/api/issues/100/vote"
    Then the system should block the request
    And return a 401 Unauthorized status
