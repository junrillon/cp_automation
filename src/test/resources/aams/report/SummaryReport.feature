@summaryReport
Feature: Audit - Summary Report

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario Outline: Verify data in Summary Report
    When I navigate to summary report page
    And filter the date "<Start Date> - <End Date>"
    And select a courier "<Company Code>"
    And I click the summary filter button
    And I sort the data based on the date
    Then I get data from the table
    And I get data from the database
      | Start Date  | End Date   | Company Code |
      | <Start Date>| <End Date> | <Company Code> |

    Then I compare the website and database data

    Examples:
      | Start Date  | End Date   | Company Code |
      | 2023-10-10  | 2023-10-19 | globalpost   |