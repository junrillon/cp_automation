
Feature: Xendit Withdraw

  Background:
    Given I log-in on frontend page https://staging.fanjuan.ph/

   # user login  credentials
      | Username | Password    |
      | xenddash    | 123123 |

  Scenario: Xendit Withdraw using GCash
    Given I click the nav balance dropdown
    When I select the withdraw button
    And I select the saved bank account
    And I place the withdraw amount
    And I confirm withdraw
    Then withdrawal is successful
