
Feature: Frontend sports betting - Altenar

  Background:
    Given I logged in on frontend page https://staging.brasilplay.com/login
      | Username   | Password |
      | cmbpp1     | 123123   |


  Scenario:
    Given I navigate to altenar sports
    When I check upcoming matches
    And I select one upcoming match
    And I place bet on live matches
    Then I check my bets
    Then I send brasil betting result in telegram
      | token                                          | chatId         |
      | 5325722800:AAESQyezs3QY_7JXY0ZFVn83eQExVfTgYgg | -1001766036425 |

  #5266678102:AAFdXQxtUGGhRn14vWmXISQMZh2dK3dwkRg, -643306593 ,-1001771171564,
  #5325722800:AAESQyezs3QY_7JXY0ZFVn83eQExVfTgYgg , -1001766036425






