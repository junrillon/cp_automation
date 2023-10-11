Feature: Audit

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
    Then I click submit
    And I check if page is created