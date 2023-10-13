@editPage @negativeTest
Feature: Audit - Edit a Page

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Create page with blank page status field
    When I navigate to pages
    And search for "Automation Test Page" to edit
    Then I leave the page status dropdown blank in edit page
      | Page Name             | Page Path              |
      | Automation Test Page  | /automation-test-page  |

    Then I submit the edit page form with blank page status
