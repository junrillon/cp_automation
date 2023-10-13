@createRole @negativeTest
Feature: Audit - Create a Role

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Create role with blank fields
    When I navigate to roles
    And click create role button
    Then I submit the empty role creation form
    Then I should see a message in create role
      """
      Role name field is required.
      The description field is required.
      The status field is required.
      """

