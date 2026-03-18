Feature: View Issues (Ward Feed)
  As a citizen of Viman Nagar
  I want to view a feed of reported issues
  So that I know what problems exist in my ward

  Background:
    Given the system has multiple reported and resolved issues in the database

  Scenario: Viewing the default issue feed
    When a user requests GET "/api/issues?ward=VimanNagar"
    Then the system should return a 200 OK status
    And the response should contain a list of issues
    And each issue should display its title, category, status, and upvote count

  Scenario: Viewing issues sorted by top votes
    When a user requests GET "/api/issues?ward=VimanNagar&sort=top"
    Then the system should return a 200 OK status
    And the response should contain a list of issues ordered by highest votes first

  Scenario: Viewing issues sorted by newest
    When a user requests GET "/api/issues?ward=VimanNagar&sort=new"
    Then the system should return a 200 OK status
    And the response should contain a list of issues ordered by most recently created first

  Scenario: Viewing a single issue detail
    Given an issue exists with ID 1
    When a user requests GET "/api/issues/1"
    Then the system should return a 200 OK status
    And the response should contain the full details of issue 1 including description, comments and photo URL

  Scenario: Viewing a non-existent issue
    Given an issue with ID 999 does not exist
    When a user requests GET "/api/issues/999"
    Then the system should return a 404 Not Found status
