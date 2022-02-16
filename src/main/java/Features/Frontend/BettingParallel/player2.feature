
Feature: Frontend betting player 2

 #      @Player2
 Scenario: Player bet on team B
 Given i can access frontend login page https://www.es3838.com/login/
 When i input the Username QAACMaap1 and 234234
 And i click the login button to access the betting page
 Then i can access the betting page
 And i click the pool header button
 And i click the available sports
 And i select team and input bet amount
        #Selection 1 = TEAM A, 2 = TEAM B, 3 = DRAW
 |Selection |Amount|
 |2         |10    |
 And i click place bet button
 And i confirm my place bet
 Then place bet success