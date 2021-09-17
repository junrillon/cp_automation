
    Feature: Frontend

    Background: Player Login
        Given i can access frontend login page https://www.es3838.com/login/
        When i input the Username jeraldnew and Password 123123
        And i click the login button
        Then i can access the homepage

    Scenario: Select sports
        And i click the pool header button
        And i click the available sports




