
    Feature: Login to backoffice

    Background: Login
    Scenario: Upload Game Image
        Given I access b2c backoffice https://admin.clickplaysports.net
        When input the BO Username shey.admin and Password Sheyadmin123
        And go to gamelist
#        And select provider EVOLUTION
#        And check all the games without image under provider EVOLUTION
       And check all the games without image under provider using excel
        | provider | fileType | location |
        | EVOLUTION| .png     |D:\CP Files\Casino Provider\Evolution\RED TIGER\Red Tiger (Resized)\Red Tiger\ |

        #adminjun, P@ssw0rd shey.admin, Sheyadmin123 #testing

