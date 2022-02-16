
    Feature: Login to backoffice

    Background: Login
    Scenario:
#        Given I access b2c backoffice https://admin.clickplaysports.net/login
#        When input the BO Username shey.admin and Password Sheyadmin123
#        And click the login button in BO
#        Then access the BO dashboard
        And go to gamelist
#        And select provider EVOLUTION
#        And check all the games without image under provider EVOLUTION
        And check all the games without image under provider using excel
        | provider | fileType | location |
        | EVOLUTION| .png     |D:\CP Files\Casino Provider\Evolution\RED TIGER\Red Tiger (Resized)\Red Tiger\ |