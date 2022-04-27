# new feature
# Tags: optional
@poolSmokeTesting
Feature: A description

  Background: Pool login
    Given I clear the pool match data
    And I logged in at pool backoffice https://staging-admin.cpp555.com

    # user login  credentials
      | Username | Password    |
      | andre    | @Clickplay1 |

  Scenario: Create and open match on test league
    Given I click the games header dropdown
    When I click matches
    And I click the create match button
    And I input match details

    # Input match details
      | Sport             | League      | Match count |
      | Automation Sports | Test League | 1           |

    And I click submit button
    And I select view match details from action dropdown
    And I click open bet status
    Then match is open