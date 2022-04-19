
Feature: PIX Withdraw

  Background:
    Given I log in on frontend page https://staging.brasilbet.com/

   # user login  credentials
      | Username | Password    |
      | brsldash    | 123123 |

  Scenario: PIX Withdraw using CPF
    Given I click the balance dropdown
    When I click the withdraw button
    And I click the saved bank account
    And I input the withdraw amount
    And I confirm my withdraw
    Then withdrawal success
