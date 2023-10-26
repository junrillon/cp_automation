@createPage @positiveTest
Feature: Audit - Create a Page

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Login to Audit Backoffice
    When I navigate to pages
    And I create a page
      | Page Name | Page Path   | Status |
      | Test Page | /test-page  | active |

    And I set page details
    Then I click submit button for page creation
    And I should see a message in create page
      """
      Create page successful.
      """
    Then I go back to pages
    And I check if page is created