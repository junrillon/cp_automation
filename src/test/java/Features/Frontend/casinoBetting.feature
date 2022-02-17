
    Feature: Frontend

        Scenario: Casino Betting
        Given access frontend login page https://staging.ggplay.co/login/
        When input the Username gg241red and 456456
        And click the login button to access the betting page
        And navigate to games casino
        And select provider
        | provider |
        | Red Tiger|
        Then play available games
#        And i click the pool header button
#        And i click the available sports
#        And i select team and input bet amount
#        #Selection 1 = TEAM A, 2 = TEAM B, 3 = DRAW
#            |Selection |Amount|
#            |1         |10    |
#        And i click place bet button
#        And i confirm my place bet
#        Then place bet success




