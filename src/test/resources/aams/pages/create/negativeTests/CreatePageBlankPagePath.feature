Feature: Audit - Create a Page

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Create page with blank page path field
    When I navigate to pages
    And I create a page
    Then I leave the page path blank
      | Page Name            | status |
      | Automation Test Page | active |

    Then I submit the page creation form with blank page path
