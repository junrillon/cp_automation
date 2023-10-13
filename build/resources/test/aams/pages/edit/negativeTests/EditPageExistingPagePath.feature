@editPage @negativeTest
Feature: Audit - Edit a Page

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Create page with existing page name
    When I navigate to pages
    And search for "Automation Test Page" to edit
    Then I enter existing page path in edit page
      | Page Name            | Page Path             | status |
      | Automation Test Page | /admin/cms/couriers   | active |

    Then I submit the edit page form with existing page path
