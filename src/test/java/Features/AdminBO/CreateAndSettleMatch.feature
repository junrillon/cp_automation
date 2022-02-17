
Feature: Create and settle


  Scenario: go to create match
    Given i can access admin backoffice login page https://admin.cpp555.com/login
    When i input the admin Username andre and Password 123123
    And i click the login button
    Then i can access the homepage
    And i click the games header dropdown
    And i click matches
    And i click the create match button
    And i input match details
      | Sport | League      | Match count |
      | Test  | Test League | 1           |
    And i click submit button
    And i select view match details from action dropdown
    And i click open bet status

    #settlement
    And verify if has bets
    And view match details
    And checking the current settlement status and match status
    And closing the match
    And select winner
    And settle match





