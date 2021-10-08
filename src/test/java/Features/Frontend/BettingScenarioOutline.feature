Feature:


    Scenario Outline: Player bet on test match
        Given i can access frontend login page https://www.es3838.com/login/
        When i input the <username> and <password>
        And i click the login button to access the betting page
        Then i can access the homepage


        Examples:
            | username | password |
            | jeralnew | 123123 |
            | jeralnew | 123123 |
            | jeralnew | 123123 |