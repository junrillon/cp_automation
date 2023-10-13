<<<<<<<< HEAD:build/resources/test/aams/roles/edit/EditRole.feature
Feature: Audit - Edit a Role
========
@createRole @positiveTest
Feature: Audit - Create a Role
>>>>>>>> 272914d (1. Add gradle task):build/resources/test/aams/roles/create/CreateRole.feature

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

<<<<<<<< HEAD:build/resources/test/aams/roles/edit/EditRole.feature
  Scenario: Edit a role with complete valid data
    When I navigate to roles
    And click create role button
    Then fill up the necessary fields
      | Role Name   | Description   | status |
      | Test Page 2 | Test Page 2   | active |
========
  Scenario: Create role with complete valid data
    When I navigate to roles
    And click create role button
    Then fill up the necessary fields
      | Role Name            | Description             | status |
      | Automation Test Page | /automation-test-page   | active |
>>>>>>>> 272914d (1. Add gradle task):build/resources/test/aams/roles/create/CreateRole.feature

    And adjust permission per page
      | location                                   |
      | src/main/resources/csv/page permission.csv |

    Then I click the submit button for role creation
<<<<<<<< HEAD:build/resources/test/aams/roles/edit/EditRole.feature
========
    And I should see a message in create role
      """
      Create role successful.
      """
    Then I go back to roles
>>>>>>>> 272914d (1. Add gradle task):build/resources/test/aams/roles/create/CreateRole.feature
    And I check if role is created
