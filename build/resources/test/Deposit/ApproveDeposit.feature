Feature: Approve Deposit

  Scenario Outline: Approve Deposit
    Given I get details in br_user_fund_transactions table
      | transId |
      | <transId> |

    When I navigate to jwt https://jwt.io/
    And I input deposit details
    And I input signature eRsecey1lBjW
    Then I get the generated token
    And I process the deposit

    Examples:
      | transId |
      | CF0C0A54 |
      | 4D4B48AE |
      | 4398EC04 |
