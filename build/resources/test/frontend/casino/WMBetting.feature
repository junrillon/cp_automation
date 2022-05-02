# new feature
# Tags: optional

Feature: A description

  Background: player login
    Given I logged in on frontend page https://staging.ggplay.co/login/

   # user login  credentials (player balance must > 1,000)
      | Username   | Password |
      | gcme4151t1 | 123123   |

  Scenario: A scenario
    Given I click live casino header
    When I check WM on checkbox filter
    And I click play baccarat game
    And I place a bet on player odds
    And I confirm place bet
    And I wait for the settlement