@editRole @positiveTest
Feature: Audit - Edit a Role

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Edit a role with complete valid data
    When I navigate to roles
    And search for "Automation Test" role to edit
    And I update the role details in edit page
      | Role Name            | Desc                  | Status |
      | Automation Test Page | Automation Test Page  | active |

    Then I submit the complete role editing form
    And I should see a message in edit role
      """
      Edit role successful.
      """
    Then I go back to roles
    And the changes should be saved
