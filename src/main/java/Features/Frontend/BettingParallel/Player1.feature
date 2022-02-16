
Feature: Frontend betting player 1

  @Player1
  Scenario: Player bet on team A
    Given i can access frontend login page https://www.es3838.com/login/
    When i input the Username aaplayer1 and 123123
    And i click the login button to access the betting page
    Then i can access the betting page
    And i click the pool header button
    And i click the available sports
    And i select team and input bet amount
        #Selection 1 = TEAM A, 2 = TEAM B, 3 = DRAW
      |Selection |Amount|
      |1         |10|
    And i click place bet button
    And i confirm my place bet
    Then place bet success





