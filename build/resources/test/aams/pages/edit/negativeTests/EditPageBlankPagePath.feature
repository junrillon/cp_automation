@editPage @negativeTest
Feature: Audit - Edit a Page

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Edit page with blank page path field
    When I navigate to pages
    And search for "Automation Test Page" to edit
    Then I leave the page path field blank in edit page
      | Page Name             | Status |
      | Automation Test Page  | active |

    Then I submit the edit page form with blank page path
