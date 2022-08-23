Feature: Pix Deposit

  Background: Navigate to jwt site
    Given I logged in on frontend page https://staging.brasilplay.com/login
      | Username   | Password |
      | cmbppl3     | #Test123   |

  Scenario: Approve Deposit
    When I click the balance dropdown
    And I click the deposit button
    And I select payment gateway
    And I input an amount of 10
    Then I submit the deposit
    And I get the transaction Id
#    And I process the deposit https://staging-pix-api.bpc555.com/api/payment/callback
#
#    Examples:
#      | transId |
#      | 2A5606B5 |
#      | F2D027D0 |
