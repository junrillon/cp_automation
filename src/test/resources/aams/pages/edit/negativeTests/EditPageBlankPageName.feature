@editPage @negativeTest
Feature: Audit - Edit a Page

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Edit page with blank page name field
    When I navigate to pages
    And search for "Automation Test Page" to edit
    Then I leave the page name field blank in edit page
      | Page Path              | Status |
      | /automation-test-page  | active |

    Then I submit the edit page form with blank page name
