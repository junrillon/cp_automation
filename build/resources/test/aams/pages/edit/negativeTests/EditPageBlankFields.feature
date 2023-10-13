@editPage @negativeTest
Feature: Audit - Edit a Page

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario: Edit a page with blank fields
    When I navigate to pages
    And search for "Automation Test Page" to edit
    And I clear the page details
    Then I submit the edit page form with blank fields