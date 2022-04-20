
Feature: Frontend casino betting - Red Tiger

  Background:
    Given I logged in on frontend page https://staging.ggplay.co/login/

   # user login  credentials
      | Username   | Password |
      | gg241red   | 456456   |

  Scenario:
    Given I navigate to games casino
    When I select provider
      | provider |
      | Red Tiger|
    And I wait for casino games to load
    And I play casino game





