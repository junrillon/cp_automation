
    Feature: Settle Match

    Background: Login
        Given i access admin backoffice login page https://dev-admin.cpp555.com/dashboard
#        When input the Username andre and Password 123123
#        And click the login button
        Then access the homepage

    Scenario: go to create match details
#        And get match id from DB
#        And redirect to match details
        And click the games header dropdown
        And click matches
        And click the search field
            | Sport |
            | Valorant  |
        And verify if has match
        And verify if has bets
#        And view match details
#        And checking the current settlement status and match status
#        And closing the match
#        And select winner
#        And settle match




