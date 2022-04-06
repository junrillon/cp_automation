
    Feature: Login to backoffice

    Background: Login
    Scenario: Upload Game Image
        Given I access b2c backoffice https://dev-admin.clickplaysports.net
        When input the BO Username adminjun and Password P@ssw0rd
        And go to gamelist
#        And select provider EVOLUTION
#        And check all the games without image under provider EVOLUTION
       And check all the games without image under provider using excel
        | provider | fileType | location                          |
        | MARIO    | .png     |D:\CP Files\Casino Provider\Mario\ |

        #adminjun, P@ssw0rd shey.admin, Sheyadmin123 #testing

