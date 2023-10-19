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
      | sampleuseratmn@clickplay.org | All Access  |
     # | location |
     # | C:\Users\daydream\Desktop\userdata.csv         |
    #Then I click cancel
    Then I click submit user
    Then I close success modal
    #And I check if user is created