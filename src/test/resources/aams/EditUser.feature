Feature: Audit

  Background: Navigate to Audit Backoffice
    Given I login on audit page "https://stage-audit-admin.cpp555.com/login"
      | Username                    | Password      |
      | adacio1221@gmail.com        | Aa123123!   |

  Scenario: Login to Audit Backoffice
    When I navigate to users
    And I search for a user
      | Email |
      | sampleuseratmn@clickplay.org |
    Then I edit the user
    Then I edit the user details
    | Role | Status |
    |   Register   |   Active    |
    Then I click submit edit user
    Then I close edit success modal
