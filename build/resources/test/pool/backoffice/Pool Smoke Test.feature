# new feature
# Tags: optional
@SmokeTest
Feature: Pool smoke testing

  Background: Pool login
    Given I logged in at pool backoffice https://staging-admin.cpp555.com
      | Username | Password    |
      | andre    | @Clickplay1 |

  Scenario: Pool backoffice smoke test

    # create sport
    When I click the games header dropdown
    And I click sports
    And I click the create sport button
    And I input sports details
      | Sport Name            | Match Label | image                           | video                                       |
      | Test Automation Sport | game        | /testImages/sportsImageTest.jpg | https://www.youtube.com/watch?v=4e9vhX7ZuCw |
    And I click the submit button
    Then sport is successfully created

    # Edit sports
    When I search the sports
    And I click the edit sport
    And I change the match label to fight
    And I click edit submit button

    # Create league
    When I click the games header dropdown
    And I click the leagues
    And I click the create league button
    And I input league details
      | League Name       |
      | Automation League |

    Then league is successfully created