
Feature: Frontend casino betting - One Game

  Background:
    Given I logged in on frontend page https://staging.ggplay.co/login/

   # user login  credentials
      | Username   | Password |
      | shimarinn   | 123123   |

  Scenario:
    Given I navigate to casino games
    When I choose provider
      | provider |
      | OneGame|
    And I wait for casino games to load image
   # And I play game





