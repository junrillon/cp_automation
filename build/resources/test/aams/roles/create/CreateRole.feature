@createRole @positiveTest
Feature: Audit - Create a Role

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Create role with complete valid data
    When I navigate to roles
    And click create role button
    Then fill up the necessary fields
      | Role Name            | Description             | status |
      | Automation Test Page | /automation-test-page   | active |

    And adjust permission per page
      | location                                   |
      | src/main/resources/csv/page permission.csv |

    Then I click the submit button for role creation
    And I should see a message in create role
      """
      Create role successful.
      """
    Then I go back to roles
    And I check if role is created