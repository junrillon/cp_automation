Feature: Jira

  Scenario: Jira card checking
    Given I access jira website "https://id.atlassian.com/login"
    When I login on Jira
      | username                          | password      |
      | junarson.rillon@clickplaycorp.com | Clickplay123! |
    And I select project "B2C"
    And I access backlog
    And I scroll to future sprint
    And I check if already logged in on testrail
      | username                  | password     |
      | qa.testrail@clickplay.org | Clickplay123 |
    And I check cards in future sprint
    Then I send results in telegram
     | token                                          | chatId     |
     | 5266678102:AAFdXQxtUGGhRn14vWmXISQMZh2dK3dwkRg | -1001785446639 |

    #-643306593 ,-1001771171564, -1001785446639
    #file_id (lets go): CgACAgUAAx0CaZHu7AADjmIuqQ0Y6JP39b-ZmyXkgvcN_VWYAAKDBQACxjU5VdiUGEsONAH2IwQ



