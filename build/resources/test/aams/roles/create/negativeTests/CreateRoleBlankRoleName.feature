@createRole @negativeTest
Feature: Audit - Create a Role

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Create role with blank role name field
    When I navigate to roles
    And click create role button
    Then I leave the role name field blank
      | Description | status |
      | role desc   | active |

    Then I submit the role creation form with blank role name
    Then I should see a message in create role
      """
      Role name field is required.
      """