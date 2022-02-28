Feature:

  @testrailchecker
  Scenario: TestRail Checker
#    Given I access testrail website https://clickplayqa.testrail.net/
#    When I input testrail username qa.testrail@clickplay.org and password Clickplay123
    Given Get tr
      | username                  | password     | url                               |
      | qa.testrail@clickplay.org | Clickplay123 | https://clickplayqa.testrail.net/ |


    #-643306593 ,-1001771171564,