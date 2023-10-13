@editRole @negativeTest
Feature: Audit - Edit a Role

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Edit role with blank role status field
    When I navigate to roles
    And search for "Automation Test" role to edit
    Then I clear the role status field blank in edit page
    Then I submit the role editing form with blank role status
    Then I should see a message in edit role
      """
      The status field is required.
      """
