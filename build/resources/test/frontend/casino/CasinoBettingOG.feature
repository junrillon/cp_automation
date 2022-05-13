
Feature: Frontend casino betting - One Game

  Background:
    Given I logged in on frontend page https://staging.ggplay.co/login/

   # user login  credentials
      | Username   | Password |
      | shimarinn   | 123123   |

  Scenario:
    Given I navigate to games casino
    When I select provider
      | provider |
      | Onegame|
    And I wait for casino games to load
    And I play game
   # Then I send OG betting result in telegram
   #   | token                                          | chatId     |
   #   | 5325722800:AAESQyezs3QY_7JXY0ZFVn83eQExVfTgYgg | -1001766036425 |




