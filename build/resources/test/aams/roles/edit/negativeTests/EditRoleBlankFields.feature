@editRole @negativeTest
Feature: Audit - Edit a Role

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Edit a role with empty fields
    When I navigate to roles
    And search for "Automation Test" role to edit
    And I clear the role details in edit page
    And I submit the empty role editing form
    Then I should see a message in edit role
      """
      Role name field is required.
      The description field is required.
      The status field is required.
      """
