
    Feature: Create Match

    Background: Login
        Given i can access admin backoffice login page https://admin.cpp555.com/login
        When i input the Username andre and Password 123123
        And i click the login button
        Then i can access the homepage

    Scenario: go to create match
        And i click the games header dropdown
        And i click matches
        And i click the search button
        And i query on the DB if CM is available
        # Pinnacle CM
            | dg |
            | gg |
            | hd |
            | ks |
        And i see all CM is present
        And i query stake on the DB
        And i see the correct stake for all CM
        And i query volume on the DB

            #Master Agent
            And i click CM1
            And i see all agents underling




