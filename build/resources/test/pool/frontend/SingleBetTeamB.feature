@regression
Feature: Frontend betting player 2

  Background: player login
  Given I logged in on frontend page https://staging.ggplay.co/login/

   # user login  credentials (player balance must > 1,000)
    | Username   | Password |
    | gcme4151t1 | 123123   |

  Scenario: Single bet on team B
    Given I click the pool header button
    When I click the test sports
    And I select team and input bet amount
     #Selection 1 = TEAM A, 2 = TEAM B, 3 = DRAW
      | Selection | Amount |
      | 2         | 100    |
    And place bet success
    And I wait for the match to settle
    Then settlement is correct




