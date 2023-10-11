Feature: Audit

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | adacio1221@gmail.com        | Aa123123!   |

  Scenario: Login to Audit Backoffice
    When I navigate to users
    And I create a user
    And I set user details
      | Email | Role   |
      | testemail@email.com | All Access  |
    Then I click cancel
#    And I set page details
#    Then I click submit
    #And I check if page is created