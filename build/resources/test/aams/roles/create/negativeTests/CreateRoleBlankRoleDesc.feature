@createRole @negativeTest
Feature: Audit - Create a Role

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Create role with blank role desc field
    When I navigate to roles
    And click create role button
    Then I leave the description field blank
      | Role Name       | status |
      | Test Role Name  | active |

    Then I submit the role creation form with blank role desc
    Then I should see a message in create role
      """
      The description field is required.
      """