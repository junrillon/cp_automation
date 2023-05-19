@poolSmokeTesting
Feature: Settle Match

  Background: Pool login
    Given I logged in at pool backoffice https://staging-admin.cpp555.com

    # user login  credentials
      | Username | Password    |
      | andre    | @Clickplay1 |

  Scenario: Settle open match

    And I click the games header dropdown
    And I navigate to matches page
    And I click the search field
      | Sport |
      | Automation Sports |
    And I verify if has match
#    And I view match details
#    And I verify if has bets
#      | sport_id | league_id| totalBetCount | betSelection1 | betSelection2 | betSelection3 |
#      | 83       | 191      | 3             | 0             | 0             | 0             |
#    And I check the current settlement status and match status
#    And I close the match
#    And I select winner
#    And I settle match
#




