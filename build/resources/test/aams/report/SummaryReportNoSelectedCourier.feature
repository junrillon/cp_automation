@summaryRerport
Feature: Audit - Summary Report

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario Outline: Filter data without selecting a courier first
    When I navigate to summary report page
    And filter the date "<Start Date> - <End Date>"
    And I click the summary filter button
    Then I should see a message in summary report
      """
      Please select a courier.
      """

    Examples:
      | Start Date  | End Date   |
      | 2023-10-10  | 2023-10-19 |