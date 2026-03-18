Feature: Simple Dashboard
  As a citizen or administrator of Viman Nagar
  I want to view a high-level summary of all issues
  So that I can see the overall civic progress of the ward

  Background:
    Given the system has 5 "reported" issues, 3 "resolved" issues, and 2 "rejected" issues in Viman Nagar

  Scenario: Viewing the dashboard summary
    When a user requests GET "/api/dashboard/summary"
    Then the system should return a 200 OK status
    And the response should contain the total count of issues (10)
    And the response should contain the count of resolved issues (3)
    And the response should contain the count of pending/reported issues (5)
    And the response should contain a breakdown of top issues by category (e.g., 4 potholes, 3 garbage)

  Scenario: Viewing the dashboard when unauthenticated
    Given an unauthenticated user attempts to view the dashboard
    When a user requests GET "/api/dashboard/summary"
    Then the system should allow access (public dashboard)
    And return a 200 OK status
