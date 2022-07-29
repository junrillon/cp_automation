Feature: Approve Deposit

  Background: Navigate to jwt site
    Given I navigate to jwt https://jwt.io/

  Scenario Outline: Approve Deposit
    When I get details in br_user_fund_transactions table
      | transId | env |
      | <transId> | stage_b2c |

    And I input deposit details
    And I input signature eRsecey1lBjW
    Then I get the generated token
    And I process the deposit https://staging-pix-api.bpc555.com/api/payment/callback

    Examples:
      | transId |
      | E334C433 |
