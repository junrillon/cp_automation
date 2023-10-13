@createRole @negativeTest
Feature: Audit - Create a Role

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Create role with blank role status field
    When I navigate to roles
    And click create role button
    Then I leave the status dropdown blank
      | Role Name       | Description      |
      | Test Role Name  | Test Description |

    Then I submit the role creation form with blank role status
    Then I should see a message in create role
      """
      The status field is required.
      """