Feature: B2C Deposit

  Background: Navigate to b2c frontend
    Given I am logged in on frontend "https://staging.ggplay.co/login"
      | Username   | Password |
      | juncmpl1     | Test123   |

  Scenario: Deposit in frontend
    When I click the balance dropdown
    And  I get the current balance
    And I click the balance dropdown
    And I click the deposit button
    And I select payment gateway "Gcash"
    And I input an amount of "1"
    And I attach file
    Then I submit the deposit
    And I get the transaction Id
#
#    Examples:
#      | transId |
#      | 2A5606B5 | juncmpl1 Test123
