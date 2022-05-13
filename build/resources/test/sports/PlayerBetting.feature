
Feature: Frontend betting player 1

  Background: player login
    Given I logged in on frontend page https://staging.ggplay.co/login/

   # user login  credentials (player balance must > 1,000)
      | Username   | Password |
      | tesggp2 | qweqwe   |

  Scenario: Player bet on Single Bet
    Given I click the sports pinnacle header button
    When I click the early matches
    And I select team A odds and input bet amount
     #Selection 1 = TEAM A, 2 = TEAM B, 3 = DRAW
     | Selection | Amount |
     | 1         | 100    |
    And I confirm my place bet in pinnacle
    And pinnacle place bet success
    And I click the pinnacle my bet
    Then pinnacle bet ticket is displayed
