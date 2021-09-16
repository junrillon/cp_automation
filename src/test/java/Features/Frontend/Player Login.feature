
    Feature: Frontend

    Background: Player Login
        Given i can access frontend login page https://www.es3838.com/login/
        When i input the Username jeraldnew and Password 123123
        And i click the login button
        Then i can access the homepage

    Scenario: Select sports
        And i click the pool header button
        And i click the available sports
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




