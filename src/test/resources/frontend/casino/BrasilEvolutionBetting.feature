
Feature: Frontend casino betting - Brasil Evolution

  Background:
    Given I clear the casino data
      | Username |
      | aff1pl|

    Given I logged in on frontend page https://staging.brasilplay.com/login
     # user login  credentials aff1pl 123123 https://staging.brasilplay.com/login gg241red2 456456
      | Username   | Password |
      | aff1pl  | 123123   |


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






