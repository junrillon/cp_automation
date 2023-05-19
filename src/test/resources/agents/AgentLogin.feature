Feature: B2C Deposit

  Background: Navigate to b2c agent site
    Given I am logged in on agent "https://staging-agents.ggplay.co/login"
      | Username  | Password |
      | intcm     | 456456   |

  Scenario: Deposit in frontend
    When I click the balance dropdown