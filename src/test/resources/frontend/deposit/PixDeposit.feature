Feature: Pix Deposit

  Background: Navigate to brasilplay frontend
    Given I logged in on frontend page https://staging.brasilplay.com/login
      | Username   | Password |
      | aff4pl     | Test123   |

  Scenario: Deposit in frontend
    When I click the balance dropdown
    And  I get the current balance
    And I click the balance dropdown
    And I click the deposit button
    And I select payment gateway "PIX"
    And I input an amount of "1"
    And I attach file
    Then I submit the deposit
    And I get the transaction Id
#
#    Examples:
#      | transId |
#      | 2A5606B5 | juncmpl1 Test123 aff4pl
