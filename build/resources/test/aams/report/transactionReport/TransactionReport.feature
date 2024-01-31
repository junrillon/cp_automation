@transactionReport
Feature: Audit - Transaction Report

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | r.junarson@gmail.com        | Clickplay@1   |

  Scenario Outline: Verify data in Transaction Report
    When I navigate to transaction report page
    And filter the date "<Start Date> - <End Date>" in transaction report
    And select a courier "<Company Code>" in transaction report
    And I click the transaction filter button
#    And I sort the data based on the date
    Then I will get data from the transaction datatable
    And get data from the aud_transactions
      | Start Date  | End Date   | Company Code |
      | <Start Date>| <End Date> | <Company Code> |

    Then data from transaction page and aud_transactions table should be the same

    Examples:
      | Start Date  | End Date   | Company Code |
      | 2023-11-06  | 2023-11-06 | globalpost   |
