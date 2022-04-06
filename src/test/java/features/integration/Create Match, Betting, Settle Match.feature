# new feature
# Tags: optional
Feature: Admin Create match and player betting

  @CreateMatch
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
    Then i click open bet status

  @PlayerBetting
  Scenario: Select sports
    Given i can access frontend login page https://es3838.com/login/
    When i input the Username jeraldnew and Password 123123
    And i click the login button to access the betting page
    Then i can access the betting page
    And i click the pool header button
    And i click the available sports
    And i select team and input bet amount
        #Selection 1 = TEAM A, 2 = TEAM B, 3 = DRAW
      |Selection |Amount|
      |1         |10    |
    And i click place bet button
    And i confirm my place bet
    Then place bet success

    @SettleMatch
    Scenario: go to create match details
   # Given i access admin backoffice login page https://admin.cpp555.com/dashboard
#    When input the Username andre and Password 123123
#    And click the login button
#    Then access the homepage
      And click the games header dropdown
      And click matches
      And click the search field
        | Sport |
        | Test  |
      And verify if has match
      And view match details
      And verify if has bets
        | sport_id | league_id| totalBetCount | betSelection1 | betSelection2 | betSelection3 | selectionCount |
        | 2        | 2        | 8             | 6             | 6             | 6             | 3              |
        And checking the current settlement status and match status
        And closing the match
        And select winner
        And settle match