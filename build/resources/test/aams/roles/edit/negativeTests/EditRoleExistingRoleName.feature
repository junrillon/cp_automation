@editRole @negativeTest
Feature: Audit - Edit a Role

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Edit role with existing role name
    When I navigate to roles
    And search for "Automation Test" role to edit
    Then I enter existing role name in edit page
      | Role Name    |
      | All Access   |

    Then I submit the role editing form with existing role name
    Then I should see a message in edit role
      """
      Role name has already been taken.
      """
