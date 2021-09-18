
    Feature: Settle Match

    Background: Login
        Given access admin backoffice login page https://dev-admin.cpp555.com/login
        When input the Username andre and Password 123123
        And click the login button
        Then can access the homepage

    Scenario Outline: go to create match details
        And redirect to match details https://dev-admin.cpp555.com/match-details/<matchId>
#        And click the games header dropdown
#        And click matches
#        And click the search field
#        And input the match ID https://dev-admin.cpp555.com/match-details/<matchId>
        And checking the current match status
        And closing the match
        And select winner
        Examples:
            | matchId |
            |  896    |
#            |  100    |
#            |  101    |
#            |  102    |

#        And click matches
#        And click the search field
#        And input the match ID 895
#        # Pinnacle CM
#            | dg |
#            | gg |
#            | hd |
#            | ks |
#        And i see all CM is present
#        And i query stake on the DB
#        And i see the correct stake for all CM
#        And i query volume on the DB
#
#            #Master Agent
#            And i click CM1
#            And i see all agents underling




