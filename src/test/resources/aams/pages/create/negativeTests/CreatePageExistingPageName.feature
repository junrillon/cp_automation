@createPage @negativeTest
Feature: Audit - Create a Page

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Create page with existing page name
    When I navigate to pages
    And I create a page
    Then I enter existing page name
      | Page Name | Page Path        | status |
      | Couriers  | /couriers-path   | active |

    Then I submit the page creation form with existing page name
    Then I should see a message in create page
      """
      Page name has already been taken.
      """