Feature:
#  Scenario: Login to TestRail
#    Given I access testrail website https://clickplayqa.testrail.net/index.php?/auth/login
#    When I input testrail username qa.testrail@clickplay.org and password Clickplay123

  Scenario: Login to Jira
    Given I access jira website https://id.atlassian.com/login
    When I input username junarson.rillon@clickplaycorp.com and password Clickplay123!
    And I select project B2C
    And I access backlog
    And I search Sprint_45 in backlog
      | username | password |
      | qa.testrail@clickplay.org  | Clickplay123 |