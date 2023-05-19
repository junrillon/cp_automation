Feature: B2C Withdraw

  Background: Navigate to b2c frontend
    Given I am logged in on frontend "https://staging.ggplay.co/login"
      | Username   | Password |
      | juncmpl1   | Test123   |

  Scenario: Withdraw money from frontend
    When I click the balance dropdown
    And  I get the current balance
    And I click the balance dropdown
    And I click the withdraw button
    And I add withdraw account
    Then I click the saved account
    And I input amount to withdraw 100
#    And I select payment gateway "Gcash"
#    And I input an amount of "1"
#    And I attach file
#    Then I submit the deposit
#    And I get the transaction Id