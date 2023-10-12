Feature: Audit - Create a Role

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Login to Audit Backoffice
    When I navigate to roles
    And click create role button
    Then fill up the necessary fields
#      | Page Name | Page Path   | Status |
#      | Test Page | /test-page  | active |
#
    And adjust permission per page
      | location                                    |
      | C:\Users\QA02\Downloads\page permission.csv |


#    Then I click submit
#    And I check if page is created