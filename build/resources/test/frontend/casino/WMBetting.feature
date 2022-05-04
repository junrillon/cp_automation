# new feature
# Tags: optional

Feature: A description

  Background: player login
    Given I logged in on frontend page https://staging.ggplay.co/login/

   # user login  credentials (player balance must > 1,000)
      | Username   | Password |
      | gcme4151t1 | 123123   |

  Scenario:
    Given I navigate to live casino games
    When I select provider
      | provider |
      | Wm       |
    And I wait for casino games to load
    #And I play casino game