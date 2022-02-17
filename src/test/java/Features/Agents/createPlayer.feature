
Feature: Create Player


  Scenario: Login and Create Player
    Given I navigate to agent site https://staging-agents.es3838.com/login
    When I input the agent username ES38C and password ES3838
    And I click the agent site login button
    Then I can access the agent dashboard


    And I click create user icon
    And I click create customer
    And I select referral agents
    And I input player "username" ,"password" and "confirm password"
    And I copy the the player username
    And I transfer 1000 balance to player
    When I click the submit button
    Then I should be able to find the created player in Customer List
