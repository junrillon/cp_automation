
Feature: Frontend casino betting - GGPlay Evolution

  Background:
    Given I clear the casino data
      | Username |
      | gg241red2|

    Given I logged in on frontend page https://staging.ggplay.co/login
     # user login  credentials aff1pl 123123 https://staging.brasilplay.com/login gg241red2 456456
      | Username   | Password |
      | gg241red2  | 456456   |


  Scenario:
    Given I navigate to live casino
    When I select provider
      | provider |
      | Evolution|
    And I wait for live games to load
    And I select live game
    And I play live casino
    Then I send EVO betting result in telegram
      | token                                          | chatId         |
      | 5325722800:AAESQyezs3QY_7JXY0ZFVn83eQExVfTgYgg | -1001766036425 |

  #5266678102:AAFdXQxtUGGhRn14vWmXISQMZh2dK3dwkRg, -643306593 ,-1001771171564,
  #5325722800:AAESQyezs3QY_7JXY0ZFVn83eQExVfTgYgg , -1001766036425





