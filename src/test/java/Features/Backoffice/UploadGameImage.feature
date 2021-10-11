
    Feature: Login to backoffice

    Background: Login
    Scenario:
        Given I access b2c backoffice https://staging-admin.clickplaysports.net/login
        When input the Username adminjun and Password P@ssw0rd
        And click the login button
        Then access the homepage
        And go to gamelist
        And check all the games without image under provider using excel
        | provider | fileType | location |
        | EVOLUTION| .png     |D:\CP Files\Casino Provider\Evolution\RED TIGER\Red Tiger (Resized)\Red Tiger\ |