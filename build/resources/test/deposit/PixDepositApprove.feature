Feature: Approve Deposit

  Background: Navigate to jwt site
    Given I navigate to jwt https://jwt.io/

  #Scenario Outline if has Examples table
  Scenario: Approve Deposit
    When I get details in br_user_fund_transactions table
      | transId | env |
      | <transId> | stage_b2c |

    And I get agent product details
    And I input deposit details
    And I input signature eRsecey1lBjW
    Then I get the generated token
    And I process the deposit https://staging-pix-api.bpc555.com/api/payment/callback
    And I logged in on frontend page https://staging.brasilplay.com/login
      | Username   | Password |
      | aff4pl     | #Test123   |
    And I click the balance dropdown
    And I check the balance after deposit

#    Examples:
#      | transId |
#      | 3B121A37 |
