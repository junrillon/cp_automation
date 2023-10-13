Feature: Audit - Create a Role

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Create role with existing role name
    When I navigate to roles
    And click create role button
    Then I enter existing role name
      | Role Name   | Description         | status |
      | All Access  | Test Role Desc      | active |

    Then I submit the role creation form with existing role name
