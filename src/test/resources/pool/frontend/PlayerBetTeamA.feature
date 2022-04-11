
Feature: Frontend betting player 1

  Background:
  Given I logged in on frontend page https://staging.ggplay.co/login/

   # user login  credentials
    | Username   | Password |
    | gcme4151t1 | 123123   |

  Scenario: Player bet on team A
    Given I click the pool header button
    When I click the available sports
    And I select team and input bet amount
        #Selection 1 = TEAM A, 2 = TEAM B, 3 = DRAW
      | Selection | Amount |
      | 1         | 10     |
    And I click place bet button
    And I confirm my place bet
    Then place bet success





