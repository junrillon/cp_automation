@pinnacleBetting
Feature: Frontend betting player 1

  Background: player login
    Given I logged in on frontend page https://staging.ggplay.co/login/

   # user login  credentials (player balance must > 1,000)
      | Username   | Password |
      | tesggp1 | qweqwe   |

  Scenario: Player bet on Single Bet
    Given I click the sports pinnacle header button
    When I click the early matches
    And I place a bet on single bet
     #Selection 1 = TEAM A, 2 = TEAM B
     | Selection | Amount |
     | 1         | 100    |
    And I confirm my place bet in pinnacle
    And pinnacle place bet success
    Then pinnacle bet ticket is displayed


  Scenario: Player bet on Parlay Bet
    Given I click the sports pinnacle header button
    When I click parlay tab button
    And I place a bet on parlay bet
    #ParlayOddsOne 1 = TEAM A ML, 2 = TEAM B ML
    #ParlayOddsTwo 1 = TEAM A OU, 2 = TEAM B OU
      | BetOddsSelectedOne | BetOddsSelectedTwo | Amount |
      | 1                  | 1                  | 100    |
    And I confirm my parlay place bet in pinnacle
    And pinnacle parlay place bet success
    Then pinnacle parlay bet ticket is displayed

  Scenario: Player bet on Teaser Bet
    Given I click the sports pinnacle header button
    When I click teaser tab button
    And I place a bet on teaser bet
    #TeaserOddsOne 1 = TEAM A HDP, 2 = TEAM B HDP
    #TeaserOddsTwo 1 = TEAM A OU, 2 = TEAM B OU
      | BetOddsSelectedOne | BetOddsSelectedTwo | Amount |
      | 1                  | 1                  | 100    |
    And I confirm my teaser place bet in pinnacle
    And pinnacle teaser place bet success
    Then pinnacle teaser bet ticket is displayed
