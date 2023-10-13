@createPage @negativeTest
Feature: Audit - Create a Page

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Create a page with blank fields
    When I navigate to pages
    And I create a page
    Then I submit the page creation form with blank fields
    Then I should see a message in create page
      """
      Page name field is required.
      The description field is required.
      The status field is required.
      """
