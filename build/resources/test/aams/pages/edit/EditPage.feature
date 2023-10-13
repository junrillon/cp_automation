@editPage @positiveTest
Feature: Audit - Edit a Page

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Edit a page
    When I navigate to pages
    And search for "Automation Test" page to edit
    And I update the page details
      | Page Name            | Page Path              | Status |
      | Automation Test Page | /automation-test-page  | active |

    Then I click the submit button for page editing.
    And I should see a message in create page
      """
      Edit page successful.
      """
    Then I go back to pages
    And I check if the changes in the page were saved