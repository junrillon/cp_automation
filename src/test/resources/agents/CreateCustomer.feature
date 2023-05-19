Feature: B2C Create Customer

  Background: Navigate to b2c agent site
    Given I am logged in on agent "https://staging-agents.ggplay.club/login"
      | Username  | Password |
      | qa        | 123123   |

  Scenario: Create customer
    When I click the create account
    Then I click the create customer
    Then I create 10 users
    #for MA, SUB and AG; input the agent username
      | userPrefix | MA     | SUB      | AG         | amountToTransfer |
      | tp         | qama   | qamasub  | qamasubag  | 1                |

    Then I save the usernames to csv
      | location                                                    |
      | C:\Users\QA02\Downloads\Compressed\Jili\player_accounts.csv |