Feature:

  @jirachecker
  Scenario: Jira Checker
    Given I access jira website https://id.atlassian.com/login
#    When I input username junarson.rillon@clickplaycorp.com and password Clickplay123!
#    And I select project B2C
#    And I access backlog
#    And Navigate to future sprint in backlog
#    And Check if logged in to testrail
#      | username                  | password     |
#      | qa.testrail@clickplay.org | Clickplay123 |
#    And Check cards in future sprint
    And I send results in telegram
      | token                                          | chatId     |
      | 5266678102:AAFdXQxtUGGhRn14vWmXISQMZh2dK3dwkRg | -1001771171564 |

    #-643306593 ,-1001771171564,